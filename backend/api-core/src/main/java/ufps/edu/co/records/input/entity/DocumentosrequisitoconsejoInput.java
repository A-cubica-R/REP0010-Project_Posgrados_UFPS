package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public class DocumentosrequisitoconsejoInput {
    public record DOCUMENTOSREQUISITOCONSEJO_CREATE(
            String nombre,
            Integer tamanomaximo) implements CreateType {
    }

    public record DOCUMENTOSREQUISITOCONSEJO_UPDATE(
            @NotNull Integer id,
            String nombre,
            Integer tamanomaximo) implements UpdateType {
    }

    public record DOCUMENTOSREQUISITOCONSEJO_PATCH(
            @NotNull Integer id,
            String nombre,
            Integer tamanomaximo) implements PatchType {
    }

    public record DOCUMENTOSREQUISITOCONSEJO_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record DOCUMENTOSREQUISITOCONSEJO_FIND(
            @NotNull Integer id) implements FindType {
    }
}
