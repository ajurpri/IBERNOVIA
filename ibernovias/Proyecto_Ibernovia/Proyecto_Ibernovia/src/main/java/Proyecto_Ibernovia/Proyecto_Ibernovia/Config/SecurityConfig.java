package Proyecto_Ibernovia.Proyecto_Ibernovia.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final List<String> allowedOrigins;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            @Value("${app.cors.allowed-origins:http://localhost:5173,http://localhost:5174,http://localhost:5175,http://localhost:5176,http://localhost:3000}") String allowedOrigins) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.allowedOrigins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitar CORS para conectar con Vue
        .csrf(csrf -> csrf.disable()) // API stateless con JWT
                .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers(
                "/api/auth/**",
                "/api/contacto",
                "/api/contacto/",
                "/api/admin/setup/check",
                "/api/chat",
                "/api/chat/",
                "/images/**"
            ).permitAll()
            // Permitir crear el primer admin con X-Setup-Key (sin requerir ROLE_ADMIN)
            .requestMatchers(HttpMethod.POST, "/api/admin/setup/create-admin").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/eventos", "/api/eventos/").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/admin/solicitudes").permitAll()
            .requestMatchers("/api/admin/**", "/api/contacto/admin/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")
            .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.disable()) // ¡ESTO quita la ventanita de usuario/password!
        .formLogin(form -> form.disable()) // ¡ESTO quita el formulario de login!
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins); // Orígenes del frontend (configurable por entorno)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}