package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.InputRequest;

public enum PaisInput implements InputRequest {
        ;

        public record CREATE(@NotBlank String nombre) implements InputRequest {
        }

        public record DELETE(@NotNull Integer id) implements InputRequest {
        }

        public record UPDATE(@NotBlank String nombre) implements InputRequest {
        }

        public record PATCH(@NotBlank String nombre) implements InputRequest {
        }

        public record FIND(@NotNull Integer id) implements InputRequest {
        }
}
