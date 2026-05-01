package ufps.edu.co.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.method.HandlerTypePredicate;

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
    private String prefix = "/api/dev/endpoint";

    /**
     * Prefijo específico para controllers en /controllers/cases
     * Ej: /app-api/v1/cases
     */
    private String casesPrefix = "/api/application";

    /**
     * Media type por defecto para las respuestas.
     * Ej: application/json
     */
    private String mediaType = "application/json";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(casesPrefix,
                HandlerTypePredicate.forBasePackage("ufps.edu.co.controllers.cases"));
        configurer.addPathPrefix(prefix,
                HandlerTypePredicate.forBasePackage("ufps.edu.co.controllers.rest"));
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
