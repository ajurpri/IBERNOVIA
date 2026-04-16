package Proyecto_Ibernovia.Proyecto_Ibernovia.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_presupuesto")
@Data
@NoArgsConstructor
public class SolicitudPresupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String empresaNombre;
    private String personaContacto;
    private String email;
    private String telefono;

    @Column(length = 1000)
    private String productosSolicitados; // JSON o descripción

    @Column(length = 500)
    private String notas; // Mensaje adicional del cliente

    private String estado; // pendiente, respondida, cancelada

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (estado == null) {
            estado = "pendiente";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
