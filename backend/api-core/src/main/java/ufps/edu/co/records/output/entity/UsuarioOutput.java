package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record UsuarioOutput(
        Integer id,
        String nombreusuario,
        Integer idClave,
        Integer idPersona,
        Integer idRol,
        PersonaOutput persona,
        RolOutput rol,
        ClaveOutput clave
) implements OutputResponse {}
