package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record ResultadopruebaOutput(
                Integer id,
                BigDecimal calificacion,
                Integer idAspirante,
                Integer idPrueba,
                AspiranteOutput aspirante,
                PruebaOutput prueba) implements OutputResponse {
}