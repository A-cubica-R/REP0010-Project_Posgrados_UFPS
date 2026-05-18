package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record ProgramaOutput(
                Integer id,
                Integer codigo,
                String nombre,
                Integer semestres,
                Integer duracion,
                String correo,
                String registrosnies,
                String nivelformacion,
                String titulo,
                String rcmineducacion,
                Integer creditos,
                String periodicidad,
                BigDecimal valormatricula,
                Integer idFacultad,
                Integer idOtros,
                Integer idSede,
                Integer idTiporegistro,
                SedeOutput sede,
                AdministrativoOutput administrativo,
                FacultadOutput facultad,
                OtrosvaloresOutput otrosvalores,
                TiporegistroOutput tiporegistro,
                List<CargoOutput> cargoList,
                List<CohorteOutput> cohorteList) implements OutputResponse {
}
