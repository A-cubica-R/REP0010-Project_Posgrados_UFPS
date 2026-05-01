package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum UsuarioInput {
    ;

    public record USUARIO_CREATE(@NotBlank String nombreusuario, @NotNull Integer idPersona, @NotNull Integer idRol, @NotNull Integer idClave) implements CreateType {}

    public record USUARIO_UPDATE(@NotNull Integer id, @NotBlank String nombreusuario, @NotNull Integer idPersona, @NotNull Integer idRol, @NotNull Integer idClave) implements UpdateType {}

    public record USUARIO_PATCH(@NotNull Integer id, String nombreusuario, Integer idPersona, Integer idRol, Integer idClave) implements PatchType {}

    public record USUARIO_DELETE(@NotNull Integer id) implements DeleteType {}

    public record USUARIO_FIND(@NotNull Integer id) implements FindType {}
}
