package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum AspiranteErrorCode implements BaseErrorCode {
    DOCUMENTACION_FUERA_DE_PLAZO_FORBIDDEN(
            "DOCUMENTACION_FUERA_DE_PLAZO_FORBIDDEN",
            "El plazo de documentación venció. No es posible subir documentos después de la fecha límite");

    private final String code;
    private final String defaultMessage;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }
}