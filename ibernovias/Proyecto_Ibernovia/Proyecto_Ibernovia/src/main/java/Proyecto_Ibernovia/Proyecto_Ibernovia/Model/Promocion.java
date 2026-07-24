package Proyecto_Ibernovia.Proyecto_Ibernovia.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "promociones")
@Data
@NoArgsConstructor
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    // Descuento opcional, ej: "15% DTO", "Regalo exclusivo"
    private String descuento;

    // Código de cupón opcional, ej: "NOVIA2026"
    private String codigo;

    // Ruta de la imagen (ej: /images/promociones/promo_123.jpg)
    private String imagen;

    @Column(name = "precio_original")
    private Double precioOriginal;

    @Column(name = "precio_oferta")
    private Double precioOferta;

    // Indica si la promoción está habilitada
    @Column(nullable = false)
    private Boolean activo = true;

    // Fecha límite opcional para expirar la promoción
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (activo == null) {
            activo = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
