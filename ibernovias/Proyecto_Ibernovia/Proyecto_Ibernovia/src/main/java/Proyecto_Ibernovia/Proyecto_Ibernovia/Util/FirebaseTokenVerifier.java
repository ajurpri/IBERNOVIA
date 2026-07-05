package Proyecto_Ibernovia.Proyecto_Ibernovia.Util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class FirebaseTokenVerifier {

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
    private volatile FirebaseAuth firebaseAuth;
    private volatile String resolvedProjectId;
    private volatile String credentialsProjectId;

    public FirebaseTokenVerifier(
            @Value("${app.firebase.enabled:false}") boolean firebaseEnabled,
            @Value("${app.firebase.project-id:}") String projectId,
            @Value("${app.firebase.credentials-resource:firebase-adminsdk.json}") String credentialsResource,
            @Value("${app.firebase.credentials-json:}") String credentialsJson,
            @Value("${app.firebase.credentials-base64:}") String credentialsBase64
    ) {
        this.firebaseEnabled = firebaseEnabled;
        this.projectId = projectId == null ? "" : projectId.trim();
        this.credentialsResource = credentialsResource == null ? "firebase-adminsdk.json" : credentialsResource.trim();
        this.credentialsJson = credentialsJson == null ? "" : credentialsJson.trim();
        this.credentialsBase64 = credentialsBase64 == null ? "" : credentialsBase64.trim();
    }

    public record FirebaseStatus(
            boolean enabled,
            boolean available,
            String configuredProjectId,
            String credentialsProjectId,
            String resolvedProjectId,
            String message
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
            return null;
        } catch (Exception e) {
            return "No se pudo inicializar Firebase Admin en el backend: " + e.getMessage();
        }
    }

    public FirebaseStatus getStatus() {
        String initError = ensureFirebaseAuth();
        return new FirebaseStatus(
                firebaseEnabled,
                initError == null,
                normalize(projectId),
                normalize(credentialsProjectId),
                normalize(resolvedProjectId),
                initError == null ? "Firebase Auth disponible." : initError
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
            return new VerificationResult(null, null, "El token de Firebase es invalido o ha expirado: " + e.getMessage());
        } catch (Exception e) {
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

        return new ClassPathResource(credentialsResource).getInputStream();
    }
}
