package ufps.edu.co.records.input.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CriterioevaluacionInput {
        ;

        public record CRITERIOEVALUACION_CREATE(
                        @NotBlank String nombre,
                        String descripcion,
                        @NotNull BigDecimal peso,
                        @NotNull Integer idCohorte) implements CreateType {
        }

        public record CRITERIOEVALUACION_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        String descripcion,
                        @NotNull BigDecimal peso,
                        @NotNull Integer idCohorte) implements UpdateType {
        }

        public record CRITERIOEVALUACION_PATCH(
                        @NotNull Integer id,
                        String nombre,
                        String descripcion,
                        BigDecimal peso,
                        Integer idCohorte) implements PatchType {
        }

        public record CRITERIOEVALUACION_DELETE(
                        @NotNull Integer id) implements DeleteType {
        }

        public record CRITERIOEVALUACION_FIND(
                        @NotNull Integer id) implements FindType {
        }

        public record CRITERIO_CREATE_BODY(
                        @NotBlank String nombre,
                        String descripcion,
                        @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal peso) implements CreateType {
        }

        public record CRITERIO_UPDATE_BODY(
                        @NotBlank String nombre,
                        String descripcion,
                        @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal peso) implements UpdateType {
        }

        public record CRITERIO_BULK_ITEM(
                        Integer id,
                        @NotBlank String nombre,
                        String descripcion,
                        @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal peso) {
        }

        public record CRITERIO_BULK_SAVE(
                        @NotNull List<CRITERIO_BULK_ITEM> criterios) implements CreateType {
        }
}
