package Proyecto_Ibernovia.Proyecto_Ibernovia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private String lugar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
