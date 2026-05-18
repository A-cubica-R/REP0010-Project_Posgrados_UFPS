package ufps.edu.co.domain.exceptions;

public class DuplicateAdmisionException extends RuntimeException {
    public DuplicateAdmisionException(Integer idAspirante, Integer idCohorte) {
        super("El aspirante " + idAspirante + " ya está registrado en la cohorte " + idCohorte);
    }
}
