package Proyecto_Ibernovia.Proyecto_Ibernovia.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva la protección contra ataques cruzados (importante para Vue)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Abre todas las rutas (API, Imágenes, etc.)
                )
                .httpBasic(basic -> basic.disable()) // ¡ESTO quita la ventanita de usuario/password!
                .formLogin(form -> form.disable()); // ¡ESTO quita el formulario de login!

        return http.build();
    }
}