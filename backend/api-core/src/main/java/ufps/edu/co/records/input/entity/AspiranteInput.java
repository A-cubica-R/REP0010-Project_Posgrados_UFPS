package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;
import java.math.BigDecimal;

public enum AspiranteInput {
    ;

    public record ASPIRANTE_CREATE(
            BigDecimal puntuacion,
            @NotNull Integer idCohorte,
            @NotNull Integer idEstado,
            @NotNull Integer idPersona,
            @NotNull Integer idTipovinculacion) implements CreateType {
    }

    public record ASPIRANTE_UPDATE(
            @NotNull Integer id,
            BigDecimal puntuacion,
            @NotNull Integer idCohorte,
            @NotNull Integer idEstado,
            @NotNull Integer idPersona,
            @NotNull Integer idTipovinculacion) implements UpdateType {
    }

    public record ASPIRANTE_PATCH(
            @NotNull Integer id,
            BigDecimal puntuacion,
            Integer idCohorte,
            Integer idEstado,
            Integer idPersona,
            Integer idTipovinculacion) implements PatchType {
    }

    public record ASPIRANTE_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record ASPIRANTE_FIND(
            @NotNull Integer id) implements FindType {
    }
}
