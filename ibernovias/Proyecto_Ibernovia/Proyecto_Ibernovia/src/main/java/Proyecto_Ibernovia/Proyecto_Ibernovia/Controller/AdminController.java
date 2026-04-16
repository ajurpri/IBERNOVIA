package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.AdminUpdateRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.AdminUserResponse;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.EventoDTO;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.SolicitudPresupuestoDTO;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.SolicitudPresupuestoRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Evento;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.SolicitudPresupuesto;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.EventoRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.SolicitudPresupuestoRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final SolicitudPresupuestoRepository solicitudRepository;
    private final JwtUtil jwtUtil;

    public AdminController(UsuarioRepository usuarioRepository, EventoRepository eventoRepository, 
                          SolicitudPresupuestoRepository solicitudRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.solicitudRepository = solicitudRepository;
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

    // ===== EVENTOS =====
    @GetMapping("/eventos")
    public ResponseEntity<?> listEventos(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        List<EventoDTO> eventos = eventoRepository.findAll().stream()
                .map(e -> new EventoDTO(
                        e.getId(),
                        e.getTitulo(),
                        e.getDescripcion(),
                        e.getFecha(),
                        e.getLugar(),
                        e.getCreatedAt(),
                        e.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventos);
    }

    @PostMapping("/eventos")
    public ResponseEntity<?> createEvento(
            @RequestBody EventoDTO eventoDTO,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Evento evento = new Evento();
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFecha(eventoDTO.getFecha());
        evento.setLugar(eventoDTO.getLugar());

        Evento saved = eventoRepository.save(evento);
        
        EventoDTO response = new EventoDTO(
                saved.getId(),
                saved.getTitulo(),
                saved.getDescripcion(),
                saved.getFecha(),
                saved.getLugar(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/eventos/{id}")
    public ResponseEntity<?> updateEvento(
            @PathVariable Long id,
            @RequestBody EventoDTO eventoDTO,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Optional<Evento> eventoOpt = eventoRepository.findById(id);
        if (eventoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Evento evento = eventoOpt.get();
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFecha(eventoDTO.getFecha());
        evento.setLugar(eventoDTO.getLugar());

        Evento saved = eventoRepository.save(evento);
        
        EventoDTO response = new EventoDTO(
                saved.getId(),
                saved.getTitulo(),
                saved.getDescripcion(),
                saved.getFecha(),
                saved.getLugar(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<?> deleteEvento(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Optional<Evento> eventoOpt = eventoRepository.findById(id);
        if (eventoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        eventoRepository.deleteById(id);
        return ResponseEntity.ok("Evento eliminado correctamente");
    }

    // ===== SOLICITUDES DE PRESUPUESTO =====
    @GetMapping("/solicitudes")
    public ResponseEntity<?> listSolicitudes(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(defaultValue = "all") String estado
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        List<SolicitudPresupuestoDTO> solicitudes;
        if ("all".equals(estado)) {
            solicitudes = solicitudRepository.findAllByOrderByCreatedAtDesc().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            solicitudes = solicitudRepository.findByEstadoOrderByCreatedAtDesc(estado).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(solicitudes);
    }

    @PostMapping("/solicitudes")
    public ResponseEntity<?> createSolicitud(@RequestBody SolicitudPresupuestoRequest request) {
        try {
            SolicitudPresupuesto solicitud = new SolicitudPresupuesto();
            solicitud.setEmpresaNombre(request.getEmpresaNombre());
            solicitud.setPersonaContacto(request.getPersonaContacto());
            solicitud.setEmail(request.getEmail());
            solicitud.setTelefono(request.getTelefono());
            solicitud.setProductosSolicitados(request.getProductosSolicitados());
            solicitud.setNotas(request.getNotas());
            solicitud.setEstado("pendiente");

            SolicitudPresupuesto saved = solicitudRepository.save(solicitud);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear solicitud");
        }
    }

    @PutMapping("/solicitudes/{id}/estado")
    public ResponseEntity<?> updateEstadoSolicitud(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Optional<SolicitudPresupuesto> solicitudOpt = solicitudRepository.findById(id);
        if (solicitudOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SolicitudPresupuesto solicitud = solicitudOpt.get();
        String nuevoEstado = request.get("estado");
        if (nuevoEstado != null && !nuevoEstado.isEmpty()) {
            solicitud.setEstado(nuevoEstado);
        }

        SolicitudPresupuesto saved = solicitudRepository.save(solicitud);
        return ResponseEntity.ok(convertToDTO(saved));
    }

    private SolicitudPresupuestoDTO convertToDTO(SolicitudPresupuesto solicitud) {
        return new SolicitudPresupuestoDTO(
                solicitud.getId(),
                solicitud.getEmpresaNombre(),
                solicitud.getPersonaContacto(),
                solicitud.getEmail(),
                solicitud.getTelefono(),
                solicitud.getProductosSolicitados(),
                solicitud.getNotas(),
                solicitud.getEstado(),
                solicitud.getCreatedAt(),
                solicitud.getUpdatedAt()
        );
    }
}
