package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum EntrevistadoresInput {
    ;

    public record ENTREVISTADORES_CREATE(@NotNull Integer idEntrevista, @NotNull Integer idAdministrativo) implements CreateType {}

    public record ENTREVISTADORES_UPDATE(@NotNull Integer id, @NotNull Integer idEntrevista, @NotNull Integer idAdministrativo) implements UpdateType {}

    public record ENTREVISTADORES_PATCH(@NotNull Integer id, Integer idEntrevista, Integer idAdministrativo) implements PatchType {}

    public record ENTREVISTADORES_DELETE(@NotNull Integer id) implements DeleteType {}

    public record ENTREVISTADORES_FIND(@NotNull Integer id) implements FindType {}
}
