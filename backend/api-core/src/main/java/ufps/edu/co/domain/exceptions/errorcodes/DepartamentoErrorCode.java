package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum DepartamentoErrorCode implements BaseErrorCode {
    DEPARTAMENTO_NOT_FOUND("DEPARTAMENTO_NOT_FOUND", "Departamento no encontrado"),
    DEPARTAMENTO_DUPLICADO("DEPARTAMENTO_DUPLICADO", "Departamento duplicado");

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
