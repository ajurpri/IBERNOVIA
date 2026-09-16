package Proyecto_Ibernovia.Proyecto_Ibernovia.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.CacheControl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Asegurar que las carpetas de imágenes existen para evitar errores 500 al resolver recursos inexistentes
        try {
            Files.createDirectories(Paths.get("public/images/productos"));
            Files.createDirectories(Paths.get("public/images/promociones"));
        } catch (IOException e) {
            System.err.println("Error creando directorios de imágenes: " + e.getMessage());
        }

        // Servir imágenes desde la carpeta public/images/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:public/images/")
                // Los nombres generados son únicos, por lo que es seguro conservarlos
                // en el navegador sin volver a descargarlos en cada visita.
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(30)).cachePublic());
    }
}
