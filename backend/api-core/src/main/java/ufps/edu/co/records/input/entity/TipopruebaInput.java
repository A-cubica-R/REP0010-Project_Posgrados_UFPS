package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum TipopruebaInput {
    ;

    public record TIPOPRUEBA_CREATE(@NotBlank String tipo, String descripcion) implements CreateType {}

    public record TIPOPRUEBA_UPDATE(@NotNull Integer id, @NotBlank String tipo, String descripcion) implements UpdateType {}

    public record TIPOPRUEBA_PATCH(@NotNull Integer id, String tipo, String descripcion) implements PatchType {}

    public record TIPOPRUEBA_DELETE(@NotNull Integer id) implements DeleteType {}

    public record TIPOPRUEBA_FIND(@NotNull Integer id) implements FindType {}
}
