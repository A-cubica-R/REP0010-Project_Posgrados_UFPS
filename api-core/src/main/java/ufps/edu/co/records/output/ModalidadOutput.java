package ufps.edu.co.records.output;

import ufps.edu.co.records.OutputResponse;

public record ModalidadOutput(
        Integer id,
        String nombre
) implements OutputResponse {}
