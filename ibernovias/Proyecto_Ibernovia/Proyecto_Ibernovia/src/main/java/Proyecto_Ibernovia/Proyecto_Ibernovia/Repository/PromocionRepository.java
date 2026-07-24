package Proyecto_Ibernovia.Proyecto_Ibernovia.Repository;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    // Buscar promociones activas ordenadas por id descendiente (las más nuevas primero)
    List<Promocion> findByActivoTrueOrderByIdDesc();
}
