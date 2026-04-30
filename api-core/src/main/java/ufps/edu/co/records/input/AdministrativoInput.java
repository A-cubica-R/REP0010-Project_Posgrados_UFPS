package ufps.edu.co.records.input;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum AdministrativoInput {
    ;

    public record ADMINISTRATIVO_CREATE(
            @NotNull Integer idPersona,
            @NotNull LocalDate fechainicio,
            LocalDate fechasalida,
            @NotNull Integer idEstado,
            @NotNull Integer idCargo) implements CreateType {
    }

    public record ADMINISTRATIVO_UPDATE(
            @NotNull Integer id,
            @NotNull Integer idPersona,
            @NotNull LocalDate fechainicio,
            LocalDate fechasalida,
            @NotNull Integer idEstado,
            @NotNull Integer idCargo) implements UpdateType {
    }

    public record ADMINISTRATIVO_PATCH(
            @NotNull Integer id,
            Integer idPersona,
            LocalDate fechainicio,
            LocalDate fechasalida,
            Integer idEstado,
            Integer idCargo) implements PatchType {
    }

    public record ADMINISTRATIVO_DELETE(@NotNull Integer id) implements DeleteType {
    }

    public record ADMINISTRATIVO_FIND(@NotNull Integer id) implements FindType {
    }
}
