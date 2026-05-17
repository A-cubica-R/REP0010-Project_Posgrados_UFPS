package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record UbicacionOutput(
        Integer id,
        String direccion,
        Integer idMunicipio,
        MunicipioOutput municipio,
        List<EntrevistaOutput> entrevistaList,
        List<PruebaOutput> pruebaList,
        List<SedeOutput> sedeList
) implements OutputResponse {
}