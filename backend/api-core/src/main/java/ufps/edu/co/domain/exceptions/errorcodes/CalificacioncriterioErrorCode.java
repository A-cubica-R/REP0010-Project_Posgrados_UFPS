package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum CalificacioncriterioErrorCode implements BaseErrorCode {
    CALIFICACIONCRITERIO_NOT_FOUND("CALIFICACIONCRITERIO_NOT_FOUND", "Calificación de criterio no encontrada"),
    CALIFICACIONCRITERIO_ALREADY_EXISTS("CALIFICACIONCRITERIO_ALREADY_EXISTS", "Ya existe una calificación para este aspirante y criterio"),
    PUNTUACION_INVALIDA("PUNTUACION_INVALIDA", "La puntuación debe estar entre 0 y 100");

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
