package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

import ufps.edu.co.records.contracts.*;

public enum EntrevistaInput {
    ;

    public record ENTREVISTA_CREATE(@NotNull LocalDate fecha, @NotNull LocalTime tiempo, @NotNull Integer idTipoentrevista, @NotNull Integer idEntrevistador, @NotNull Integer idAspirante, @NotNull Integer idEstado, @NotNull Integer idUbicacion) implements CreateType {}

    public record ENTREVISTA_UPDATE(@NotNull Integer id, @NotNull LocalDate fecha, @NotNull LocalTime tiempo, @NotNull Float calificacion, @NotNull Integer idTipoentrevista, @NotNull Integer idEntrevistador, @NotNull Integer idAspirante, @NotNull Integer idEstado, @NotNull Integer idUbicacion) implements UpdateType {}

    public record ENTREVISTA_PATCH(@NotNull Integer id, LocalDate fecha, @NotNull LocalTime tiempo, Float calificacion, Integer idTipoentrevista, Integer idEntrevistador, Integer idEstado, Integer idUbicacion) implements PatchType {}

    public record ENTREVISTA_DELETE(@NotNull Integer id) implements DeleteType {}

    public record ENTREVISTA_FIND(@NotNull Integer id) implements FindType {}

    public record ENTREVISTA_RATE(@NotNull Integer id, @NotNull Float calificacion) implements PatchType {}
}
