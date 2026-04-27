package ufps.edu.co.records.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public enum PaisRequest {;

    public record CREATE(
        @NotBlank String nombre
    ) {};

    public record DELETE(
        @NotNull Integer id
    ) {};

    public record UPDATE(
        @NotNull Integer id,
        @NotBlank String nombre
    ) {};

    public record PATCH(
        @NotNull Integer id,
        @NotBlank String nombre
    ) {};

    public record FIND(
        @NotNull Integer id
    ) {};
}
