package Proyecto_Ibernovia.Proyecto_Ibernovia.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data // Esto genera getters y setters automáticamente con Lombok
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;    // Ej: "liga-novia-encaje-blanco"
    private String categoria; // Ej: "Ligas", "Pendientes", "Gemelos"
    private Double precio;
    private String imagen;    // Nombre del archivo: "Página1.jpg"

    @Column(length = 500)
    private String descripcion;
}