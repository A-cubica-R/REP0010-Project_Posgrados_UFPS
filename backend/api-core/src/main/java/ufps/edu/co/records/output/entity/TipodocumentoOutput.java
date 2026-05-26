package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record TipodocumentoOutput(
        Integer id,
        String descripcion,
        String extension,
        String tipo,
        Integer tamanomaximo,
        List<DocumentoOutput> documentoList
) implements OutputResponse {}
