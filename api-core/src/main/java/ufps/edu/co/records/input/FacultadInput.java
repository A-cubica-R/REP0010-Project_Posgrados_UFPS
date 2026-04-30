package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum FacultadInput {
    ;

    public record FACULTAD_CREATE(@NotBlank String nombre, String correo, @NotNull Integer idAdministrativo) implements CreateType {}

    public record FACULTAD_UPDATE(@NotNull Integer id, @NotBlank String nombre, String correo, @NotNull Integer idAdministrativo) implements UpdateType {}

    public record FACULTAD_PATCH(@NotNull Integer id, String nombre, String correo, Integer idAdministrativo) implements PatchType {}

    public record FACULTAD_DELETE(@NotNull Integer id) implements DeleteType {}

    public record FACULTAD_FIND(@NotNull Integer id) implements FindType {}
}
