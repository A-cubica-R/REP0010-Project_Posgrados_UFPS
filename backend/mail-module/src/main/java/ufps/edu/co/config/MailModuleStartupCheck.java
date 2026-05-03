package ufps.edu.co.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailModuleStartupCheck {

    private static final Logger logger = LoggerFactory.getLogger(MailModuleStartupCheck.class);

    @Value("${app.module.name.mail-module}")
    private String moduleName;

    @PostConstruct
    void validateAndLogModuleLoad() {
        if (moduleName == null || moduleName.isBlank()) {
            throw new IllegalStateException("app.module.name.mail-module is required for mail-module");
        }

        String banner = """
                ======================================================
                =                MODULE %s LOADED                    =
                ======================================================
                """.formatted(moduleName);

        logger.warn("\n" + banner);
    }
}
