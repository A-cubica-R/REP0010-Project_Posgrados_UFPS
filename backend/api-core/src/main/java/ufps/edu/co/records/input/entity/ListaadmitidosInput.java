package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;

public enum ListaadmitidosInput {
    ;

    public record GENERATE_LISTA(
            @NotNull Integer idCohorte,
            @NotNull Integer idAdministrativo
    ) {}

    public record RECHAZAR_ASPIRANTE(
            @NotNull Integer idAspiranteRechazado,
            @NotNull Integer idCohorte,
            @NotNull Integer idAdministrativo
    ) {}
}
