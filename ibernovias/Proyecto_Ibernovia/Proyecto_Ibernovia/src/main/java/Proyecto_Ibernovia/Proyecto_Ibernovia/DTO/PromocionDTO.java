package Proyecto_Ibernovia.Proyecto_Ibernovia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String descuento;
    private String codigo;
    private String imagen;
    private Double precioOriginal;
    private Double precioOferta;
    private Boolean activo;
    private LocalDateTime fechaFin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
