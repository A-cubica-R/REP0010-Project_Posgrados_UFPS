package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum CohorteErrorCode implements BaseErrorCode {
    COHORTE_NOT_FOUND("COHORTE_NOT_FOUND", "Cohorte no encontrada"),
    COHORTE_DUPLICADO("COHORTE_DUPLICADO", "Cohorte duplicada"),
    COHORTE_CON_ASIGNACIONES_BLOQUEADAS(
            "COHORTE_CON_ASIGNACIONES_BLOQUEADAS",
            "No se pueden eliminar asignaciones porque existen documentos cargados que dependen de ellas");

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
