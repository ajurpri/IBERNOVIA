package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.DTO.ContactRequest;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.ContactMessage;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contacto")
public class ContactoController {

    private final ContactMessageService contactMessageService;

    public ContactoController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> enviarContacto(@Valid @RequestBody ContactRequest request) {
        try {
            ContactMessage saved = contactMessageService.saveContactMessage(request);
            return ResponseEntity.ok(Map.of(
                "message", "Solicitud guardada correctamente",
                "id", saved.getId().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al guardar la solicitud"));
        }
    }

    // Endpoint para admin - listar todos los mensajes
    @GetMapping("/admin/all")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(contactMessageService.getAllMessages());
    }

    // Endpoint para admin - listar sin leer
    @GetMapping("/admin/unread")
    public ResponseEntity<List<ContactMessage>> getUnreadMessages() {
        return ResponseEntity.ok(contactMessageService.getUnreadMessages());
    }

    // Endpoint para admin - obtener detalle
    @GetMapping("/admin/{id}")
    public ResponseEntity<ContactMessage> getMessageDetail(@PathVariable Long id) {
        return contactMessageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para admin - marcar como leído
    @PutMapping("/admin/{id}/read")
    public ResponseEntity<ContactMessage> markAsRead(@PathVariable Long id) {
        ContactMessage updated = contactMessageService.markAsRead(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para admin - agregar nota
    @PutMapping("/admin/{id}/note")
    public ResponseEntity<ContactMessage> addNote(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String note = body.get("nota");
        ContactMessage updated = contactMessageService.addNote(id, note);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
}
