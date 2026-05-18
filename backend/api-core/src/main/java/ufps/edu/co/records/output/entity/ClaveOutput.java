package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record ClaveOutput(
        Integer id,
        String valor,
        List<UsuarioOutput> usuarioList
) implements OutputResponse {}
