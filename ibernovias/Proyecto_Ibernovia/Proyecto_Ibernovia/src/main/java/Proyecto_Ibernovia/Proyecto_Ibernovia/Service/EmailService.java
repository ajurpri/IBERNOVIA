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

    private final String smtpHost;
    private final String smtpPort;
    private final String smtpUsername;
    private final String smtpPassword;

    private final boolean smtpSslEnabled;
    private final boolean smtpStartTlsEnabled;
    private final boolean smtpCheckServerIdentity;
    private final String smtpSslTrust;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${contact.mail.to}") String contactTo,
            @Value("${contact.mail.from}") String contactFrom,
            @Value("${spring.mail.host:}") String smtpHost,
            @Value("${spring.mail.port:}") String smtpPort,
            @Value("${spring.mail.username:}") String smtpUsername,
            @Value("${spring.mail.password:}") String smtpPassword,
            @Value("${spring.mail.properties.mail.smtp.ssl.enable:false}") boolean smtpSslEnabled,
            @Value("${spring.mail.properties.mail.smtp.starttls.enable:false}") boolean smtpStartTlsEnabled,
            @Value("${spring.mail.properties.mail.smtp.ssl.checkserveridentity:true}") boolean smtpCheckServerIdentity,
            @Value("${spring.mail.properties.mail.smtp.ssl.trust:}") String smtpSslTrust) {
        this.mailSender = mailSender;
        this.contactTo = contactTo;
        this.contactFrom = contactFrom;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
        this.smtpSslEnabled = smtpSslEnabled;
        this.smtpStartTlsEnabled = smtpStartTlsEnabled;
        this.smtpCheckServerIdentity = smtpCheckServerIdentity;
        this.smtpSslTrust = smtpSslTrust;
        logConfiguration();
    }

    private boolean smtpConfigured() {
        if (smtpUsername == null || smtpUsername.isBlank()) return false;
        if (smtpPassword == null || smtpPassword.isBlank()) return false;

        // Evitar intentar enviar con valores de ejemplo del application.properties
        if ("tu-email@gmail.com".equalsIgnoreCase(smtpUsername.trim())) return false;
        if ("tu-app-password".equals(smtpPassword.trim())) return false;

        return true;
    }

    private void logConfiguration() {
        logger.info("📧 Email Service Initialized");
        logger.info("  - Contact To: {}", contactTo);
        logger.info("  - Contact From: {}", contactFrom);
        logger.info("  - SMTP Host: {}:{}", smtpHost, smtpPort);
        logger.info("  - SMTP Username: {}", smtpUsername);
        logger.info("  - SMTP SSL Enabled: {}", smtpSslEnabled);
        logger.info("  - SMTP STARTTLS Enabled: {}", smtpStartTlsEnabled);
        logger.info("  - SMTP Check Server Identity: {}", smtpCheckServerIdentity);
        logger.info("  - SMTP SSL Trust: {}", smtpSslTrust == null ? "" : smtpSslTrust);
        logger.info("  - SMTP Configured: {}", smtpConfigured());
    }

    public boolean sendContactEmail(ContactRequest request) {
        if (!smtpConfigured()) {
            logger.warn("⚠️  SMTP not configured. Email would be sent to: {} with subject: {}",
                contactTo, "Contacto: " + request.getAsunto());
            logger.warn("📝 Message preview:\n{}", buildBody(request));
            return false;
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
            return true;
        } catch (Exception e) {
            logger.error("❌ Error sending email: {}", e.getMessage(), e);
            return false;
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
