package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CargoInput {
    ;

    public record CARGO_CREATE(@NotBlank String nombre, String descripcion, @NotNull Integer idPrograma) implements CreateType {}

    public record CARGO_UPDATE(@NotNull Integer id, @NotBlank String nombre, String descripcion, @NotNull Integer idPrograma) implements UpdateType {}

    public record CARGO_PATCH(@NotNull Integer id, String nombre, String descripcion, Integer idPrograma) implements PatchType {}

    public record CARGO_DELETE(@NotNull Integer id) implements DeleteType {}

    public record CARGO_FIND(@NotNull Integer id) implements FindType {}
}
