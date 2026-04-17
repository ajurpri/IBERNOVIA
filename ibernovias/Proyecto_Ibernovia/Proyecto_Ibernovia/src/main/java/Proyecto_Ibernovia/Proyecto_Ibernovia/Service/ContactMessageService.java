package Proyecto_Ibernovia.Proyecto_Ibernovia.Service;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.ContactRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.ContactMessage;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.ContactMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactMessageService {

    private static final Logger logger = LoggerFactory.getLogger(ContactMessageService.class);

    public record SaveResult(ContactMessage saved, boolean emailSent) {}

    private final ContactMessageRepository contactMessageRepository;
    private final EmailService emailService;

    public ContactMessageService(ContactMessageRepository contactMessageRepository, EmailService emailService) {
        this.contactMessageRepository = contactMessageRepository;
        this.emailService = emailService;
    }

    public SaveResult saveContactMessage(ContactRequest request) {
        ContactMessage message = new ContactMessage();
        message.setNombre(request.getNombre());
        message.setEmail(request.getEmail());
        message.setAsunto(request.getAsunto());
        message.setMensaje(request.getMensaje());

        ContactMessage saved = contactMessageRepository.save(message);
        logger.info("✅ Mensaje guardado en BD - ID: {}, De: {}, Asunto: {}",
            saved.getId(), saved.getEmail(), saved.getAsunto());

        boolean emailSent = emailService.sendContactEmail(request);
        if (!emailSent) {
            logger.warn("⚠️ Mensaje guardado, pero el correo NO se pudo enviar para ID {}", saved.getId());
        }

        return new SaveResult(saved, emailSent);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAllByOrderByFechaCreacionDesc();
    }

    public List<ContactMessage> getUnreadMessages() {
        return contactMessageRepository.findByLeidoOrderByFechaCreacionDesc(false);
    }

    public Optional<ContactMessage> getMessageById(Long id) {
        return contactMessageRepository.findById(id);
    }

    public ContactMessage markAsRead(Long id) {
        Optional<ContactMessage> messageOpt = contactMessageRepository.findById(id);
        if (messageOpt.isPresent()) {
            ContactMessage message = messageOpt.get();
            message.setLeido(true);
            return contactMessageRepository.save(message);
        }
        return null;
    }

    public ContactMessage addNote(Long id, String note) {
        Optional<ContactMessage> messageOpt = contactMessageRepository.findById(id);
        if (messageOpt.isPresent()) {
            ContactMessage message = messageOpt.get();
            message.setNotas(note);
            message.setRespondido(true);
            return contactMessageRepository.save(message);
        }
        return null;
    }

    public void deleteMessage(Long id) {
        contactMessageRepository.deleteById(id);
        logger.info("🗑️ Mensaje eliminado - ID: {}", id);
    }
}
