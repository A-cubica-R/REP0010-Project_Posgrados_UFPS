package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import ufps.edu.co.records.contracts.*;

public enum EntrevistaInput {
    ;

    public record ENTREVISTA_CREATE(@NotNull LocalDate fecha, @NotNull Float calificacion, @NotNull Integer idTipoentrevista, @NotNull Integer idEntrevistador, @NotNull Integer idAspirante, @NotNull Integer idEstado) implements CreateType {}

    public record ENTREVISTA_UPDATE(@NotNull Integer id, @NotNull LocalDate fecha, @NotNull Float calificacion, @NotNull Integer idTipoentrevista, @NotNull Integer idEntrevistador, @NotNull Integer idAspirante, @NotNull Integer idEstado) implements UpdateType {}

    public record ENTREVISTA_PATCH(@NotNull Integer id, LocalDate fecha, Float calificacion, Integer idTipoentrevista, Integer idEntrevistador, Integer idAspirante, Integer idEstado) implements PatchType {}

    public record ENTREVISTA_DELETE(@NotNull Integer id) implements DeleteType {}

    public record ENTREVISTA_FIND(@NotNull Integer id) implements FindType {}
}
