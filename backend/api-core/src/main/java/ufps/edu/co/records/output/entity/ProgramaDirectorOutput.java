package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record ProgramaDirectorOutput(
        Integer idUsuario,
        AdministrativoOutput administrativo,
        PersonaOutput persona,
        ProgramaOutput programa) implements OutputResponse {
}