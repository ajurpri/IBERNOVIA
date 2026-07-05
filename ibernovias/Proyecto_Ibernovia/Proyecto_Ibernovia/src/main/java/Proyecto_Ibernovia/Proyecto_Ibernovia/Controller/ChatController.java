package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Producto;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.ProductoRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                return ResponseEntity.badRequest().body(Map.of("reply", "El historial de chat esta vacio o no se ha proporcionado."));
            }

            List<Map<String, String>> sanitizedHistory = sanitizeHistory(history);
            if (sanitizedHistory.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("reply", "No he recibido suficiente contenido para ayudarte."));
            }

            boolean canSeePrices = checkCanSeePrices(authHeader, businessCodeHeader);
            List<Producto> productos = productoRepository.findAll();
            String latestUserMessage = extractLatestUserMessage(sanitizedHistory);
            String intent = detectIntent(latestUserMessage);
            List<Producto> suggestedProducts = selectPriorityProducts(productos, latestUserMessage).stream().limit(4).collect(Collectors.toList());
            String systemInstructions = buildSystemInstructions(productos, canSeePrices, latestUserMessage, intent);

            if (geminiApiKey == null || geminiApiKey.isBlank()) {
                return ResponseEntity.ok(buildChatResponse(
                        buildDemoReply(latestUserMessage, productos, canSeePrices, intent),
                        suggestedProducts,
                        canSeePrices
                ));
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();

            ObjectNode systemInstructionNode = mapper.createObjectNode();
            ArrayNode sysPartsNode = mapper.createArrayNode();
            ObjectNode sysPartNode = mapper.createObjectNode();
            sysPartNode.put("text", systemInstructions);
            sysPartsNode.add(sysPartNode);
            systemInstructionNode.set("parts", sysPartsNode);
            rootNode.set("systemInstruction", systemInstructionNode);

            ObjectNode generationConfigNode = mapper.createObjectNode();
            generationConfigNode.put("temperature", 0.55);
            generationConfigNode.put("topP", 0.9);
            generationConfigNode.put("topK", 32);
            generationConfigNode.put("maxOutputTokens", 700);
            rootNode.set("generationConfig", generationConfigNode);

            ArrayNode contentsNode = mapper.createArrayNode();
            for (Map<String, String> msg : sanitizedHistory) {
                String role = msg.get("role");
                String text = msg.get("text");
                if (text == null || text.isBlank()) {
                    continue;
                }

                ObjectNode contentObj = mapper.createObjectNode();
                contentObj.put("role", ("client".equalsIgnoreCase(role) || "user".equalsIgnoreCase(role)) ? "user" : "model");

                ArrayNode partsNode = mapper.createArrayNode();
                ObjectNode partNode = mapper.createObjectNode();
                partNode.put("text", text);
                partsNode.add(partNode);
                contentObj.set("parts", partsNode);

                contentsNode.add(contentObj);
            }
            rootNode.set("contents", contentsNode);

            String jsonPayload = mapper.writeValueAsString(rootNode);

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(12))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode responseJson = mapper.readTree(response.body());
                String replyText = responseJson.path("candidates")
                        .path(0)
                        .path("content")
                        .path("parts")
                        .path(0)
                        .path("text")
                        .asText();

                if (replyText == null || replyText.isBlank()) {
                    replyText = buildDemoReply(latestUserMessage, productos, canSeePrices, intent);
                }

                return ResponseEntity.ok(buildChatResponse(replyText, suggestedProducts, canSeePrices));
            }

            System.err.println("Gemini API Error Status: " + response.statusCode());
            System.err.println("Gemini API Error Body: " + response.body());
            return ResponseEntity.status(response.statusCode()).body(buildChatResponse(
                    "Lo siento, ha ocurrido un error al procesar tu consulta con el servicio de asistencia de Ibernovia.",
                    suggestedProducts,
                    canSeePrices
            ));
        } catch (Exception e) {
            System.err.println("ERROR EN CHAT CONTROLLER:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "reply",
                    "Ha ocurrido un error inesperado al procesar el chat. Si quieres, vuelve a intentarlo en unos instantes.",
                    "suggestedProducts",
                    List.of()
            ));
        }
    }

    private Map<String, Object> buildChatResponse(String reply, List<Producto> suggestedProducts, boolean canSeePrices) {
        return Map.of(
                "reply", reply,
                "suggestedProducts", suggestedProducts.stream()
                        .map(producto -> {
                            Map<String, Object> item = new LinkedHashMap<>();
                            item.put("id", producto.getId());
                            item.put("nombre", safeValue(producto.getNombre()));
                            item.put("categoria", safeValue(producto.getCategoria()));
                            item.put("familia", safeValue(producto.getFamilia()));
                            item.put("descripcion", safeValue(producto.getDescripcion()));
                            item.put("imagen", safeValue(producto.getImagen()));
                            item.put("precio", canSeePrices ? producto.getPrecio() : null);
                            return item;
                        })
                        .collect(Collectors.toList())
        );
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

    private List<Map<String, String>> sanitizeHistory(List<Map<String, String>> history) {
        List<Map<String, String>> sanitized = new ArrayList<>();
        int start = Math.max(0, history.size() - 12);

        for (int i = start; i < history.size(); i++) {
            Map<String, String> msg = history.get(i);
            if (msg == null) {
                continue;
            }

            String role = msg.getOrDefault("role", "user");
            String text = msg.get("text");
            if (text == null) {
                continue;
            }

            String normalized = text.replace("\r", "").trim();
            if (normalized.isBlank()) {
                continue;
            }

            if (normalized.length() > 1200) {
                normalized = normalized.substring(0, 1200);
            }

            sanitized.add(Map.of(
                    "role", role,
                    "text", normalized
            ));
        }

        return sanitized;
    }

    private String extractLatestUserMessage(List<Map<String, String>> history) {
        for (int i = history.size() - 1; i >= 0; i--) {
            Map<String, String> msg = history.get(i);
            String role = msg.get("role");
            if ("user".equalsIgnoreCase(role) || "client".equalsIgnoreCase(role)) {
                return msg.getOrDefault("text", "");
            }
        }

        return "";
    }

    private String buildSystemInstructions(List<Producto> productos, boolean canSeePrices, String latestUserMessage, String intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Eres el concierge digital de IBERNOVIA Atelier. Atiendes a clientes finales y profesionales con un tono elegante, sereno, cercano y experto.\n\n");
        sb.append("OBJETIVO:\n");
        sb.append("- Resolver la consulta con precision y tacto comercial.\n");
        sb.append("- Ayudar a descubrir productos reales del catalogo.\n");
        sb.append("- Guiar hacia cita previa, contacto o alta profesional cuando tenga sentido.\n\n");

        sb.append("ESTILO DE RESPUESTA:\n");
        sb.append("- Responde siempre en espanol.\n");
        sb.append("- Empieza con una respuesta directa de 1 o 2 frases.\n");
        sb.append("- Si recomiendas productos, menciona de 2 a 4 opciones concretas del catalogo y explica por que encajan.\n");
        sb.append("- Si la consulta es amplia, usa un bloque corto o una lista breve y limpia.\n");
        sb.append("- Adapta el enfoque segun la intencion: inspiracion de producto, informacion practica, acceso profesional o cita.\n");
        sb.append("- Cierra con una invitacion suave para seguir afinando, pedir cita o solicitar acceso profesional.\n");
        sb.append("- No inventes productos, stock, precios, horarios ni condiciones.\n");
        sb.append("- Si el tema no tiene que ver con Ibernovia, reconduce con elegancia al atelier y a su catalogo.\n\n");

        sb.append("REGLAS DE NEGOCIO:\n");
        sb.append("- Publico general: puedes orientar, recomendar y describir productos, pero no revelar precios.\n");
        sb.append("- Si preguntan por precio sin acceso profesional, explica que las tarifas son exclusivas para clientes profesionales autorizados.\n");
        sb.append("- Si preguntan por cita, contacto, horarios o ubicacion, responde con datos exactos.\n");
        sb.append("- Si no hay una coincidencia exacta en catalogo, dilo y redirige a la categoria o familia mas cercana.\n\n");

        sb.append("DATOS DEL ATELIER:\n");
        sb.append("- Direccion: Plaza de Abastos, C. Ollerias, 48, 23740 Andujar, Jaen, Espana.\n");
        sb.append("- Telefono: 953 51 50 70.\n");
        sb.append("- Email: info@ibernovia.es.\n");
        sb.append("- Horario: lunes a viernes de 9:00 a 14:00 y de 17:00 a 20:00. Sabados y domingos cerrado.\n");
        sb.append("- Cita previa: recomendable para una atencion mas personalizada.\n\n");

        sb.append("ULTIMA INTENCION DEL USUARIO:\n");
        if (latestUserMessage == null || latestUserMessage.isBlank()) {
            sb.append("- No indicada.\n\n");
        } else {
            sb.append("- ").append(latestUserMessage).append("\n\n");
        }

        sb.append("TIPO DE CONSULTA DETECTADO:\n");
        sb.append("- ").append(intent).append("\n\n");

        if (canSeePrices) {
            sb.append("VISIBILIDAD DE PRECIOS: AUTORIZADA.\n");
            sb.append("Puedes mencionar precios cuando aporten valor comercial.\n\n");
        } else {
            sb.append("VISIBILIDAD DE PRECIOS: NO AUTORIZADA.\n");
            sb.append("No reveles precios ni rangos. Invita al acceso profesional si hace falta.\n\n");
        }

        List<Producto> destacados = selectPriorityProducts(productos, latestUserMessage);
        List<Producto> activos = productos.stream()
                .filter(p -> !Boolean.FALSE.equals(p.getActivo()))
                .sorted(Comparator.comparing(p -> safeValue(p.getFamilia()) + "|" + safeValue(p.getCategoria()) + "|" + safeValue(p.getNombre())))
                .collect(Collectors.toList());

        sb.append("PRODUCTOS PRIORITARIOS PARA ESTA CONSULTA:\n");
        if (destacados.isEmpty()) {
            sb.append("- No se han detectado coincidencias claras; usa el catalogo general y sugiere la familia mas cercana.\n");
        } else {
            for (Producto p : destacados) {
                appendProductLine(sb, p, canSeePrices);
            }
        }

        sb.append("\nRESUMEN DE FAMILIAS ACTIVAS:\n");
        for (String familia : summarizeFamilies(activos)) {
            sb.append("- ").append(familia).append("\n");
        }

        sb.append("\nCATALOGO ACTIVO DE APOYO:\n");
        List<Producto> contextoReducido = activos.stream().limit(120).collect(Collectors.toList());

        for (Producto p : contextoReducido) {
            appendProductLine(sb, p, canSeePrices);
        }

        return sb.toString();
    }

    private String buildDemoReply(String latestUserMessage, List<Producto> productos, boolean canSeePrices, String intent) {
        String normalized = normalize(latestUserMessage);
        StringBuilder reply = new StringBuilder("[MODO DEMO] ");

        if (normalized.contains("horario") || normalized.contains("abri") || normalized.contains("cerr")) {
            reply.append("Nuestro horario es de lunes a viernes de 9:00 a 14:00 y de 17:00 a 20:00. Sabados y domingos permanecemos cerrados.\n\n");
            reply.append("Si quieres, tambien puedo orientarte sobre cita previa o recomendarte una linea de producto.");
            return reply.toString();
        }

        if (normalized.contains("cita") || normalized.contains("visita") || normalized.contains("probar")) {
            reply.append("Te recomendamos concertar cita previa para que la atencion en el atelier sea mas comoda y personalizada. Puedes gestionarla desde contacto o escribiendo a info@ibernovia.es.\n\n");
            reply.append("Si me dices si buscas novia, fiesta, comunion o complementos, te adelanto una seleccion antes de la visita.");
            return reply.toString();
        }

        if (normalized.contains("precio") || normalized.contains("cuanto") || normalized.contains("tarifa")) {
            if (canSeePrices) {
                reply.append("Tu perfil tiene acceso profesional, aunque en este modo demo no estoy generando la respuesta completa con IA. Si me dices el producto o la categoria exacta, te oriento con el catalogo disponible.");
            } else {
                reply.append("Las tarifas de Ibernovia se muestran solo a clientes profesionales autorizados. Si necesitas precios, puedes solicitar el alta profesional o acceder con tu codigo empresarial.\n\n");
                reply.append("Mientras tanto, si me indicas el estilo o el evento, si puedo recomendarte opciones del catalogo.");
            }
            return reply.toString();
        }

        List<Producto> matches = findRelevantProducts(productos, normalized, 4);
        if (!matches.isEmpty()) {
            reply.append(openingForIntent(intent));
            reply.append("\n\nHe localizado varias opciones del catalogo que encajan con tu consulta:\n\n");
            for (Producto p : matches) {
                reply.append("- ")
                        .append(safeValue(p.getNombre()))
                        .append(" (")
                        .append(safeValue(p.getCategoria()));
                if (!safeValue(p.getFamilia()).isBlank()) {
                    reply.append(" / ").append(safeValue(p.getFamilia()));
                }
                reply.append(")");
                if (!safeValue(p.getDescripcion()).isBlank()) {
                    reply.append(": ").append(safeValue(p.getDescripcion()));
                }
                if (canSeePrices && p.getPrecio() != null) {
                    reply.append(" Precio: ").append(p.getPrecio()).append(" EUR.");
                }
                reply.append("\n");
            }
            reply.append("\nSi quieres, afino la seleccion por categoria, estilo o tipo de evento.");
            return reply.toString();
        }

        reply.append("Puedo ayudarte con complementos para novia, fiesta, comunion o novio, asi como con horarios, cita previa y acceso profesional.\n\n");
        reply.append("Prueba con algo como: \"busco tocados elegantes para novia\", \"que horario teneis\" o \"como accedo a precios profesionales\".");
        return reply.toString();
    }

    private String detectIntent(String latestUserMessage) {
        String normalized = normalize(latestUserMessage);
        if (normalized.isBlank()) {
            return "consulta general";
        }
        if (containsAny(normalized, "precio", "tarifa", "cuanto", "coste", "presupuesto")) {
            return "consulta de precios";
        }
        if (containsAny(normalized, "cita", "visita", "probar", "probarme", "tienda", "atelier")) {
            return "solicitud de cita o visita";
        }
        if (containsAny(normalized, "horario", "abierto", "cerrado", "telefono", "direccion", "contacto", "email")) {
            return "informacion practica";
        }
        if (containsAny(normalized, "profesional", "empresa", "b2b", "mayorista", "codigo")) {
            return "acceso profesional";
        }
        if (containsAny(normalized, "novia", "velo", "liga", "tocado", "tiara", "pendiente", "abanico")) {
            return "busqueda de producto novia";
        }
        if (containsAny(normalized, "fiesta", "mantilla", "pamela", "pendiente", "bolso", "guante")) {
            return "busqueda de producto fiesta";
        }
        if (containsAny(normalized, "comunion", "rosario", "diadema", "corona", "crucifijo")) {
            return "busqueda de producto comunion";
        }
        if (containsAny(normalized, "novio", "gemelo")) {
            return "busqueda de producto novio";
        }
        return "asesoramiento de producto";
    }

    private List<Producto> selectPriorityProducts(List<Producto> productos, String latestUserMessage) {
        String normalizedQuery = normalize(latestUserMessage);
        List<Producto> matches = findRelevantProducts(productos, normalizedQuery, 12);
        if (!matches.isEmpty()) {
            return matches;
        }

        String preferredFamily = inferPreferredFamily(normalizedQuery);
        if (preferredFamily.isBlank()) {
            return List.of();
        }

        String familyKey = normalize(preferredFamily);
        return productos.stream()
                .filter(p -> !Boolean.FALSE.equals(p.getActivo()))
                .filter(p -> normalize(safeValue(p.getFamilia())).contains(familyKey))
                .sorted(Comparator.comparing(p -> safeValue(p.getCategoria()) + "|" + safeValue(p.getNombre())))
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<String> summarizeFamilies(List<Producto> productos) {
        return productos.stream()
                .map(Producto::getFamilia)
                .map(this::safeValue)
                .filter(value -> !value.isBlank())
                .map(String::trim)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Producto> findRelevantProducts(List<Producto> productos, String normalizedQuery, int limit) {
        if (normalizedQuery == null || normalizedQuery.isBlank()) {
            return List.of();
        }

        String[] tokens = normalizedQuery.split("\\s+");

        return productos.stream()
                .filter(p -> !Boolean.FALSE.equals(p.getActivo()))
                .map(p -> Map.entry(p, scoreProduct(p, tokens)))
                .filter(entry -> entry.getValue() > 0)
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private int scoreProduct(Producto producto, String[] tokens) {
        String haystack = normalize(
                safeValue(producto.getNombre()) + " " +
                safeValue(producto.getCategoria()) + " " +
                safeValue(producto.getFamilia()) + " " +
                safeValue(producto.getDescripcion())
        );

        int score = 0;
        for (String token : tokens) {
            if (token.length() < 3) {
                continue;
            }
            if (haystack.contains(token)) {
                score += 2;
            }
        }

        String family = normalize(safeValue(producto.getFamilia()));
        if (containsAny(String.join(" ", tokens), "novia") && family.contains("novia")) score += 4;
        if (containsAny(String.join(" ", tokens), "fiesta") && family.contains("fiesta")) score += 4;
        if (containsAny(String.join(" ", tokens), "comunion") && family.contains("comunion")) score += 4;
        if (containsAny(String.join(" ", tokens), "novio") && family.contains("novio")) score += 4;
        if (containsAny(String.join(" ", tokens), "arras") && family.contains("arras")) score += 4;

        return score;
    }

    private void appendProductLine(StringBuilder sb, Producto p, boolean canSeePrices) {
        sb.append("- ID: ").append(p.getId())
                .append(" | Nombre: ").append(safeValue(p.getNombre()))
                .append(" | Categoria: ").append(safeValue(p.getCategoria()))
                .append(" | Familia: ").append(safeValue(p.getFamilia()))
                .append(" | Descripcion: ").append(safeValue(p.getDescripcion()).isBlank() ? "Sin descripcion adicional." : safeValue(p.getDescripcion()));

        if (canSeePrices && p.getPrecio() != null) {
            sb.append(" | Precio: ").append(p.getPrecio()).append(" EUR");
        }

        sb.append("\n");
    }

    private String inferPreferredFamily(String normalizedQuery) {
        if (containsAny(normalizedQuery, "novia", "velo", "liga", "tocado", "tiara")) return "novia";
        if (containsAny(normalizedQuery, "fiesta", "mantilla", "bolso", "pamela")) return "fiesta";
        if (containsAny(normalizedQuery, "comunion", "rosario", "diadema", "corona")) return "comunion";
        if (containsAny(normalizedQuery, "novio", "gemelo")) return "novio";
        if (containsAny(normalizedQuery, "arras", "cojin", "cesta")) return "arras";
        return "";
    }

    private String openingForIntent(String intent) {
        return switch (intent) {
            case "busqueda de producto novia" -> "Si buscas una linea de novia con aire refinado, estas opciones son una muy buena base para empezar.";
            case "busqueda de producto fiesta" -> "Para un look de fiesta con presencia y elegancia, estas referencias tienen bastante sentido.";
            case "busqueda de producto comunion" -> "Para comunion, estas opciones pueden encajar muy bien segun el estilo que busques.";
            case "busqueda de producto novio" -> "Si estas valorando complementos de novio, estas referencias son las mas alineadas con tu consulta.";
            case "acceso profesional" -> "Te oriento primero con el acceso profesional y, ademas, con algunas referencias utiles del catalogo.";
            default -> "Te orientaria por aqui para que la eleccion sea mas rapida y afinada.";
        };
    }

    private boolean containsAny(String text, String... candidates) {
        for (String candidate : candidates) {
            if (text.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();

        return normalized.replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
