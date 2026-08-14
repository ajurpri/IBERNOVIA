package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping({"/api/health", "/"})
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "message", "Ibernovia backend is running smoothly"
        ));
    }
}
