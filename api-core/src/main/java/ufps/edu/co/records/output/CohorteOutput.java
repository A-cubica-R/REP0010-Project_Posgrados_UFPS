package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CohorteOutput(
    Integer id,
    String nombre,
    String fechaInicio,
    String fechaFin
) implements OutputResponse {}
