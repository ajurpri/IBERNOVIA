package Proyecto_Ibernovia.Proyecto_Ibernovia.Util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.time.Duration;

@Component
public class FirebaseTokenVerifier {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseTokenVerifier.class);
    private static final String FIREBASE_CERTS_URL =
            "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";
    private static final int ERROR_SNIPPET_LENGTH = 180;

    public record VerificationResult(String email, String name, String errorMessage) {
        public boolean isValid() {
            return email != null && !email.isBlank();
        }
    }

    private final String projectId;
    private final String credentialsResource;
    private final boolean firebaseEnabled;
    private final String credentialsJson;
    private final String credentialsBase64;
    private final HttpClient httpClient;
    private volatile FirebaseAuth firebaseAuth;
    private volatile String resolvedProjectId;
    private volatile String credentialsProjectId;

    public FirebaseTokenVerifier(
            @Value("${app.firebase.enabled:false}") boolean firebaseEnabled,
            @Value("${app.firebase.project-id:}") String projectId,
            @Value("${app.firebase.credentials-resource:}") String credentialsResource,
            @Value("${app.firebase.credentials-json:}") String credentialsJson,
            @Value("${app.firebase.credentials-base64:}") String credentialsBase64
    ) {
        this.firebaseEnabled = firebaseEnabled;
        this.projectId = projectId == null ? "" : projectId.trim();
        this.credentialsResource = credentialsResource == null ? "" : credentialsResource.trim();
        this.credentialsJson = credentialsJson == null ? "" : credentialsJson.trim();
        this.credentialsBase64 = credentialsBase64 == null ? "" : credentialsBase64.trim();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public record FirebaseStatus(
            boolean enabled,
            boolean available,
            String configuredProjectId,
            String credentialsProjectId,
            String resolvedProjectId,
            String message,
            Integer certsHttpStatus,
            String certsProbeMessage
    ) {}

    private synchronized String ensureFirebaseAuth() {
        if (firebaseAuth != null) {
            return null;
        }

        if (!firebaseEnabled) {
            return "Firebase Auth esta deshabilitado en el backend. Define APP_FIREBASE_ENABLED=true para activarlo.";
        }

        try (InputStream credentialsStream = openCredentialsStream()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
            if (credentials instanceof ServiceAccountCredentials serviceAccountCredentials) {
                credentialsProjectId = normalize(serviceAccountCredentials.getProjectId());
            }

            if (!projectId.isBlank()
                    && credentialsProjectId != null
                    && !credentialsProjectId.isBlank()
                    && !projectId.equals(credentialsProjectId)) {
                return "APP_FIREBASE_PROJECT_ID (" + projectId + ") no coincide con el proyecto de las credenciales (" + credentialsProjectId + ").";
            }

            String effectiveProjectId = !projectId.isBlank() ? projectId : normalize(credentialsProjectId);

            FirebaseOptions.Builder optionsBuilder = FirebaseOptions.builder()
                    .setCredentials(credentials);

            if (effectiveProjectId != null && !effectiveProjectId.isBlank()) {
                optionsBuilder.setProjectId(effectiveProjectId);
            }

            FirebaseApp app;
            if (FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(optionsBuilder.build());
            } else {
                app = FirebaseApp.getApps().get(0);
            }

            firebaseAuth = FirebaseAuth.getInstance(app);
            resolvedProjectId = effectiveProjectId;
            logger.info("Firebase Admin inicializado correctamente. projectIdConfigurado='{}', projectIdCredenciales='{}', projectIdResuelto='{}'",
                    normalize(projectId), normalize(credentialsProjectId), normalize(resolvedProjectId));
            return null;
        } catch (Exception e) {
            logger.error("No se pudo inicializar Firebase Admin en el backend", e);
            return "No se pudo inicializar Firebase Admin en el backend: " + e.getMessage();
        }
    }

    public FirebaseStatus getStatus() {
        String initError = ensureFirebaseAuth();
        CertsProbeResult probe = probeFirebaseCertsEndpoint();
        return new FirebaseStatus(
                firebaseEnabled,
                initError == null,
                normalize(projectId),
                normalize(credentialsProjectId),
                normalize(resolvedProjectId),
                initError == null ? "Firebase Auth disponible." : initError,
                probe.httpStatus(),
                probe.message()
        );
    }

    public VerificationResult verifyToken(String token) {
        String initError = ensureFirebaseAuth();
        if (initError != null) {
            return new VerificationResult(null, null, initError);
        }

        try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);
            return new VerificationResult(decodedToken.getEmail(), decodedToken.getName(), null);
        } catch (FirebaseAuthException e) {
            String errorMessage = "El token de Firebase es invalido o ha expirado: " + e.getMessage();
            if (e.getMessage() != null && e.getMessage().contains("Error while fetching public key certificates")) {
                CertsProbeResult probe = probeFirebaseCertsEndpoint();
                errorMessage += " | Sonda backend->Google: " + probe.message();
            }
            logger.error("Error validando token de Firebase", e);
            return new VerificationResult(null, null, errorMessage);
        } catch (Exception e) {
            logger.error("No se pudo validar el token de Firebase", e);
            return new VerificationResult(null, null, "No se pudo validar el token de Firebase: " + e.getMessage());
        }
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private InputStream openCredentialsStream() throws Exception {
        if (!credentialsJson.isBlank()) {
            return new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8));
        }

        if (!credentialsBase64.isBlank()) {
            byte[] decoded = Base64.getDecoder().decode(credentialsBase64);
            return new ByteArrayInputStream(decoded);
        }

        if (credentialsResource.isBlank()) {
            throw new IllegalStateException("Configura APP_FIREBASE_CREDENTIALS_JSON, APP_FIREBASE_CREDENTIALS_BASE64 o APP_FIREBASE_CREDENTIALS_RESOURCE.");
        }

        return new ClassPathResource(credentialsResource).getInputStream();
    }

    private record CertsProbeResult(Integer httpStatus, String message) {}

    private CertsProbeResult probeFirebaseCertsEndpoint() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(FIREBASE_CERTS_URL))
                    .timeout(Duration.ofSeconds(15))
                    .header("User-Agent", "IBERNOVIA-Firebase-Probe/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String snippet = normalizeSnippet(response.body());
            String message = "HTTP " + response.statusCode();
            if (!snippet.isBlank()) {
                message += " body=" + snippet;
            }
            logger.info("Sonda de certificados Firebase completada con estado {}", response.statusCode());
            return new CertsProbeResult(response.statusCode(), message);
        } catch (Exception e) {
            logger.error("Fallo al consultar el endpoint de certificados publicos de Firebase", e);
            return new CertsProbeResult(null, "Error consultando certificados de Google: " + e.getMessage());
        }
    }

    private String normalizeSnippet(String value) {
        if (value == null) {
            return "";
        }
        String normalized = value.replaceAll("\\s+", " ").trim();
        if (normalized.length() <= ERROR_SNIPPET_LENGTH) {
            return normalized;
        }
        return normalized.substring(0, ERROR_SNIPPET_LENGTH) + "...";
    }
}
