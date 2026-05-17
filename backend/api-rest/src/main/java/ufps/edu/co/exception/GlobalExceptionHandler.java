package ufps.edu.co.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ufps.edu.co.domain.exceptions.DomainException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        HttpStatus status = resolveStatus(ex.getCode().getCode());
        ErrorResponse body = new ErrorResponse(
                ex.getCode().getCode(),
                ex.getCode().getDefaultMessage(),
                ex.getParam()
        );
        return ResponseEntity.status(status).body(body);
    }

    private HttpStatus resolveStatus(String code) {
        if (code.endsWith("_NOT_FOUND")) return HttpStatus.NOT_FOUND;
        if (code.endsWith("_ALREADY_EXISTS") || code.endsWith("_DUPLICADO")) return HttpStatus.CONFLICT;
        return HttpStatus.BAD_REQUEST;
    }
}
