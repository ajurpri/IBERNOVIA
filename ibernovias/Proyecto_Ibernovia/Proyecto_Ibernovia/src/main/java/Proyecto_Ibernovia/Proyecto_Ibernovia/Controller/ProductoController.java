package Proyecto_Ibernovia.Proyecto_Ibernovia.Controller;

import Proyecto_Ibernovia.Proyecto_Ibernovia.Model.Producto;
import Proyecto_Ibernovia.Proyecto_Ibernovia.Repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class ProductoController {

    private final ProductoRepository repository;

    public ProductoController(ProductoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Optional<Producto> producto = repository.findById(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
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
        if (detalles.getCategoria() != null) producto.setCategoria(detalles.getCategoria());
        if (detalles.getPrecio() != null) producto.setPrecio(detalles.getPrecio());
        if (detalles.getImagen() != null) producto.setImagen(detalles.getImagen());
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
    public List<Producto> obtenerPorCategoria(@PathVariable String categoria) {
        return repository.findByCategoria(categoria);
    }

    @GetMapping("/buscar/{query}")
    public List<Producto> buscar(@PathVariable String query) {
        return repository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(query, query);
    }

    @PostMapping("/inicializar")
    public ResponseEntity<String> inicializarProductos() {
        List<Producto> productos = repository.findAll();
        int totalActualizados = 0;
        
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            
            // Asignar imagen basada en el índice
            // Formato: /images/complemento (1).jpg hasta /images/complemento (280).jpg
            int imagenNum = (i % 280) + 1;
            String imagenNombre = String.format("complemento (%d).jpg", imagenNum);
            p.setImagen("/images/" + imagenNombre);
            
            // Generar precio aleatorio
            double precio = 15 + Math.random() * 80;
            p.setPrecio(Math.round(precio * 100.0) / 100.0);
            
            try {
                repository.save(p);
                totalActualizados++;
            } catch (Exception e) {
                System.err.println("Error guardando producto " + p.getId() + ": " + e.getMessage());
            }
        }

        return ResponseEntity.ok("✓ Inicialización completada: " + totalActualizados + " productos actualizados con imágenes locales");
    }

    @PostMapping("/generar-280")
    public ResponseEntity<String> generar280Productos() {
        // Primero limpia la base de datos
        try {
            repository.deleteAll();
        } catch (Exception e) {
            System.err.println("Error limpiando BD: " + e.getMessage());
        }

        String[] categorias = {"Ligas", "Pendientes", "Abanicos", "Gemelos", "Cruces", "Tocados", "Broches", "Can-canes", "Religioso"};
        String[] adjetivos = {"Elegante", "Delicado", "Lujoso", "Premium", "Exquisito", "Fino", "Artesanal", "Exclusivo", "Nupcial", "Romántico", "Clásico", "Moderno"};
        String[] sustantivos = {"Liga", "Pendiente", "Abanico", "Gemelo", "Cruz", "Tocado", "Broche", "Can-Can", "Diadema", "Brazalete", "Ramillete", "Tiara"};

        int productosCreados = 0;

        for (int i = 1; i <= 280; i++) {
            Producto p = new Producto();
            
            // Nombre: Adjetivo + Sustantivo + número
            String adjetivo = adjetivos[i % adjetivos.length];
            String sustantivo = sustantivos[i % sustantivos.length];
            p.setNombre(adjetivo + " " + sustantivo + " #" + i);
            
            // Categoría
            p.setCategoria(categorias[i % categorias.length]);
            
            // Descripción
            p.setDescripcion("Complemento nupcial de alta calidad para ocasiones especiales. Diseño exclusivo IBERNOVIA.");
            
            // Imagen - Ruta simple que Vite servirá desde /public
            String imagenNombre = String.format("Complemento (%d).jpg", i);
            p.setImagen("/images/" + imagenNombre);
            
            // Precio aleatorio
            double precio = 15 + Math.random() * 80;
            p.setPrecio(Math.round(precio * 100.0) / 100.0);
            
            // Stock
            p.setStock((int) (Math.random() * 50) + 1);
            
            // Activo
            p.setActivo(true);
            
            try {
                repository.save(p);
                productosCreados++;
            } catch (Exception e) {
                System.err.println("Error creando producto " + i + ": " + e.getMessage());
            }
        }

        return ResponseEntity.ok("✓ Éxito! Se crearon " + productosCreados + " productos con imágenes, nombres y precios.");
    }
}
