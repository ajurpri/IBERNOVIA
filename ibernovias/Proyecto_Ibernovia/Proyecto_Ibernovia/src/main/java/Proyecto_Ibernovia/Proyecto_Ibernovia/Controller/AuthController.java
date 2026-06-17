package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.LoginRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.LoginResponse;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.RegisterRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.FirebaseTokenVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseTokenVerifier firebaseTokenVerifier;

    @org.springframework.beans.factory.annotation.Value("${app.business.codes:EMPRESA2025,IBERNOVIA2025,DEMO123}")
    private String businessCodesString;

    public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, FirebaseTokenVerifier firebaseTokenVerifier) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.firebaseTokenVerifier = firebaseTokenVerifier;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Validaciones básicas
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("Las contraseñas no coinciden");
            }

            if (request.getPassword().length() < 6) {
                return ResponseEntity.badRequest().body("La contraseña debe tener al menos 6 caracteres");
            }

            if (usuarioRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body("El email ya está registrado");
            }

            // Crear nuevo usuario
            Usuario usuario = new Usuario(
                    request.getEmail(),
                    request.getNombre(),
                    request.getApellido(),
                    passwordEncoder.encode(request.getPassword())
            );

            Usuario savedUsuario = usuarioRepository.save(usuario);

            // Generar token
            String token = jwtUtil.generateToken(savedUsuario.getId(), savedUsuario.getEmail());

                LoginResponse response = new LoginResponse(
                    token,
                    savedUsuario.getId(),
                    savedUsuario.getEmail(),
                    savedUsuario.getNombre(),
                    savedUsuario.getApellido(),
                    savedUsuario.getIsAdmin(),
                    savedUsuario.getIsBusiness()
                );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el registro: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(request.getEmail());

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Email o contraseña incorrectos");
            }

            Usuario usuario = usuarioOptional.get();

            // Verificar contraseña
            if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Email o contraseña incorrectos");
            }

            // Verificar si usuario está activo
            if (!usuario.getActivo()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario inactivo");
            }

            // Generar token
            String token = jwtUtil.generateToken(usuario.getId(), usuario.getEmail());

                LoginResponse response = new LoginResponse(
                    token,
                    usuario.getId(),
                    usuario.getEmail(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getIsAdmin(),
                    usuario.getIsBusiness()
                );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el login: " + e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no proporcionado");
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        Long userId = jwtUtil.extractUserIdFromToken(token);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(userId);

        if (usuarioOptional.isEmpty() || !usuarioOptional.get().getActivo()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        LoginResponse response = new LoginResponse(
            token,
            usuario.getId(),
            usuario.getEmail(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getIsAdmin(),
            usuario.getIsBusiness()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // El logout se maneja en el frontend (eliminar token)
        return ResponseEntity.ok("Sesión cerrada");
    }

    @PostMapping("/firebase-login")
    public ResponseEntity<?> firebaseLogin(@RequestBody java.util.Map<String, String> body) {
        try {
            String idToken = body.get("token");
            if (idToken == null || idToken.isBlank()) {
                return ResponseEntity.badRequest().body("Token de Firebase requerido");
            }

            io.jsonwebtoken.Claims claims = firebaseTokenVerifier.verifyToken(idToken);
            if (claims == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de Firebase inválido o expirado");
            }

            String email = claims.get("email", String.class);
            if (email == null || email.isBlank()) {
                return ResponseEntity.badRequest().body("El token no contiene un email válido");
            }

            // Buscar o crear usuario
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
            Usuario usuario;
            
            // Determinar si es admin predefinido
            boolean isAdminEmail = false;
            String adminEmailsEnv = System.getenv("VITE_ADMIN_EMAILS");
            if (adminEmailsEnv == null) {
                adminEmailsEnv = System.getenv("ADMIN_EMAILS");
            }
            if (adminEmailsEnv != null && !adminEmailsEnv.isBlank()) {
                for (String adminEmail : adminEmailsEnv.split(",")) {
                    if (adminEmail.trim().equalsIgnoreCase(email.trim())) {
                        isAdminEmail = true;
                        break;
                    }
                }
            }
            if (!isAdminEmail) {
                isAdminEmail = "admin@ibernovia.com".equalsIgnoreCase(email)
                        || "dam@ibernovia.com".equalsIgnoreCase(email)
                        || "comercial@ibernovia.com".equalsIgnoreCase(email);
            }

            if (usuarioOptional.isEmpty()) {
                // Registro automático
                String name = claims.get("name", String.class);
                if (name == null || name.isBlank()) {
                    name = email.split("@")[0];
                }
                String nombre = name;
                String apellido = "";
                
                int spaceIndex = name.indexOf(" ");
                if (spaceIndex > 0) {
                    nombre = name.substring(0, spaceIndex);
                    apellido = name.substring(spaceIndex + 1);
                }

                String randomPassword = java.util.UUID.randomUUID().toString();
                usuario = new Usuario(
                        email,
                        nombre,
                        apellido,
                        passwordEncoder.encode(randomPassword)
                );
                
                if (isAdminEmail) {
                    usuario.setIsAdmin(true);
                }
                
                usuario = usuarioRepository.save(usuario);
            } else {
                usuario = usuarioOptional.get();
                if (!usuario.getActivo()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario inactivo");
                }
                
                if (isAdminEmail && !Boolean.TRUE.equals(usuario.getIsAdmin())) {
                    usuario.setIsAdmin(true);
                    usuario = usuarioRepository.save(usuario);
                }
            }

            String token = jwtUtil.generateToken(usuario.getId(), usuario.getEmail());

            LoginResponse response = new LoginResponse(
                    token,
                    usuario.getId(),
                    usuario.getEmail(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getIsAdmin(),
                    usuario.getIsBusiness()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la autenticación con Firebase: " + e.getMessage());
        }
    }

    @PostMapping("/validar-codigo")
    public ResponseEntity<?> validarCodigo(@RequestBody java.util.Map<String, String> body) {
        try {
            String codigo = body.get("codigo");
            if (codigo == null || codigo.isBlank()) {
                return ResponseEntity.badRequest().body(java.util.Map.of("success", false, "message", "Código requerido"));
            }
            String trimmed = codigo.trim();
            if (businessCodesString != null) {
                for (String code : businessCodesString.split(",")) {
                    if (code.trim().equalsIgnoreCase(trimmed)) {
                        return ResponseEntity.ok(java.util.Map.of("success", true));
                    }
                }
            }
            return ResponseEntity.ok(java.util.Map.of("success", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("success", false, "message", "Error al validar código: " + e.getMessage()));
        }
    }
}
