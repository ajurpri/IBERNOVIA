package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Producto;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.ProductoRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    @Value("${app.gemini.api-key:}")
    private String geminiApiKey;

    @Value("${app.business.codes:EMPRESA2025,IBERNOVIA2025,DEMO123}")
    private String businessCodesString;

    public ChatController(ProductoRepository productoRepository, UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> chat(
            @RequestBody Map<String, Object> body,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-Business-Code", required = false) String businessCodeHeader
    ) {
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, String>> history = (List<Map<String, String>>) body.get("history");
            if (history == null || history.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("reply", "El historial de chat está vacío o no se ha proporcionado."));
            }

            // 1. Determinar rol / visibilidad de precios
            boolean canSeePrices = checkCanSeePrices(authHeader, businessCodeHeader);

            // 2. Construir contexto del sistema
            List<Producto> productos = productoRepository.findAll();
            String systemInstructions = buildSystemInstructions(productos, canSeePrices);

            // 3. Si no hay clave API de Gemini configurada, retornar respuesta en modo demo
            if (geminiApiKey == null || geminiApiKey.isBlank()) {
                return ResponseEntity.ok(Map.of(
                        "reply",
                        "[MODO DEMO] ¡Hola! Bienvenido al Atelier de Ibernovia. Estoy funcionando en modo de demostración porque no hay configurada una clave de API de Gemini (`GEMINI_API_KEY`) en el servidor. Configúrala en `application.properties` o como variable de entorno para chatear en vivo conmigo.\n\n" +
                        "¿Deseas conocer más sobre el atelier, nuestros horarios o ver nuestra selección de productos?"
                ));
            }

            // 4. Formatear JSON de envío para Gemini API usando Jackson
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();

            // Configurar instrucciones del sistema
            ObjectNode systemInstructionNode = mapper.createObjectNode();
            ArrayNode sysPartsNode = mapper.createArrayNode();
            ObjectNode sysPartNode = mapper.createObjectNode();
            sysPartNode.put("text", systemInstructions);
            sysPartsNode.add(sysPartNode);
            systemInstructionNode.set("parts", sysPartsNode);
            rootNode.set("systemInstruction", systemInstructionNode);

            // Configurar contenidos del chat (historial)
            ArrayNode contentsNode = mapper.createArrayNode();
            for (Map<String, String> msg : history) {
                String role = msg.get("role");
                String text = msg.get("text");

                if (text == null || text.isBlank()) continue;

                ObjectNode contentObj = mapper.createObjectNode();
                if ("client".equalsIgnoreCase(role) || "user".equalsIgnoreCase(role)) {
                    contentObj.put("role", "user");
                } else {
                    contentObj.put("role", "model");
                }

                ArrayNode partsNode = mapper.createArrayNode();
                ObjectNode partNode = mapper.createObjectNode();
                partNode.put("text", text);
                partsNode.add(partNode);
                contentObj.set("parts", partsNode);

                contentsNode.add(contentObj);
            }
            rootNode.set("contents", contentsNode);

            String jsonPayload = mapper.writeValueAsString(rootNode);

            // 5. Realizar petición HTTP a la API de Gemini
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(java.time.Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey))
                    .header("Content-Type", "application/json")
                    .timeout(java.time.Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                com.fasterxml.jackson.databind.JsonNode responseJson = mapper.readTree(response.body());
                String replyText = responseJson.path("candidates")
                        .path(0)
                        .path("content")
                        .path("parts")
                        .path(0)
                        .path("text")
                        .asText();
                return ResponseEntity.ok(Map.of("reply", replyText));
            } else {
                System.err.println("Gemini API Error Status: " + response.statusCode());
                System.err.println("Gemini API Error Body: " + response.body());
                return ResponseEntity.status(response.statusCode()).body(Map.of(
                        "reply",
                        "Lo siento, ha ocurrido un error al procesar tu consulta con el servicio de Inteligencia Artificial de Google. (Código HTTP: " + response.statusCode() + ")"
                ));
            }

        } catch (Exception e) {
            System.err.println("❌ ERROR EN CHAT CONTROLLER:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "reply",
                    "Ha ocurrido un error inesperado al procesar el chat: " + e.getMessage()
            ));
        }
    }

    private boolean checkCanSeePrices(String authHeader, String businessCodeHeader) {
        if (businessCodeHeader != null && businessCodesString != null) {
            String trimmedHeader = businessCodeHeader.trim();
            for (String code : businessCodesString.split(",")) {
                if (code.trim().equalsIgnoreCase(trimmedHeader)) {
                    return true;
                }
            }
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.extractUserIdFromToken(token);
                if (userId != null) {
                    Optional<Usuario> userOpt = usuarioRepository.findById(userId);
                    if (userOpt.isPresent()) {
                        Usuario user = userOpt.get();
                        return Boolean.TRUE.equals(user.getIsAdmin()) || Boolean.TRUE.equals(user.getIsBusiness());
                    }
                }
            }
        }
        return false;
    }

    private String buildSystemInstructions(List<Producto> productos, boolean canSeePrices) {
        StringBuilder sb = new StringBuilder();
        sb.append("Eres el Asistente Digital Concierge de Ibernovia Atelier, una firma de alta costura de vestidos de novia, trajes de fiesta y complementos de lujo.\n\n");
        sb.append("PAUTAS DE COMPORTAMIENTO:\n");
        sb.append("- Habla siempre en español con un tono elegante, respetuoso, cálido y refinado, adecuado para una boutique de lujo.\n");
        sb.append("- Sé claro, conciso y ayuda al usuario a encontrar lo que busca.\n");
        sb.append("- Si el usuario te pregunta por temas totalmente ajenos a Ibernovia (como política, deportes, programación, o te pide escribir poemas), redirige cortés y elegantemente la conversación de vuelta al Atelier.\n");
        sb.append("- En ningún caso inventes productos que no estén listados en el catálogo provisto abajo.\n\n");

        sb.append("INFORMACIÓN DE CONTACTO Y ATELIER:\n");
        sb.append("- Dirección: Plaza de Abastos, C. Ollerías, 48, 23740 Andújar, Jaén (España).\n");
        sb.append("- Teléfono: 953 51 50 70.\n");
        sb.append("- Correo Electrónico: info@ibernovia.es.\n");
        sb.append("- Horarios de atención: Lunes a viernes de 9:00 a 14:00 y de 17:00 a 20:00. Sábados y domingos cerrado.\n");
        sb.append("- Cita Previa: Para una experiencia óptima y exclusiva, recomendamos concertar una cita previa a través de nuestra sección de Contacto.\n\n");

        if (canSeePrices) {
            sb.append("ACCESO A PRECIOS AUTORIZADO (Clientes Profesionales/Empresas):\n");
            sb.append("- Tienes permiso para mostrar y discutir precios de los productos.\n");
            sb.append("- A continuación se detalla la selección de productos activos junto a su precio profesional:\n\n");
        } else {
            sb.append("ACCESO A PRECIOS DENEGADO (Público General):\n");
            sb.append("- IMPORTANTE: NO tienes permiso para revelar precios de productos.\n");
            sb.append("- Si el cliente pregunta por el precio de cualquier producto o pide presupuestos, indícale de forma educada y sutil que los precios son confidenciales y exclusivos para profesionales. Explícale que para visualizarlos debe registrarse como profesional en la plataforma o introducir un código de acceso válido en el modal de 'Acceso Empresarial'.\n");
            sb.append("- A continuación se detallan los productos del catálogo (los precios han sido eliminados de tu contexto por seguridad):\n\n");
        }

        for (Producto p : productos) {
            if (Boolean.FALSE.equals(p.getActivo())) continue;
            sb.append("- ID: ").append(p.getId())
              .append(" | Nombre: ").append(p.getNombre())
              .append(" | Categoría: ").append(p.getCategoria())
              .append(" | Familia: ").append(p.getFamilia())
              .append(" | Descripción: ").append(p.getDescripcion() != null ? p.getDescripcion() : "Sin descripción adicional.");
            
            if (canSeePrices && p.getPrecio() != null) {
                sb.append(" | Precio: ").append(p.getPrecio()).append(" €");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
