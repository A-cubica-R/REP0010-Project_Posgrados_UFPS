package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public class DocumentosrequisitoconcejocohorteInput {
    public record DOCUMENTOSREQUISITOCONCEJOCOHORTE_CREATE(
            Integer idDocrequisito,
            Integer idCohorte) implements CreateType {
    }

    public record DOCUMENTOSREQUISITOCONCEJOCOHORTE_UPDATE(
            @NotNull Integer id,
            Integer idDocrequisito,
            Integer idCohorte) implements UpdateType {
    }

    public record DOCUMENTOSREQUISITOCONCEJOCOHORTE_PATCH(
            @NotNull Integer id,
            Integer idDocrequisito,
            Integer idCohorte) implements PatchType {
    }

    public record DOCUMENTOSREQUISITOCONCEJOCOHORTE_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record DOCUMENTOSREQUISITOCONCEJOCOHORTE_FIND(
            @NotNull Integer id) implements FindType {
    }
}
