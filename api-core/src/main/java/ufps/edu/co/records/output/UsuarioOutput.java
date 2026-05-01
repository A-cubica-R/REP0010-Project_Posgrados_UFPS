package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record UsuarioOutput(
        Integer id,
        String nombreusuario,
        Integer idPersona,
        Integer idRol,
        Integer idClave,
        PersonaOutput persona,
        RolOutput rol,
        ClaveOutput clave
) implements OutputResponse {}
