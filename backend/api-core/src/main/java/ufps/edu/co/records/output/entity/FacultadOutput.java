package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record FacultadOutput(
        Integer id,
        String nombre,
        String correo,
        List<CargoOutput> cargoList,
        List<ProgramaOutput> programaList)
        implements OutputResponse {
}
