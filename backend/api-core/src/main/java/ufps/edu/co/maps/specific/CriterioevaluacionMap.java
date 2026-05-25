package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;

import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.*;
import ufps.edu.co.records.output.entity.CriterioevaluacionOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;

@Component
@UniversalMapping(
    create = CRITERIOEVALUACION_CREATE.class, 
    update = CRITERIOEVALUACION_UPDATE.class, 
    delete = CRITERIOEVALUACION_DELETE.class, 
    patch = CRITERIOEVALUACION_PATCH.class, 
    find = CRITERIOEVALUACION_FIND.class)
public class CriterioevaluacionMap extends
        UniversalMapper<CriterioevaluacionOutput, CriterioevaluacionDTO> {

    private final ProgramaMap programaMap = new ProgramaMap();

    @Override
    public CriterioevaluacionOutput toOutput(CriterioevaluacionDTO dto) {
        if (dto != null) {

            ProgramaOutput programaOutput = null;

            if (dto.getIdprograma() != null && dto.getPrograma() != null) {
                programaOutput = programaMap.toOutput(dto.getPrograma());
            }

            return CriterioevaluacionOutput.builder()
                    .id(dto.getId())
                    .nombre(dto.getNombre())
                    .activo(dto.getActivo())
                    .descripcion(dto.getDescripcion())
                    .peso(dto.getPeso())
                    .idPrograma(dto.getIdprograma())
                    .programa(programaOutput)
                    .build();
        }
        return null;
    }

    public List<CriterioevaluacionOutput> toOutputList(List<CriterioevaluacionDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
