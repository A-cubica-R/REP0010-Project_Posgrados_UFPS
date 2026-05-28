package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AprobarDocumentoOutput (
    Integer id,
    String nombre,
    String estado
) implements OutputResponse {}
