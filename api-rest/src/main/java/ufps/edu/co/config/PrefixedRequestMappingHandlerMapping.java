package ufps.edu.co.config;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

/**
 * Custom RequestMappingHandlerMapping que aplica prefijos automáticos
 * basado en el paquete del controller.
 */
public class PrefixedRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private String prefix = "/app-api/v1";
    private String casesPrefix = "/app-api/v1/cases";

    @Override
    protected void registerHandlerMethod(Object handler, java.lang.reflect.Method method, RequestMappingInfo mapping) {
        Class<?> beanClass = handler.getClass();
        String packageName = beanClass.getPackage().getName();

        // Si el controller está en /controllers/cases, aplica casesPrefix
        if (packageName.contains("controllers.cases")) {
            mapping = mapping.mutate()
                    .paths(prependPrefix(mapping.getPathPatternsCondition().getPatterns(), casesPrefix))
                    .build();
        }
        // Si está en /controllers/rest, aplica prefix normal
        else if (packageName.contains("controllers.rest")) {
            mapping = mapping.mutate()
                    .paths(prependPrefix(mapping.getPathPatternsCondition().getPatterns(), prefix))
                    .build();
        }

        super.registerHandlerMethod(handler, method, mapping);
    }

    /**
     * Prepend un prefijo a los patrones de ruta.
     */
    private String[] prependPrefix(Set<PathPattern> patterns, String prefix) {
        Set<String> prefixedPatterns = new LinkedHashSet<>();
        
        for (PathPattern pattern : patterns) {
            if (pattern != null) {
                String patternStr = pattern.getPatternString();
                if (patternStr != null && !patternStr.isEmpty()) {
                    String prefixedPath = prefix + patternStr;
                    prefixedPatterns.add(prefixedPath);
                }
            }
        }
        return prefixedPatterns.toArray(new String[0]);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setCasesPrefix(String casesPrefix) {
        this.casesPrefix = casesPrefix;
    }
}
