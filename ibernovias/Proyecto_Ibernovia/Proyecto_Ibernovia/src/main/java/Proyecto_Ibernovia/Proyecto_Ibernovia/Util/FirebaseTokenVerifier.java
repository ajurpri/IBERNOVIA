package Proyecto_Ibernovia.Proyecto_Ibernovia.Util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FirebaseTokenVerifier {

    public record VerificationResult(String email, String name, String errorMessage) {
        public boolean isValid() {
            return email != null && !email.isBlank();
        }
    }

    private final String projectId;
    private final String credentialsResource;
    private volatile FirebaseAuth firebaseAuth;

    public FirebaseTokenVerifier(
            @Value("${app.firebase.project-id:}") String projectId,
            @Value("${app.firebase.credentials-resource:firebase-adminsdk.json}") String credentialsResource
    ) {
        this.projectId = projectId == null ? "" : projectId.trim();
        this.credentialsResource = credentialsResource == null ? "firebase-adminsdk.json" : credentialsResource.trim();
    }

    private synchronized String ensureFirebaseAuth() {
        if (firebaseAuth != null) {
            return null;
        }

        try (InputStream credentialsStream = new ClassPathResource(credentialsResource).getInputStream()) {
            FirebaseOptions.Builder optionsBuilder = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentialsStream));

            if (!projectId.isBlank()) {
                optionsBuilder.setProjectId(projectId);
            }

            FirebaseApp app;
            if (FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(optionsBuilder.build());
            } else {
                app = FirebaseApp.getApps().get(0);
            }

            firebaseAuth = FirebaseAuth.getInstance(app);
            return null;
        } catch (Exception e) {
            return "No se pudo inicializar Firebase Admin en el backend: " + e.getMessage();
        }
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
}
