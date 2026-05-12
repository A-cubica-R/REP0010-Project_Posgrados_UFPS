package ufps.edu.co.records.input.entity;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CriterioaceptacionInput {
        ;

        public record CRITERIOACEPTACION_CREATE(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        String descripcion,
                        @NotNull BigDecimal peso,
                        @NotNull Integer idCohorte) implements CreateType {
        }

        public record CRITERIOACEPTACION_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        String descripcion,
                        @NotNull BigDecimal peso,
                        @NotNull Integer idCohorte) implements UpdateType {
        }

        public record CRITERIOACEPTACION_PATCH(
                        @NotNull Integer id,
                        String nombre,
                        String descripcion,
                        BigDecimal peso,
                        Integer idCohorte) implements PatchType {
        }

        public record CRITERIOACEPTACION_DELETE(
                        @NotNull Integer id) implements DeleteType {
        }

        public record CRITERIOACEPTACION_FIND(
                        @NotNull Integer id) implements FindType {
        }
}
