package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CargoOutput(
        Integer id,
        String nombre,
        String descripcion,
        FacultadOutput facultad,
        ProgramaOutput programa,
        List<AdministrativoOutput> administrativoList) implements OutputResponse {
}
