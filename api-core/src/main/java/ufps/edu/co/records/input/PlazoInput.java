package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import ufps.edu.co.records.contracts.*;

public enum PlazoInput {
    ;

    public record PLAZO_CREATE(@NotNull LocalDate fechainicio, @NotNull LocalDate fechafin, @NotNull Integer idTipoplazo) implements CreateType {}

    public record PLAZO_UPDATE(@NotNull Integer id, @NotNull LocalDate fechainicio, @NotNull LocalDate fechafin, @NotNull Integer idTipoplazo) implements UpdateType {}

    public record PLAZO_PATCH(@NotNull Integer id, LocalDate fechainicio, LocalDate fechafin, Integer idTipoplazo) implements PatchType {}

    public record PLAZO_DELETE(@NotNull Integer id) implements DeleteType {}

    public record PLAZO_FIND(@NotNull Integer id) implements FindType {}
}
