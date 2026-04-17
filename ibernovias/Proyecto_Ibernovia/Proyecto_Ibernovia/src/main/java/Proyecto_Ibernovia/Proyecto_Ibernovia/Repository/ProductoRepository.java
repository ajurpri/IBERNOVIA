package Proyecto_Ibernovia.Proyecto_Ibernovia.Repository;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByFamilia(String familia);
    List<Producto> findByActivoTrue();
    List<Producto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);
}