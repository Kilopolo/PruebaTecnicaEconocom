package es.econocom.backendPruebaTecnica.login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de CORS (Cross-Origin Resource Sharing) para permitir
 * que el frontend (por ejemplo, Angular en http://localhost:4200) se comunique
 * con el backend (Spring Boot) sin restricciones de origen cruzado.
 */
@Configuration
public class CorsConfig {

    /**
     * Bean que define la configuración CORS personalizada para los endpoints del backend.
     *
     * @return WebMvcConfigurer con la configuración CORS aplicada al path "/api/**".
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configura los mapeos CORS para permitir solicitudes desde el origen
             * http://localhost:4200 a cualquier endpoint bajo "/api/**".
             *
             * @param registry Registro de configuraciones CORS.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
