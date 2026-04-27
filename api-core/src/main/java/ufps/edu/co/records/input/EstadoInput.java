package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.InputRequest;

public enum EstadoInput implements InputRequest {
    ;

    public record CREATE(
            @NotBlank String tipo) implements InputRequest {
    };

    public record DELETE(
            @NotNull Integer id) implements InputRequest {
    };

    public record UPDATE(
            @NotNull Integer id,
            @NotBlank String tipo) implements InputRequest {
    };

    public record PATCH(
            @NotNull Integer id,
            String tipo) implements InputRequest {
    };

    public record FIND(
            @NotNull Integer id) implements InputRequest {
    };
}
