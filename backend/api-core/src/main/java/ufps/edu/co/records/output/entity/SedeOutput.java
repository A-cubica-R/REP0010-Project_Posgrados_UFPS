package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record SedeOutput(
        Integer id,
        String nombre,
        Integer idUbicacion,
        UbicacionOutput ubicacion,
        List<ProgramaOutput> programaList
) implements OutputResponse {
}
