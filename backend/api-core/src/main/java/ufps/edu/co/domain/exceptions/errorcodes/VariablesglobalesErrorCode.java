package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum VariablesglobalesErrorCode implements BaseErrorCode {
    VALOR_GLOBAL_NO_CONFIGURADO_NOT_FOUND_TAMANO_MAXIMO(
            "VALOR_GLOBAL_NO_CONFIGURADO_NOT_FOUND_TAMANO_MAXIMO",
            "No existe configuración global para calcular el valor del tamaño máximo de archivo");

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