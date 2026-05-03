package ufps.edu.co.config.path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.method.HandlerTypePredicate;

/**
 * Configuración centralizada para los endpoints de api-rest.
 * Define los prefijos globales para diferentes tipos de controllers.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api.rest")
public class ApiRestConfiguration implements WebMvcConfigurer {

    private String prefix;

    private String casesPrefix;

    private String mediaType = "application/json";

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(casesPrefix,
                HandlerTypePredicate.forBasePackage("ufps.edu.co.controllers.cases"));
        configurer.addPathPrefix(prefix,
                HandlerTypePredicate.forBasePackage("ufps.edu.co.controllers.rest"));
    }
}
