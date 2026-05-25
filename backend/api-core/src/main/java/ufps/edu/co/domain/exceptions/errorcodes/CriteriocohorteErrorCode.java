package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum CriteriocohorteErrorCode implements BaseErrorCode {
    CRITERIOCOHORTE_NOT_FOUND("CRITERIOCOHORTE_NOT_FOUND", "Criteriocohorte no encontrada"),
    CRITERIOCOHORTE_DUPLICADO("CRITERIOCOHORTE_DUPLICADO", "Criteriocohorte duplicada"),
    CRITERIO_YA_ASIGNADO_A_COHORTE("CRITERIO_YA_ASIGNADO_A_COHORTE", "El criterio ya está asignado a esta cohorte"),
    CRITERIO_NO_PERTENECE_AL_PROGRAMA("CRITERIO_NO_PERTENECE_AL_PROGRAMA", "El criterio no pertenece al programa de la cohorte"),
    CRITERIO_INACTIVO("CRITERIO_INACTIVO", "El criterio está inactivo y no puede asignarse"),
    PESO_SNAPSHOT_EXCEDE_LIMITE("PESO_SNAPSHOT_EXCEDE_LIMITE", "La suma de pesos snapshot de la cohorte excede el 100%");

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