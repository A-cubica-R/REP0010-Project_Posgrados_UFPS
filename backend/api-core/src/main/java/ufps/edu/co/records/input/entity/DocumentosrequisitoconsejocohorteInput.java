package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public class DocumentosrequisitoconsejocohorteInput {
    public record DOCUMENTOSREQUISITOCONSEJOCOHORTE_CREATE(
            Integer idDocrequisito,
            Integer idCohorte) implements CreateType {
    }

    public record DOCUMENTOSREQUISITOCONSEJOCOHORTE_UPDATE(
            @NotNull Integer id,
            Integer idDocrequisito,
            Integer idCohorte) implements UpdateType {
    }

    public record DOCUMENTOSREQUISITOCONSEJOCOHORTE_PATCH(
            @NotNull Integer id,
            Integer idDocrequisito,
            Integer idCohorte) implements PatchType {
    }

    public record DOCUMENTOSREQUISITOCONSEJOCOHORTE_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record DOCUMENTOSREQUISITOCONSEJOCOHORTE_FIND(
            @NotNull Integer id) implements FindType {
    }
}
