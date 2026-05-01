package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CambiodocumentoOutput(
        Integer id,
        Integer idDocumentoanterior,
        Integer idDocumentoactual,
        DocumentoOutput documentoAnterior,
        DocumentoOutput documentoActual
) implements OutputResponse {}
