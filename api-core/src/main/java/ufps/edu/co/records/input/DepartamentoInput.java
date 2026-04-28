package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public enum DepartamentoInput implements InputRequest {
        ;

        public record DEPARTAMENTO_CREATE(
                        @NotBlank @Getter String nombre,
                        @NotNull @Getter Integer idPais)
                        implements CreateType {
        };

        public record DEPARTAMENTO_DELETE(
                        @NotNull @Getter Integer id)
                        implements DeleteType {
        };

        public record DEPARTAMENTO_UPDATE(
                        @NotNull @Getter Integer id,
                        @NotBlank @Getter String nombre,
                        @NotNull @Getter Integer idPais)
                        implements UpdateType {
        };

        public record DEPARTAMENTO_PATCH(
                        @NotNull @Getter Integer id,
                        @Getter String nombre,
                        @Getter Integer idPais)
                        implements PatchType {
        };

        public record DEPARTAMENTO_FIND(
                        @NotNull @Getter Integer id)
                        implements FindType {
        };
}
