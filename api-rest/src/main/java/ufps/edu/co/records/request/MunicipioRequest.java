package ufps.edu.co.records.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public enum MunicipioRequest {;

    public record Create(
        @NotBlank String nombre,
        @NotNull Integer departamentoId
    ) {};

    public record Delete(
        @NotNull Integer id
    ) {};

    public record Update(
        @NotNull Integer id,
        @NotBlank String nombre,
        @NotNull Integer departamentoId
    ) {};

    public record Patch(
        @NotNull Integer id,
        String nombre,
        Integer departamentoId
    ) {};
}
