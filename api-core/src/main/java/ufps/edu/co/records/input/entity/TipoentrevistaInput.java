package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum TipoentrevistaInput {
    ;

    public record TIPOENTREVISTA_CREATE(@NotBlank String nombre, String descripcion) implements CreateType {}

    public record TIPOENTREVISTA_UPDATE(@NotNull Integer id, @NotBlank String nombre, String descripcion) implements UpdateType {}

    public record TIPOENTREVISTA_PATCH(@NotNull Integer id, String nombre, String descripcion) implements PatchType {}

    public record TIPOENTREVISTA_DELETE(@NotNull Integer id) implements DeleteType {}

    public record TIPOENTREVISTA_FIND(@NotNull Integer id) implements FindType {}
}
