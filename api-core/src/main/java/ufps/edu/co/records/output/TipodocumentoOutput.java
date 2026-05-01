package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record TipodocumentoOutput(
        Integer id,
        String nombre,
        String descripcion,
        String extension,
        Integer tamanomaximo
) implements OutputResponse {}
