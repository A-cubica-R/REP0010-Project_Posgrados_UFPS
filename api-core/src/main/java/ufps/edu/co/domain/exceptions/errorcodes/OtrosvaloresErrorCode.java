package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
public enum OtrosvaloresErrorCode implements BaseErrorCode {
    OTROSVALORES_NOT_FOUND("OTROSVALORES_NOT_FOUND", "Otrosvalores no encontrado"),
    OTROSVALORES_DUPLICADO("OTROSVALORES_DUPLICADO", "Otrosvalores duplicado");

    private final String code;
    private final String defaultMessage;

    OtrosvaloresErrorCode(String code, String defaultMessage) {
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