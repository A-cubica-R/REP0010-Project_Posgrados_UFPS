package ufps.edu.co.domain.validators;

public final class DepartamentoValidator {

    public static String validateNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del departamento es obligatorio");
        }

        String normalized = nombre.trim();
        
        if (normalized.length() > 100) {
            throw new IllegalArgumentException("El nombre del departamento no puede superar 100 caracteres");
        }

        return normalized;
    }

    public static Integer validateIdPais(Integer idPais) {
        if (idPais == null || idPais <= 0) {
            throw new IllegalArgumentException("El id del pais debe ser mayor que 0");
        }
        return idPais;
    }
}
