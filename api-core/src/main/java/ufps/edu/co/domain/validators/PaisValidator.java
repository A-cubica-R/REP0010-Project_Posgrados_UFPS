package ufps.edu.co.domain.validators;

public final class PaisValidator {

    public static String validateNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del país es obligatorio");
        }

        String normalized = nombre.trim();
        
        if (normalized.length() > 100) {
            throw new IllegalArgumentException("El nombre del país no puede superar 100 caracteres");
        }

        return normalized;
    }

    public static Integer validateIdPais(Integer idPais) {
        if (idPais == null || idPais <= 0) {
            throw new IllegalArgumentException("El id del país debe ser mayor que 0");
        }
        return idPais;
    }
}
