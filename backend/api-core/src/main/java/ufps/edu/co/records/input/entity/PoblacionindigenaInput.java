package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum PoblacionindigenaInput {
        ;

        public record POBLACIONINDIGENA_CREATE(
                        @NotBlank String poblacion) implements CreateType {
        }

        public record POBLACIONINDIGENA_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String poblacion) implements UpdateType {
        }

        public record POBLACIONINDIGENA_PATCH(
                        @NotNull Integer id,
                        String poblacion) implements PatchType {
        }

        public record POBLACIONINDIGENA_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record POBLACIONINDIGENA_FIND(@NotNull Integer id) implements FindType {
        }
}
