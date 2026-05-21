package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public class DocumentocohorteInput {
    public record DOCUMENTOCOHORTE_CREATE(
            @NotBlank String nombre,
            @NotNull Boolean obligatorio,
            @NotNull Integer idCohorte) implements CreateType {
    }

    public record DOCUMENTOCOHORTE_UPDATE(
            @NotNull Integer id,
            @NotBlank String nombre,
            @NotNull Boolean obligatorio,
            @NotNull Integer idCohorte) implements UpdateType {
    }

    public record DOCUMENTOCOHORTE_PATCH(
            @NotNull Integer id,
            String nombre,
            Boolean obligatorio,
            Integer idCohorte) implements PatchType {
    }

    public record DOCUMENTOCOHORTE_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record DOCUMENTOCOHORTE_FIND(
            @NotNull Integer id) implements FindType {
    }
}