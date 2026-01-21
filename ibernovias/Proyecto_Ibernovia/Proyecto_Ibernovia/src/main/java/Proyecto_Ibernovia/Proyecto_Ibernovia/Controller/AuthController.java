package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.LoginRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.LoginResponse;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.RegisterRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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
                    savedUsuario.getApellido()
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
                    usuario.getApellido()
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
        String email = jwtUtil.extractEmailFromToken(token);

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
                usuario.getApellido()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // El logout se maneja en el frontend (eliminar token)
        return ResponseEntity.ok("Sesión cerrada");
    }
}
