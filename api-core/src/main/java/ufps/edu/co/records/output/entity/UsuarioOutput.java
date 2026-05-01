package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record UsuarioOutput(
        Integer id,
        String nombreusuario,
        PersonaOutput persona,
        RolOutput rol,
        ClaveOutput clave
) implements OutputResponse {}
