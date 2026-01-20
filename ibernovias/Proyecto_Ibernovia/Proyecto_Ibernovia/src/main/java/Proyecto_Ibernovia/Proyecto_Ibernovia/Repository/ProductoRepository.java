package Proyecto_Ibernovia.Proyecto_Ibernovia.Repository;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí podrías añadir métodos como findByCategoria(String cat) más adelante
}