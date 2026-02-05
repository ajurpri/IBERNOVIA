package Proyecto_Ibernovia.Proyecto_Ibernovia.Service;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String contactTo;
    private final String contactFrom;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${contact.mail.to}") String contactTo,
            @Value("${contact.mail.from}") String contactFrom) {
        this.mailSender = mailSender;
        this.contactTo = contactTo;
        this.contactFrom = contactFrom;
    }

    public void sendContactEmail(ContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contactTo);
        message.setFrom(contactFrom);
        message.setReplyTo(request.getEmail());
        message.setSubject("Contacto: " + request.getAsunto());
        message.setText(buildBody(request));
        mailSender.send(message);
    }

    private String buildBody(ContactRequest request) {
        return "Nuevo mensaje desde el formulario de contacto" +
                "\n\nNombre: " + request.getNombre() +
                "\nEmail: " + request.getEmail() +
                "\nAsunto: " + request.getAsunto() +
                "\n\nMensaje:\n" + request.getMensaje();
    }
}
