package ufps.edu.co.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.DuplicateAdmisionException;

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

    @ExceptionHandler(DuplicateAdmisionException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAdmision(DuplicateAdmisionException ex) {
        ErrorResponse body = new ErrorResponse(
                "LISTAADMITIDOS_DUPLICADO",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(PdfEmailException.class)
    public ResponseEntity<ErrorResponse> handlePdfEmailException(PdfEmailException ex) {
        ErrorResponse body = new ErrorResponse(
                "PDF_EMAIL_ERROR",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(body);
    }

    private HttpStatus resolveStatus(String code) {
        if (code.endsWith("_NOT_FOUND")) return HttpStatus.NOT_FOUND;
        if (code.endsWith("_ALREADY_EXISTS") || code.endsWith("_DUPLICADO")) return HttpStatus.CONFLICT;
        if (code.endsWith("_CONFLICT")) return HttpStatus.CONFLICT;
        if (code.endsWith("_FORBIDDEN")) return HttpStatus.FORBIDDEN;
        return HttpStatus.BAD_REQUEST;
    }
}
