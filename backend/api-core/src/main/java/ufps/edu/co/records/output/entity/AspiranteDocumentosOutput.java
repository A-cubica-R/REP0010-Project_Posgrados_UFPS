package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AspiranteDocumentosOutput(
        Integer idAspirante,
        String nombreAspirante,
        String cedula,
        String estadoGeneral,
        List<DocumentoResumenOutput> documentos
) implements OutputResponse {

    @Builder
    public record DocumentoResumenOutput(
        Integer idDocumento,
        Integer idDocumentosrequisitoconsejocohorte,
        Integer idDocumentosrequisitoprogramacohorte,
        String nombreTitulo,
        String estado,
        String motivoRechazo,
        String linkArchivo
    ) {}
}
