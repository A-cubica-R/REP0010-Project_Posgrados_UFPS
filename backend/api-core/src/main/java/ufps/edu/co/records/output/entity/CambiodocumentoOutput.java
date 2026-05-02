package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CambiodocumentoOutput(
        Integer id,
        DocumentoOutput documentoAnterior,
        DocumentoOutput documentoActual
) implements OutputResponse {}
