package Proyecto_Ibernovia.Proyecto_Ibernovia.Repository;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findAllByOrderByFechaCreacionDesc();
    List<ContactMessage> findByLeidoOrderByFechaCreacionDesc(Boolean leido);
    List<ContactMessage> findByEmpresaContainingIgnoreCaseOrderByFechaCreacionDesc(String empresa);
}
