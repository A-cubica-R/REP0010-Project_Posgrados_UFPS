package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import ufps.edu.co.records.contracts.*;

public enum EntrevistaInput {
    ;

    public record ENTREVISTA_CREATE(
            @NotNull LocalDate fecha,
            @NotNull LocalTime tiempo,
            @NotNull Integer idTipoentrevista,
            @NotNull Integer idAspirante,
            @NotBlank String ubicacion) implements CreateType {
    }

    public record ENTREVISTA_UPDATE(
            @NotNull Integer id,
            @NotNull BigDecimal calificacion,
            @NotNull LocalDate fecha,
            @NotNull LocalTime tiempo,
            @NotNull Integer idAspirante,
            @NotNull Integer idEstado,
            @NotNull Integer idTipoentrevista,
            @NotBlank String ubicacion) implements UpdateType {
    }

    public record ENTREVISTA_RESCHEDULE(
            @NotNull Integer id,
            @NotNull LocalDate fecha,
            @NotNull LocalTime tiempo,
            @NotNull Integer idTipoentrevista,
            @NotBlank String ubicacion) implements UpdateType {
    }

    public record ENTREVISTA_PATCH(
            @NotNull Integer id,
            BigDecimal calificacion,
            LocalDate fecha,
            LocalTime tiempo,
            Integer idAspirante,
            Integer idEstado,
            Integer idTipoentrevista,
            String ubicacion) implements PatchType {
    }

    public record ENTREVISTA_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record ENTREVISTA_FIND(
            @NotNull Integer id) implements FindType {
    }

    public record ENTREVISTA_RATE(
            @NotNull Integer id,
            @NotNull BigDecimal calificacion) implements PatchType {
    }
}
