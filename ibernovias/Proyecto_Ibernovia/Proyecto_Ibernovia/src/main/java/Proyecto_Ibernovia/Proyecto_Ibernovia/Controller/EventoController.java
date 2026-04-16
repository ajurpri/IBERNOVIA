package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.EventoDTO;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Evento;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.EventoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class EventoController {

    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @GetMapping("/eventos")
    public ResponseEntity<?> listEventos() {
        try {
            List<EventoDTO> eventos = eventoRepository.findByFechaAfterOrderByFechaAsc(LocalDateTime.now()).stream()
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
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }
}
