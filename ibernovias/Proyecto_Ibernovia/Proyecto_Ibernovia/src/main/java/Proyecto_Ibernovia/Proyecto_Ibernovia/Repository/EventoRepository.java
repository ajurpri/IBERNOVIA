package Proyecto_Ibernovia.Proyecto_Ibernovia.Repository;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByFechaAfterOrderByFechaAsc(LocalDateTime fecha);
}
