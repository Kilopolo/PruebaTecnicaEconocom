package es.econocom.backendPruebaTecnica.login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad que define las reglas de autorización
 * y autenticación para los endpoints del backend usando Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define la cadena de filtros de seguridad para las solicitudes HTTP.
     * <p>
     * - Desactiva CSRF (Cross-Site Request Forgery).
     * - Permite acceso sin autenticación a la raíz ("/") y a cualquier ruta bajo "/api/auth/**".
     * - Requiere autenticación para todas las demás rutas.
     * - Usa autenticación básica HTTP por defecto.
     *
     * @param http objeto {@link HttpSecurity} proporcionado por Spring.
     * @return una instancia de {@link SecurityFilterChain} con la configuración definida.
     * @throws Exception si ocurre un error durante la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
