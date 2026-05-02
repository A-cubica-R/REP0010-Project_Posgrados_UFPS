package ufps.edu.co.domain.exceptions.errorcodes;

import ufps.edu.co.domain.exceptions.BaseErrorCode;

public enum SedeErrorCode implements BaseErrorCode {
    SEDE_NOT_FOUND("SEDE_NOT_FOUND", "Sede no encontrada"),
    SEDE_DUPLICADA("SEDE_DUPLICADA", "Sede duplicada");

    private final String code;
    private final String defaultMessage;

    SedeErrorCode(String code, String defaultMessage) {
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
