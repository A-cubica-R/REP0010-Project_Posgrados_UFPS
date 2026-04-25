package ufps.edu.co.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración centralizada para los endpoints de api-rest.
 * Define el prefijo global y otras propiedades de la API REST.
 */
@Configuration
@ConfigurationProperties(prefix = "api.rest")
public class ApiRestConfiguration {

    /**
     * Prefijo global para todos los endpoints.
     * Ej: /app-api/v1
     */
    private String prefix = "/app-api/v1";

    /**
     * Media type por defecto para las respuestas.
     * Ej: application/json
     */
    private String mediaType = "application/json";

    // Getters y Setters
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
