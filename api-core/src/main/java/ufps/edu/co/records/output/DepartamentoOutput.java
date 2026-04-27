package ufps.edu.co.records.output;

import ufps.edu.co.records.OutputResponse;

public record DepartamentoOutput(
        Integer id,
        String nombre,
        Integer idPais
) implements OutputResponse {}