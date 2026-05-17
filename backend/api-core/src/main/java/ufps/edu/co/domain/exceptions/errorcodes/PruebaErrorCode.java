package ufps.edu.co.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ufps.edu.co.domain.exceptions.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum PruebaErrorCode implements BaseErrorCode {
    PRUEBA_NOT_FOUND("PRUEBA_NOT_FOUND", "Prueba no encontrada");

    private final String code;
    private final String defaultMessage;
}
