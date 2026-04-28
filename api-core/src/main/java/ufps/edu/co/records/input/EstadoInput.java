package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum EstadoInput {
        ;

        public record ESTADO_CREATE(
                        @NotBlank String tipo) implements CreateType {
        };

        public record ESTADO_DELETE(
                        @NotNull Integer id) implements DeleteType {
        };

        public record ESTADO_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String tipo) implements UpdateType {
        };

        public record ESTADO_PATCH(
                        @NotNull Integer id,
                        String tipo) implements PatchType {
        };

        public record ESTADO_FIND(
                        @NotNull Integer id) implements FindType {
        };
}
