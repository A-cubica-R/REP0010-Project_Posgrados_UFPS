package ufps.edu.co.rest.dto;

import java.io.Serializable;

public record PagoResumenDTO(
        Integer id,
        Integer idAspirante,
        Integer idEstado,
        Integer idPagoconcepto,
        String aspirante,
        String estado,
        Integer pagoconceptoId,
        String pagoconceptoTipo) implements Serializable {
}