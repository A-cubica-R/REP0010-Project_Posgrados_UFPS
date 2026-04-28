package ufps.edu.co.records.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CohorteInput {
    ;

    public record COHORTE_CREATE(
        @NotBlank String nombre,
        @NotNull Integer idEstado,
        @NotNull LocalDate fechaInicio,
        LocalDate fechaFin) implements CreateType {
    }

    public record COHORTE_UPDATE(
        @NotNull Integer id,
        @NotBlank String nombre,
        @NotNull Integer idEstado,
        @NotNull LocalDate fechaInicio,
        LocalDate fechaFin) implements UpdateType {
    }

    public record COHORTE_PATCH(
        @NotNull Integer id,
        @NotBlank String nombre,
        @NotNull Integer idEstado,
        @NotNull LocalDate fechaInicio,
        LocalDate fechaFin) implements PatchType {
    }

    public record COHORTE_DELETE(@NotNull Integer id) implements DeleteType {
    }

    public record COHORTE_FIND(@NotNull Integer id) implements FindType {
    }
}
