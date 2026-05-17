package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CapacidadexepcionalInput {
        ;

        public record CAPACIDADEXEPCIONAL_CREATE(
                        @NotBlank String tipocapacidad) implements CreateType {
        }

        public record CAPACIDADEXEPCIONAL_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String tipocapacidad) implements UpdateType {
        }

        public record CAPACIDADEXEPCIONAL_PATCH(
                        @NotNull Integer id,
                        String tipocapacidad) implements PatchType {
        }

        public record CAPACIDADEXEPCIONAL_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record CAPACIDADEXEPCIONAL_FIND(@NotNull Integer id) implements FindType {
        }
}
