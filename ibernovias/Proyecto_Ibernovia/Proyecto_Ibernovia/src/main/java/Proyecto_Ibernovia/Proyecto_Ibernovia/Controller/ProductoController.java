package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Producto;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;
import java.io.*;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Usuario;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.UsuarioRepository;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Util.JwtUtil;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    @Value("${app.business.codes:EMPRESA2025,IBERNOVIA2025,DEMO123}")
    private String businessCodesString;

    public ProductoController(ProductoRepository repository, UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Producto> listarProductos(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-Business-Code", required = false) String businessCodeHeader
    ) {
        boolean canSee = checkCanSeePrices(authHeader, businessCodeHeader);
        return repository.findAll().stream()
                .map(p -> sanitizeProduct(p, canSee))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-Business-Code", required = false) String businessCodeHeader
    ) {
        Optional<Producto> producto = repository.findById(id);
        boolean canSee = checkCanSeePrices(authHeader, businessCodeHeader);
        return producto.map(p -> ResponseEntity.ok(sanitizeProduct(p, canSee)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        // Procesar imagen si viene en base64
        if (producto.getImagen() != null && producto.getImagen().startsWith("data:image/")) {
            String imagenProcesada = processBase64Image(producto.getImagen());
            producto.setImagen(imagenProcesada);
        }
        Producto savedProducto = repository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detalles) {
        Optional<Producto> optionalProducto = repository.findById(id);
        if (optionalProducto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto producto = optionalProducto.get();
        if (detalles.getNombre() != null) producto.setNombre(detalles.getNombre());
        if (detalles.getFamilia() != null) producto.setFamilia(detalles.getFamilia());
        if (detalles.getCategoria() != null) producto.setCategoria(detalles.getCategoria());
        if (detalles.getPrecio() != null) producto.setPrecio(detalles.getPrecio());
        if (detalles.getImagen() != null) {
            // Procesar imagen si viene en base64
            if (detalles.getImagen().startsWith("data:image/")) {
                String imagenProcesada = processBase64Image(detalles.getImagen());
                producto.setImagen(imagenProcesada);
            } else {
                producto.setImagen(detalles.getImagen());
            }
        }
        if (detalles.getDescripcion() != null) producto.setDescripcion(detalles.getDescripcion());
        if (detalles.getStock() != null) producto.setStock(detalles.getStock());
        if (detalles.getActivo() != null) producto.setActivo(detalles.getActivo());

        Producto actualizado = repository.save(producto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Producto> obtenerPorCategoria(
            @PathVariable String categoria,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-Business-Code", required = false) String businessCodeHeader
    ) {
        boolean canSee = checkCanSeePrices(authHeader, businessCodeHeader);
        return repository.findByCategoria(categoria).stream()
                .map(p -> sanitizeProduct(p, canSee))
                .toList();
    }

    @GetMapping("/familia/{familia}")
    public List<Producto> obtenerPorFamilia(
            @PathVariable String familia,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-Business-Code", required = false) String businessCodeHeader
    ) {
        boolean canSee = checkCanSeePrices(authHeader, businessCodeHeader);
        return repository.findByFamilia(familia).stream()
                .map(p -> sanitizeProduct(p, canSee))
                .toList();
    }

    @GetMapping("/buscar/{query}")
    public List<Producto> buscar(
            @PathVariable String query,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-Business-Code", required = false) String businessCodeHeader
    ) {
        boolean canSee = checkCanSeePrices(authHeader, businessCodeHeader);
        return repository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(query, query).stream()
                .map(p -> sanitizeProduct(p, canSee))
                .toList();
    }

    @PostMapping("/inicializar")
    public ResponseEntity<String> inicializarProductos() {
        return ResponseEntity.status(HttpStatus.GONE)
                .body("Endpoint obsoleto: el catálogo se gestiona por importación. No se ha modificado ningún producto.");
    }

    @PostMapping("/generar-280")
    public ResponseEntity<String> generar280Productos() {
        return ResponseEntity.status(HttpStatus.GONE)
                .body("Endpoint obsoleto: el catálogo se gestiona por importación. No se ha creado ningún producto.");
    }

    // Procesar imagen base64 y guardar en el servidor
    private String processBase64Image(String base64Image) {
        try {
            // Verificar si es una imagen base64
            if (base64Image == null || !base64Image.startsWith("data:image/")) {
                return base64Image; // No es base64, retornar como está
            }

            // Crear directorio de imágenes si no existe
            Path imaginesDir = Paths.get("public/images/productos");
            Files.createDirectories(imaginesDir);

            // Extraer tipo de imagen y datos base64
            String[] parts = base64Image.split(",");
            if (parts.length != 2) return base64Image;

            String dataBase64 = parts[1];
            byte[] decodedBytes = Base64.getDecoder().decode(dataBase64);

            // Determinar extensión de archivo
            String mimeType = parts[0]; // data:image/png;base64 o similar
            String extension = "jpg";
            if (mimeType.contains("png")) extension = "png";
            else if (mimeType.contains("gif")) extension = "gif";
            else if (mimeType.contains("webp")) extension = "webp";

            // Generar nombre único para la imagen
            String fileName = "producto_" + System.currentTimeMillis() + "." + extension;
            Path filePath = imaginesDir.resolve(fileName);

            // Guardar archivo
            Files.write(filePath, decodedBytes);

            // Retornar ruta relativa para servir desde el navegador
            return "/images/productos/" + fileName;

        } catch (Exception e) {
            System.err.println("Error procesando imagen base64: " + e.getMessage());
            return null; // Tomar una acción por defecto si fallan
        }
    }

    private boolean checkCanSeePrices(String authHeader, String businessCodeHeader) {
        // 1. Check if the valid business code is provided in the header
        if (businessCodeHeader != null && businessCodesString != null) {
            String trimmedHeader = businessCodeHeader.trim();
            for (String code : businessCodesString.split(",")) {
                if (code.trim().equalsIgnoreCase(trimmedHeader)) {
                    return true;
                }
            }
        }

        // 2. Check if the user is logged in and has ADMIN or BUSINESS role
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

    private Producto sanitizeProduct(Producto p, boolean canSeePrices) {
        Producto copy = new Producto();
        copy.setId(p.getId());
        copy.setNombre(p.getNombre());
        copy.setFamilia(p.getFamilia());
        copy.setCategoria(p.getCategoria());
        copy.setImagen(p.getImagen());
        copy.setDescripcion(p.getDescripcion());
        copy.setStock(p.getStock());
        copy.setActivo(p.getActivo());
        copy.setCreatedAt(p.getCreatedAt());
        copy.setUpdatedAt(p.getUpdatedAt());
        if (canSeePrices) {
            copy.setPrecio(p.getPrecio());
        } else {
            copy.setPrecio(null);
        }
        return copy;
    }
}

