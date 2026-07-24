package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.AdminUpdateRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.AdminUserResponse;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.EventoDTO;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.PromocionDTO;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.SolicitudPresupuestoDTO;
import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.SolicitudPresupuestoRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Evento;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Promocion;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.SolicitudPresupuesto;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.EventoRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.PromocionRepository;
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
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final SolicitudPresupuestoRepository solicitudRepository;
    private final PromocionRepository promocionRepository;
    private final JwtUtil jwtUtil;

    public AdminController(UsuarioRepository usuarioRepository, EventoRepository eventoRepository, 
                          SolicitudPresupuestoRepository solicitudRepository, PromocionRepository promocionRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.solicitudRepository = solicitudRepository;
        this.promocionRepository = promocionRepository;
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
                        u.getIsBusiness(),
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
        if (request.getIsBusiness() != null) {
            user.setIsBusiness(request.getIsBusiness());
        }

        Usuario saved = usuarioRepository.save(user);
        AdminUserResponse response = new AdminUserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getNombre(),
                saved.getApellido(),
                saved.getActivo(),
                saved.getIsAdmin(),
                saved.getIsBusiness(),
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

    // ===== PROMOCIONES =====
    @GetMapping("/promociones")
    public ResponseEntity<?> listPromocionesAdmin(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        List<PromocionDTO> promos = promocionRepository.findAll().stream()
                .map(p -> new PromocionDTO(
                        p.getId(),
                        p.getTitulo(),
                        p.getDescripcion(),
                        p.getDescuento(),
                        p.getCodigo(),
                        p.getImagen(),
                        p.getActivo(),
                        p.getFechaFin(),
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(promos);
    }

    @PostMapping("/promociones")
    public ResponseEntity<?> createPromocion(
            @RequestBody PromocionDTO dto,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Promocion promo = new Promocion();
        promo.setTitulo(dto.getTitulo());
        promo.setDescripcion(dto.getDescripcion());
        promo.setDescuento(dto.getDescuento());
        promo.setCodigo(dto.getCodigo());
        promo.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        promo.setFechaFin(dto.getFechaFin());

        if (dto.getImagen() != null && dto.getImagen().startsWith("data:image/")) {
            String processed = processBase64Image(dto.getImagen());
            promo.setImagen(processed);
        } else {
            promo.setImagen(dto.getImagen());
        }

        Promocion saved = promocionRepository.save(promo);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
    }

    @PutMapping("/promociones/{id}")
    public ResponseEntity<?> updatePromocion(
            @PathVariable Long id,
            @RequestBody PromocionDTO dto,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Optional<Promocion> promoOpt = promocionRepository.findById(id);
        if (promoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Promocion promo = promoOpt.get();
        promo.setTitulo(dto.getTitulo());
        promo.setDescripcion(dto.getDescripcion());
        promo.setDescuento(dto.getDescuento());
        promo.setCodigo(dto.getCodigo());
        if (dto.getActivo() != null) {
            promo.setActivo(dto.getActivo());
        }
        promo.setFechaFin(dto.getFechaFin());

        if (dto.getImagen() != null) {
            if (dto.getImagen().startsWith("data:image/")) {
                String processed = processBase64Image(dto.getImagen());
                promo.setImagen(processed);
            } else {
                promo.setImagen(dto.getImagen());
            }
        }

        Promocion saved = promocionRepository.save(promo);
        return ResponseEntity.ok(convertToDTO(saved));
    }

    @DeleteMapping("/promociones/{id}")
    public ResponseEntity<?> deletePromocion(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (getAdminFromToken(authHeader).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Optional<Promocion> promoOpt = promocionRepository.findById(id);
        if (promoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        promocionRepository.deleteById(id);
        return ResponseEntity.ok("Promoción eliminada correctamente");
    }

    private PromocionDTO convertToDTO(Promocion p) {
        return new PromocionDTO(
                p.getId(),
                p.getTitulo(),
                p.getDescripcion(),
                p.getDescuento(),
                p.getCodigo(),
                p.getImagen(),
                p.getActivo(),
                p.getFechaFin(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }

    private String processBase64Image(String base64Image) {
        try {
            if (base64Image == null || !base64Image.startsWith("data:image/")) {
                return base64Image;
            }

            Path imagesDir = Paths.get("public/images/promociones");
            Files.createDirectories(imagesDir);

            String[] parts = base64Image.split(",");
            if (parts.length != 2) return base64Image;

            String dataBase64 = parts[1];
            byte[] decodedBytes = Base64.getDecoder().decode(dataBase64);

            String mimeType = parts[0];
            String extension = "jpg";
            if (mimeType.contains("png")) extension = "png";
            else if (mimeType.contains("gif")) extension = "gif";
            else if (mimeType.contains("webp")) extension = "webp";

            String fileName = "promo_" + System.currentTimeMillis() + "." + extension;
            Path filePath = imagesDir.resolve(fileName);

            Files.write(filePath, decodedBytes);

            return "/images/promociones/" + fileName;

        } catch (Exception e) {
            System.err.println("Error procesando imagen de promocion base64: " + e.getMessage());
            return null;
        }
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
