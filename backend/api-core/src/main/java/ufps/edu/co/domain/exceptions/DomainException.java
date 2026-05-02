package ufps.edu.co.domain.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final BaseErrorCode code;
    private final Object param;

    public DomainException(BaseErrorCode code, Object param) {
        super(code.getDefaultMessage());
        this.code = code;
        this.param = param;
    }

    @Override
    public String getMessage() {
        return String.format(
                "[%s] %s FOR PARAM %s",
                code.getCode(),
                code.getDefaultMessage(),
                param);
    }
}