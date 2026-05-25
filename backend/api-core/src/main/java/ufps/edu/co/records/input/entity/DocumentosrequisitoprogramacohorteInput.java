package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public class DocumentosrequisitoprogramacohorteInput {
    public record DOCUMENTOSREQUISITOPROGRAMACOHORTE_CREATE(
            Integer idDocrequisito,
            Integer idCohorte) implements CreateType {
    }

    public record DOCUMENTOSREQUISITOPROGRAMACOHORTE_UPDATE(
            @NotNull Integer id,
            Integer idDocrequisito,
            Integer idCohorte) implements UpdateType {
    }

    public record DOCUMENTOSREQUISITOPROGRAMACOHORTE_PATCH(
            @NotNull Integer id,
            Integer idDocrequisito,
            Integer idCohorte) implements PatchType {
    }

    public record DOCUMENTOSREQUISITOPROGRAMACOHORTE_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record DOCUMENTOSREQUISITOPROGRAMACOHORTE_FIND(
            @NotNull Integer id) implements FindType {
    }
}
