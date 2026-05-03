package ufps.edu.co.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3StorageStartupCheck {

    private static final Logger logger = LoggerFactory.getLogger(S3StorageStartupCheck.class);

    @Value("${app.module.name.s3-storage}")
    private String moduleName;

    @PostConstruct
    void validateAndLogModuleLoad() {
        if (moduleName == null || moduleName.isBlank()) {
            throw new IllegalStateException("app.module.name.s3-storage is required for s3-storage");
        }

        String banner = """
                ======================================================
                =                MODULE %s LOADED                    =
                ======================================================
                """.formatted(moduleName);

        logger.warn("\n" + banner);
    }
}
