package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.PromocionDTO;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Promocion;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.PromocionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PromocionController {

    private final PromocionRepository promocionRepository;

    public PromocionController(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    @GetMapping("/promociones")
    public ResponseEntity<?> listPromociones() {
        try {
            List<PromocionDTO> activePromos = promocionRepository.findByActivoTrueOrderByIdDesc().stream()
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
            return ResponseEntity.ok(activePromos);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }
}
