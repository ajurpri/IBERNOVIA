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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FirebaseTokenVerifier {

    private static final String GOOGLE_JWK_URL = "https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com";
    private final Map<String, PublicKey> keyCache = new HashMap<>();
    private long cacheExpiryTime = 0;
    
    private final String projectId;

    public FirebaseTokenVerifier(@Value("${app.firebase.project-id:ibernovia}") String projectId) {
        this.projectId = projectId;
    }

    private synchronized void refreshKeys() {
        if (System.currentTimeMillis() < cacheExpiryTime && !keyCache.isEmpty()) {
            return;
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GOOGLE_JWK_URL))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.body());
                JsonNode keysNode = root.get("keys");
                if (keysNode != null && keysNode.isArray()) {
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
                    // Cache por 1 hora
                    cacheExpiryTime = System.currentTimeMillis() + 3600000;
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching Google public keys: " + e.getMessage());
        }
    }

    public Claims verifyToken(String token) {
        try {
            refreshKeys();
            
            // Extraer el encabezado para obtener el kid (Key ID)
            String[] parts = token.split("\\.");
            if (parts.length < 2) return null;
            
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode header = mapper.readTree(headerJson);
            String kid = header.has("kid") ? header.get("kid").asText() : null;
            
            if (kid == null || !keyCache.containsKey(kid)) {
                return null;
            }
            
            PublicKey publicKey = keyCache.get(kid);
            
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);
            
            Claims claims = claimsJws.getBody();
            
            // Validar emisor (https://securetoken.google.com/<projectId>)
            String expectedIssuer = "https://securetoken.google.com/" + projectId;
            if (!expectedIssuer.equals(claims.getIssuer())) {
                System.err.println("Firebase issuer mismatch: expected " + expectedIssuer + ", got " + claims.getIssuer());
                return null;
            }
            
            // Validar audiencia (debe ser el projectId de Firebase)
            if (!projectId.equals(claims.getAudience())) {
                System.err.println("Firebase audience mismatch: expected " + projectId + ", got " + claims.getAudience());
                return null;
            }
            
            return claims;
        } catch (Exception e) {
            System.err.println("Firebase token verification failed: " + e.getMessage());
            return null;
        }
    }
}
