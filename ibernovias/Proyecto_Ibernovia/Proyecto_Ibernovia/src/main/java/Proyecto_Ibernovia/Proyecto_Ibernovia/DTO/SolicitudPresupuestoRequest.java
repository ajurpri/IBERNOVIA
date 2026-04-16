package Proyecto_Ibernovia.Proyecto_Ibernovia.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudPresupuestoRequest {
    private String empresaNombre;
    private String personaContacto;
    private String email;
    private String telefono;
    private String productosSolicitados;
    private String notas;
}
