package ufps.edu.co.records.input.entity;

import java.math.BigDecimal;

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
}
