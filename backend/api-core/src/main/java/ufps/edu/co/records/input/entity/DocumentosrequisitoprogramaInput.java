package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public class DocumentosrequisitoprogramaInput {

        
        public record DOCUMENTOSREQUISITOPROGRAMA_CREATE(
                        String nombre,
                        Integer idPrograma) implements CreateType {
        }

        @Builder
        public record DOCUMENTOSREQUISITOPROGRAMA_CREATEDOCUMENT(
                        String nombre,
                        Integer tamanomaximo,
                        String urlformato) implements CreateType {
        }

        public record DOCUMENTOSREQUISITOPROGRAMA_UPDATE(
                        @NotNull Integer id,
                        String nombre,
                        Integer tamanomaximo,
                        String urlformato) implements UpdateType {
        }

        public record DOCUMENTOSREQUISITOPROGRAMA_PATCH(
                        @NotNull Integer id,
                        String nombre,
                        Integer tamanomaximo,
                        String urlformato) implements PatchType {
        }

        public record DOCUMENTOSREQUISITOPROGRAMA_DELETE(
                        @NotNull Integer id) implements DeleteType {
        }

        public record DOCUMENTOSREQUISITOPROGRAMA_FIND(
                        @NotNull Integer id) implements FindType {
        }
}
