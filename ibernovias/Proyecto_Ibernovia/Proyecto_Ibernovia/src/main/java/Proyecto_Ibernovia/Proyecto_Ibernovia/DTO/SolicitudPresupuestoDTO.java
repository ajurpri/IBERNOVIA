package Proyecto_Ibernovia.Proyecto_Ibernovia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudPresupuestoDTO {
    private Long id;
    private String empresaNombre;
    private String personaContacto;
    private String email;
    private String telefono;
    private String productosSolicitados;
    private String notas;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
