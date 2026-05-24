package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CriterioevaluacionOutput(
                Integer id,
                String nombre,
                Boolean activo,
                String descripcion,
                BigDecimal peso,
                Integer idPrograma,
                ProgramaOutput programa) implements OutputResponse {
}