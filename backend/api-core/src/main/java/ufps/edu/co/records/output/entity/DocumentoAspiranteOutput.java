package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DocumentoAspiranteOutput(
        Integer idDocumento,
        Integer idRequisito,
        String nombre,
        String status,
        String nombreArchivo,
        String rejectionReason
) implements OutputResponse {
}
