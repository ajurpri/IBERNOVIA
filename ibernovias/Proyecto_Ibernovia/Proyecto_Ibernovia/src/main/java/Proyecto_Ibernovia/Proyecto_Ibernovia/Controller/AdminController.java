package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.AdminUpdateRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.AdminUserResponse;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AdminController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    private Optional<Usuario> getAdminFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return Optional.empty();
        }
        Long userId = jwtUtil.extractUserIdFromToken(token);
        if (userId == null) {
            return Optional.empty();
        }
        Optional<Usuario> user = usuarioRepository.findById(userId);
        if (user.isEmpty() || !Boolean.TRUE.equals(user.get().getIsAdmin())) {
            return Optional.empty();
        }
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<?> listUsers(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        List<AdminUserResponse> response = usuarioRepository.findAll().stream()
                .map(u -> new AdminUserResponse(
                        u.getId(),
                        u.getEmail(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getActivo(),
                        u.getIsAdmin(),
                        u.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody AdminUpdateRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Optional<Usuario> userOpt = usuarioRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario user = userOpt.get();
        if (request.getIsAdmin() != null) {
            user.setIsAdmin(request.getIsAdmin());
        }
        if (request.getActivo() != null) {
            user.setActivo(request.getActivo());
        }

        Usuario saved = usuarioRepository.save(user);
        AdminUserResponse response = new AdminUserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getNombre(),
                saved.getApellido(),
                saved.getActivo(),
                saved.getIsAdmin(),
                saved.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}
