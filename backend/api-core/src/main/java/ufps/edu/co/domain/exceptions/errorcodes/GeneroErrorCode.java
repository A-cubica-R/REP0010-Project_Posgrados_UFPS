package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
public enum GeneroErrorCode implements BaseErrorCode {
    GENERO_NOT_FOUND("GENERO_NOT_FOUND", "Género no encontrado"),
    GENERO_DUPLICADO("GENERO_DUPLICADO", "Género duplicado");

    private final String code;
    private final String defaultMessage;

    GeneroErrorCode(String code, String defaultMessage) {
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