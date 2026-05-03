package ufps.edu.co.domain.exceptions.errorcodes;

import ufps.edu.co.domain.exceptions.BaseErrorCode;

public enum PaisErrorCode implements BaseErrorCode {
    PAIS_NOT_FOUND("PAIS_NOT_FOUND", "País no encontrado"),
    PAIS_DUPLICADO("PAIS_DUPLICADO", "País duplicado");

    private final String code;
    private final String defaultMessage;

    PaisErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }
    
}
