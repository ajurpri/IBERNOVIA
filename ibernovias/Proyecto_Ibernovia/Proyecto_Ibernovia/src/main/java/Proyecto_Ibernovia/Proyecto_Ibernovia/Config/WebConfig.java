package Proyecto_Ibernovia.Proyecto_Ibernovia.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Servir imágenes desde la carpeta public/images del frontend
        String imagePath = Paths.get("../../front-end/public/images/").toAbsolutePath().toString();
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + imagePath.replace("\\", "/") + "/");
    }
}
