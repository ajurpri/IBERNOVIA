package Proyecto_Ibernovia.Proyecto_Ibernovia.Repository;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.SolicitudPresupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudPresupuestoRepository extends JpaRepository<SolicitudPresupuesto, Long> {
    List<SolicitudPresupuesto> findByEstadoOrderByCreatedAtDesc(String estado);
    List<SolicitudPresupuesto> findAllByOrderByCreatedAtDesc();
}
