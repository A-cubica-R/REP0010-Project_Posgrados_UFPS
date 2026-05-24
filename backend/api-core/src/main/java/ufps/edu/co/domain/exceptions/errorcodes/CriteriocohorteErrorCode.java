package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum CriteriocohorteErrorCode implements BaseErrorCode {
    CRITERIOCOHORTE_NOT_FOUND("CRITERIOCOHORTE_NOT_FOUND", "Criteriocohorte no encontrada"),
    CRITERIOCOHORTE_DUPLICADO("CRITERIOCOHORTE_DUPLICADO", "Criteriocohorte duplicada");

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