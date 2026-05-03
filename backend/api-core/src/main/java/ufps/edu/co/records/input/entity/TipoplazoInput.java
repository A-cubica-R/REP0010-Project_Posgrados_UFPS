package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum TipoplazoInput {
    ;

    public record TIPOPLAZO_CREATE(@NotBlank String nombre, String descripcion) implements CreateType {}

    public record TIPOPLAZO_UPDATE(@NotNull Integer id, @NotBlank String nombre, String descripcion) implements UpdateType {}

    public record TIPOPLAZO_PATCH(@NotNull Integer id, String nombre, String descripcion) implements PatchType {}

    public record TIPOPLAZO_DELETE(@NotNull Integer id) implements DeleteType {}

    public record TIPOPLAZO_FIND(@NotNull Integer id) implements FindType {}
}
