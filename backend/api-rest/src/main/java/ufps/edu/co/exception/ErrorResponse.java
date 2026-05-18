package ufps.edu.co.exception;

public record ErrorResponse(String code, String message, Object param) {}
