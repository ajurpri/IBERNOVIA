package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/setup")
public class AdminSetupController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSetupController.class);

    private final UsuarioRepository usuarioRepository;

    public AdminSetupController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Crear primer admin (solo funciona si no hay admins)
    @PostMapping("/create-admin")
    public ResponseEntity<Map<String, Object>> createFirstAdmin(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String nombre = body.get("nombre");
            String password = body.get("password");

            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "Email y contraseña requeridos"));
            }

            // Verificar que no existan admins
            long adminCount = usuarioRepository.findAll().stream()
                    .filter(Usuario::getIsAdmin)
                    .count();

            if (adminCount > 0) {
                logger.warn("⚠️ Intento de crear admin cuando ya existen admins");
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("success", false, "message", "Ya existen administradores"));
            }

            // Verificar que no exista usuario con ese email
            Optional<Usuario> existing = usuarioRepository.findByEmail(email);
            if (existing.isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "El email ya existe"));
            }

            // Crear usuario admin
            Usuario adminUser = new Usuario();
            adminUser.setEmail(email);
            adminUser.setNombre(nombre != null && !nombre.isEmpty() ? nombre : "Administrador");
            adminUser.setApellido(""); // Por defecto vacío, se puede actualizar luego
            adminUser.setPassword(password); // En producción: usar BCrypt
            adminUser.setIsAdmin(true);
            adminUser.setActivo(true);

            Usuario saved = usuarioRepository.save(adminUser);

            logger.info("✅ Administrador creado: {} ({})", saved.getId(), saved.getEmail());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "✓ Admin creado correctamente",
                    "admin", Map.of(
                            "id", saved.getId(),
                            "email", saved.getEmail(),
                            "nombre", saved.getNombre()
                    )
            ));

        } catch (Exception e) {
            logger.error("❌ Error al crear admin: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error: " + e.getMessage()));
        }
    }

    // Verificar si hay admins
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAdminExists() {
        long adminCount = usuarioRepository.findAll().stream()
                .filter(Usuario::getIsAdmin)
                .count();

        return ResponseEntity.ok(Map.of(
                "hasAdmin", adminCount > 0,
                "adminCount", adminCount
        ));
    }

    // Crear admin adicional (sin restricción de admins previos)
    @PostMapping("/create-admin-adicional")
    public ResponseEntity<Map<String, Object>> createAdditionalAdmin(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String nombre = body.get("nombre");
            String password = body.get("password");

            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "Email y contraseña requeridos"));
            }

            // Verificar que no exista usuario con ese email
            Optional<Usuario> existing = usuarioRepository.findByEmail(email);
            if (existing.isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "⚠️ El email ya existe en el sistema"));
            }

            // Crear usuario admin
            Usuario adminUser = new Usuario();
            adminUser.setEmail(email);
            adminUser.setNombre(nombre != null && !nombre.isEmpty() ? nombre : "Administrador");
            adminUser.setApellido(""); // Por defecto vacío
            adminUser.setPassword(password); // En producción: usar BCrypt
            adminUser.setIsAdmin(true);
            adminUser.setActivo(true);

            Usuario saved = usuarioRepository.save(adminUser);

            logger.info("✅ Admin adicional creado: {} ({})", saved.getId(), saved.getEmail());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "✓ Admin adicional creado correctamente",
                    "admin", Map.of(
                            "id", saved.getId(),
                            "email", saved.getEmail(),
                            "nombre", saved.getNombre()
                    )
            ));

        } catch (Exception e) {
            logger.error("❌ Error al crear admin adicional: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error: " + e.getMessage()));
        }
    }
}
