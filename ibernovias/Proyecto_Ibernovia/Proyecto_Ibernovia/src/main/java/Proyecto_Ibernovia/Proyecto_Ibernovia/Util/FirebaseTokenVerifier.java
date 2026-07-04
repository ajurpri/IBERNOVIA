package Proyecto_Ibernovia.Proyecto_Ibernovia.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class FirebaseTokenVerifier {

    public record VerificationResult(Claims claims, String errorMessage) {
        public boolean isValid() {
            return claims != null;
        }
    }

    private static final String GOOGLE_JWK_URL = "https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com";

    private final Map<String, PublicKey> keyCache = new HashMap<>();
    private long cacheExpiryTime = 0;
    private final String projectId;

    public FirebaseTokenVerifier(@Value("${app.firebase.project-id:}") String projectId) {
        this.projectId = projectId == null ? "" : projectId.trim();
    }

    private synchronized String refreshKeys() {
        if (System.currentTimeMillis() < cacheExpiryTime && !keyCache.isEmpty()) {
            return null;
        }

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GOOGLE_JWK_URL))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return "No se pudieron descargar las claves publicas de Google (HTTP " + response.statusCode() + ")";
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode keysNode = root.get("keys");
            if (keysNode == null || !keysNode.isArray() || keysNode.isEmpty()) {
                return "Google no devolvio claves publicas validas para verificar Firebase";
            }

            keyCache.clear();
            for (JsonNode keyNode : keysNode) {
                String kid = keyNode.get("kid").asText();
                String nStr = keyNode.get("n").asText();
                String eStr = keyNode.get("e").asText();

                byte[] nBytes = Base64.getUrlDecoder().decode(nStr);
                byte[] eBytes = Base64.getUrlDecoder().decode(eStr);

                BigInteger modulus = new BigInteger(1, nBytes);
                BigInteger exponent = new BigInteger(1, eBytes);

                RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
                KeyFactory factory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = factory.generatePublic(spec);

                keyCache.put(kid, publicKey);
            }

            cacheExpiryTime = System.currentTimeMillis() + 3600000;
            return null;
        } catch (Exception e) {
            return "No se pudieron obtener las claves publicas de Firebase/Google: " + e.getMessage();
        }
    }

    public VerificationResult verifyToken(String token) {
        if (projectId.isBlank()) {
            return new VerificationResult(null,
                    "El backend no tiene configurado app.firebase.project-id. Define APP_FIREBASE_PROJECT_ID, FIREBASE_PROJECT_ID, GOOGLE_CLOUD_PROJECT o VITE_FIREBASE_PROJECT_ID.");
        }

        String refreshError = refreshKeys();
        if (refreshError != null) {
            return new VerificationResult(null, refreshError);
        }

        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                return new VerificationResult(null, "El token de Firebase no tiene un formato JWT valido");
            }

            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode header = mapper.readTree(headerJson);
            String kid = header.has("kid") ? header.get("kid").asText() : null;

            if (kid == null || kid.isBlank()) {
                return new VerificationResult(null, "El token de Firebase no incluye un kid valido");
            }

            if (!keyCache.containsKey(kid)) {
                return new VerificationResult(null,
                        "La clave publica del token Firebase no esta disponible en el backend. Puede haber un desfase de configuracion o de rotacion de claves.");
            }

            PublicKey publicKey = keyCache.get(kid);
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            String expectedIssuer = "https://securetoken.google.com/" + projectId;

            if (!expectedIssuer.equals(claims.getIssuer())) {
                return new VerificationResult(null,
                        "El token pertenece a otro proyecto de Firebase. Issuer esperado: " + expectedIssuer + ", recibido: " + claims.getIssuer());
            }

            if (!projectId.equals(claims.getAudience())) {
                return new VerificationResult(null,
                        "El token pertenece a otro projectId de Firebase. Audiencia esperada: " + projectId + ", recibida: " + claims.getAudience());
            }

            return new VerificationResult(claims, null);
        } catch (Exception e) {
            return new VerificationResult(null, "El token de Firebase es invalido o ha expirado: " + e.getMessage());
        }
    }
}
