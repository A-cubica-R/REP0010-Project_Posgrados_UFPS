package ufps.edu.co.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

/**
 * Configuración centralizada para los endpoints de api-rest.
 * Define los prefijos globales para diferentes tipos de controllers.
 */
@Configuration
@ConfigurationProperties(prefix = "api.rest")
public class ApiRestConfiguration implements WebMvcConfigurer {

    /**
     * Prefijo global para controllers en /controllers/rest
     * Ej: /app-api/v1
     */
    private String prefix = "/app-api/v1";

    /**
     * Prefijo específico para controllers en /controllers/cases
     * Ej: /app-api/v1/cases
     */
    private String casesPrefix = "/app-api/v1/cases";

    /**
     * Media type por defecto para las respuestas.
     * Ej: application/json
     */
    private String mediaType = "application/json";

    /**
     * Bean que registra el custom RequestMappingHandlerMapping
     * para aplicar prefijos automáticos.
     */
    @Bean
    public PrefixedRequestMappingHandlerMapping prefixedRequestMappingHandlerMapping() {
        PrefixedRequestMappingHandlerMapping mapping = new PrefixedRequestMappingHandlerMapping();
        mapping.setPrefix(this.prefix);
        mapping.setCasesPrefix(this.casesPrefix);
        mapping.setOrder(0);
        return mapping;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Los prefijos se aplican automáticamente mediante PrefixedRequestMappingHandlerMapping
    }

    // Getters y Setters
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCasesPrefix() {
        return casesPrefix;
    }

    public void setCasesPrefix(String casesPrefix) {
        this.casesPrefix = casesPrefix;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
