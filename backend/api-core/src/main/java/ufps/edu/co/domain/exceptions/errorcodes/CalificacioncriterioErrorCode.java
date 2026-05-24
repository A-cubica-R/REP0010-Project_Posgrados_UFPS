package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum CalificacioncriterioErrorCode implements BaseErrorCode {
    CALIFICACIONCRITERIO_NOT_FOUND("CALIFICACIONCRITERIO_NOT_FOUND", "Calificación de criterio no encontrada"),
    CALIFICACIONCRITERIO_ALREADY_EXISTS("CALIFICACIONCRITERIO_ALREADY_EXISTS", "Ya existe una calificación para este aspirante y criterio"),
    PUNTUACION_INVALIDA("PUNTUACION_INVALIDA", "El puntaje debe ser un número entero positivo mayor o igual a 1"),
    PUNTAJE_EXCEDE_MAXIMO("PUNTAJE_EXCEDE_MAXIMO", "el puntaje máximo que puede tener este criterio es");

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
