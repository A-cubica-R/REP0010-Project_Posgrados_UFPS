package ufps.edu.co.records.input.entity;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CalificacioncriterioInput {
    ;

    public record CALIFICACIONCRITERIO_CREATE(
            @NotNull Integer idAspirante,
            @NotNull Integer idCriterio,
            @NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal puntuacion,
            String observaciones) implements CreateType {
    }

    public record CALIFICACIONCRITERIO_UPDATE(
            @NotNull Integer id,
            @NotNull Integer idAspirante,
            @NotNull Integer idCriterio,
            @NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal puntuacion,
            String observaciones) implements UpdateType {
    }

    public record CALIFICACIONCRITERIO_PATCH(
            @NotNull Integer id,
            Integer idAspirante,
            Integer idCriterio,
            @DecimalMin("0") @DecimalMax("100") BigDecimal puntuacion,
            @DecimalMin(value = "0", inclusive = false) BigDecimal pesoSnapshot,
            String observaciones) implements PatchType {
    }

    public record CALIFICACIONCRITERIO_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record CALIFICACIONCRITERIO_FIND(
            @NotNull Integer id) implements FindType {
    }
}
