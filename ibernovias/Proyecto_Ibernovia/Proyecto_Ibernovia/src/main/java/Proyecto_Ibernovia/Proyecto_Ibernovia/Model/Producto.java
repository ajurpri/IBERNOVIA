package Proyecto_Ibernovia.Proyecto_Ibernovia.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Familia (nivel alto): Novia / Novio / Fiesta / Comunión / Arras
    private String familia;

    // Categoría (nivel tipo): Ligas, Pendientes, ...
    private String categoria;

    private Double precio;
    private String imagen;
    
    @Column(length = 500)
    private String descripcion;
    
    private Integer stock;
    private Boolean activo;
    
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
        // El stock se genera en el controller, no aquí
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}