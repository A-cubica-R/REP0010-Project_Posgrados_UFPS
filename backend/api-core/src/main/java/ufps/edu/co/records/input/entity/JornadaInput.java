package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum JornadaInput {
        ;

        public record JORNADA_CREATE(
                        @NotBlank String tipo) implements CreateType {
        };

        public record JORNADA_DELETE(
                        @NotNull Integer id) implements DeleteType {
        };

        public record JORNADA_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String tipo) implements UpdateType {
        };

        public record JORNADA_PATCH(
                        @NotNull Integer id,
                        @NotBlank String tipo) implements PatchType {
        };

        public record JORNADA_FIND(
                        @NotNull Integer id) implements FindType {
        };

}
