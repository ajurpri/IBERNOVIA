package Proyecto_Ibernovia.Proyecto_Ibernovia.Service;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.ContactRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final String contactTo;
    private final String contactFrom;
    private final String smtpUsername;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${contact.mail.to}") String contactTo,
            @Value("${contact.mail.from}") String contactFrom,
            @Value("${spring.mail.username:}") String smtpUsername) {
        this.mailSender = mailSender;
        this.contactTo = contactTo;
        this.contactFrom = contactFrom;
        this.smtpUsername = smtpUsername;
        logConfiguration();
    }

    private void logConfiguration() {
        logger.info("📧 Email Service Initialized");
        logger.info("  - Contact To: {}", contactTo);
        logger.info("  - Contact From: {}", contactFrom);
        logger.info("  - SMTP Configured: {}", !smtpUsername.isEmpty());
    }

    public void sendContactEmail(ContactRequest request) {
        // Validar que SMTP esté configurado
        if (smtpUsername == null || smtpUsername.isEmpty()) {
            logger.warn("⚠️  SMTP not configured. Email would be sent to: {} with subject: {}", 
                contactTo, "Contacto: " + request.getAsunto());
            logger.warn("📝 Message preview:\n{}", buildBody(request));
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(contactTo);
            message.setFrom(contactFrom);
            message.setReplyTo(request.getEmail());
            message.setSubject("Contacto: " + request.getAsunto());
            message.setText(buildBody(request));
            mailSender.send(message);
            logger.info("✅ Email sent successfully to {} from {}", contactTo, request.getEmail());
        } catch (Exception e) {
            logger.error("❌ Error sending email: {}", e.getMessage(), e);
            throw new RuntimeException("Error al enviar correo: " + e.getMessage(), e);
        }
    }

    private String buildBody(ContactRequest request) {
        return "Nuevo mensaje desde el formulario de contacto" +
                "\n\nNombre: " + request.getNombre() +
                "\nEmail: " + request.getEmail() +
                "\nAsunto: " + request.getAsunto() +
                "\n\nMensaje:\n" + request.getMensaje();
    }
}
