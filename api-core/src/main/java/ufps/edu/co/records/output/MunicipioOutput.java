package ufps.edu.co.records.output;

import ufps.edu.co.records.OutputResponse;

public record MunicipioOutput(
        Integer id,
        String nombre,
        Integer idDepartamento
) implements OutputResponse {}
