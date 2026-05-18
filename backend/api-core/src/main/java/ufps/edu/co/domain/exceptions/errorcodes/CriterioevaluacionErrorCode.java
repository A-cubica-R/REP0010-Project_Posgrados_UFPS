package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum CriterioevaluacionErrorCode implements BaseErrorCode {
    CRITERIOEVALUACION_NOT_FOUND("CRITERIOEVALUACION_NOT_FOUND", "Criterio de aceptación no encontrado"),
    PESO_EXCEDE_LIMITE("PESO_EXCEDE_LIMITE", "La suma de los pesos de los criterios de aceptación no puede exceder 100");

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
