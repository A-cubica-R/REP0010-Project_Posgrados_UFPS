package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum EntrevistadorInput {
    ;

    public record ENTREVISTADOR_CREATE(@NotNull Integer idAdministrativo, String observaciones) implements CreateType {}

    public record ENTREVISTADOR_UPDATE(@NotNull Integer id, @NotNull Integer idAdministrativo, String observaciones) implements UpdateType {}

    public record ENTREVISTADOR_PATCH(@NotNull Integer id, Integer idAdministrativo, String observaciones) implements PatchType {}

    public record ENTREVISTADOR_DELETE(@NotNull Integer id) implements DeleteType {}

    public record ENTREVISTADOR_FIND(@NotNull Integer id) implements FindType {}
}
