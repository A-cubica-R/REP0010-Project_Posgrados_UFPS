package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DocumentoRequeridoOutput(
        Integer idDocumentosrequisitoconsejocohorte,
        Integer idDocumentosrequisitoprogramacohorte,
        String nombre,
        String urlformato
) implements OutputResponse {
}
