package ufps.edu.co.records.output;

import java.util.List;

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
        Integer idSede,
        Integer idAdministrativo,
        Integer idFacultad,
        Integer idOtros,
        SedeOutput sede,
        AdministrativoOutput administrativo,
        FacultadOutput facultad,
        List<CargoOutput> cargoList,
        List<OfertaacademicaOutput> ofertaacademicaList) implements OutputResponse {
}
