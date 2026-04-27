package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import ufps.edu.co.records.InputRequest;

public enum DepartamentoInput {
        ;

        public record CREATE(
                        @NotBlank @Getter String nombre,
                        @NotNull @Getter Integer idPais)
                        implements InputRequest {
        };

        public record DELETE(
                        @NotNull @Getter Integer id)
                        implements InputRequest {
        };

        public record UPDATE(
                        @NotBlank @Getter String nombre,
                        @NotNull @Getter Integer idPais)
                        implements InputRequest {
        };

        public record PATCH(
                        @Getter String nombre,
                        @Getter Integer idPais)
                        implements InputRequest {
        };

        public record FIND(
                        @NotNull @Getter Integer id)
                        implements InputRequest {
        };
}
