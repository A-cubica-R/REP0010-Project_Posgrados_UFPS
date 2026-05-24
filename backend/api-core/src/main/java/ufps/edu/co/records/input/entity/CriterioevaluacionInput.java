package ufps.edu.co.records.input.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ufps.edu.co.records.contracts.*;

public enum CriterioevaluacionInput {
        ;

        @Builder
        public record CRITERIOEVALUACION_CREATE(
                        @NotBlank String nombre,
                        Boolean activo,
                        String descripcion,
                        @NotNull BigDecimal peso,
                        @NotNull Integer idPrograma) implements CreateType {
        }

        @Builder
        public record CRITERIOEVALUACION_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        Boolean activo,
                        String descripcion,
                        @NotNull BigDecimal peso,
                        @NotNull Integer idPrograma) implements UpdateType {
        }

        public record CRITERIOEVALUACION_PATCH(
                        @NotNull Integer id,
                        String nombre,
                        Boolean activo,
                        String descripcion,
                        BigDecimal peso,
                        Integer idCohorte,
                        Integer idPrograma) implements PatchType {
        }

        @Builder
        public record CRITERIOEVALUACION_DELETE(
                        @NotNull Integer id) implements DeleteType {
        }

        @Builder
        public record CRITERIOEVALUACION_FIND(
                        @NotNull Integer id) implements FindType {
        }

        @Builder
        public record CRITERIO_CREATE_BODY(
                        @NotBlank String nombre,
                        Boolean activo,
                        String descripcion,
                        @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal peso) implements CreateType {
        }

        @Builder
        public record CRITERIO_UPDATE_BODY(
                        @NotBlank String nombre,
                        Boolean activo,
                        String descripcion,
                        @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal peso) implements UpdateType {
        }

        @Builder
        public record CRITERIO_BULK_ITEM(
                        Integer id,
                        @NotBlank String nombre,
                        Boolean activo,
                        String descripcion,
                        @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal peso) {
        }

        @Builder
        public record CRITERIO_BULK_SAVE(
                        @NotNull List<CRITERIO_BULK_ITEM> criterios) implements CreateType {
        }
}
