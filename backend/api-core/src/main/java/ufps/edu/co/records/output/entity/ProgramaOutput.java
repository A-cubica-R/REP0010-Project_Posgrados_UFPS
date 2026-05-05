package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record ProgramaOutput(
                Integer id,
                Integer codigo,
                String nombre,
                Integer semestres,
                String correo,
                String registrosnies,
                String nivelformacion,
                String titulo,
                String rcmineducacion,
                Integer creditos,
                String periodicidad,
                Float valormatricula,
                SedeOutput sede,
                AdministrativoOutput administrativo,
                FacultadOutput facultad) implements OutputResponse {
}
