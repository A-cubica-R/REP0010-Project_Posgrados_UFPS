package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.*;
import ufps.edu.co.records.output.entity.CriterioevaluacionOutput;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;

@Component
public class CriterioevaluacionMap extends
        GlobalMapper<CRITERIOEVALUACION_CREATE, CRITERIOEVALUACION_UPDATE, CRITERIOEVALUACION_DELETE, CRITERIOEVALUACION_PATCH, CRITERIOEVALUACION_FIND, CriterioevaluacionOutput, CriterioevaluacionDTO> {

    public CriterioevaluacionMap() {
        super(CRITERIOEVALUACION_CREATE.class, CRITERIOEVALUACION_UPDATE.class, CRITERIOEVALUACION_DELETE.class,
                CRITERIOEVALUACION_PATCH.class, CRITERIOEVALUACION_FIND.class);
    }

    @Override
    protected CriterioevaluacionDTO toDtoCreate(CRITERIOEVALUACION_CREATE input) {
        return CriterioevaluacionDTO.builder()
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .peso(input.peso())
                .idCohorte(input.idCohorte())
                .build();
    }

    @Override
    protected CriterioevaluacionDTO toDtoUpdate(CRITERIOEVALUACION_UPDATE input) {
        return CriterioevaluacionDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .peso(input.peso())
                .idCohorte(input.idCohorte())
                .build();
    }

    @Override
    protected CriterioevaluacionDTO toDtoDelete(CRITERIOEVALUACION_DELETE input) {
        return CriterioevaluacionDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected CriterioevaluacionDTO toDtoPatch(CRITERIOEVALUACION_PATCH input) {
        return CriterioevaluacionDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .peso(input.peso())
                .idCohorte(input.idCohorte())
                .build();
    }

    @Override
    protected CriterioevaluacionDTO toDtoFind(CRITERIOEVALUACION_FIND input) {
        return CriterioevaluacionDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    public CriterioevaluacionOutput toOutput(CriterioevaluacionDTO dto) {
        if (dto == null)
            return null;

        CohorteMap cohorteMap = new CohorteMap();
        
        return CriterioevaluacionOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .peso(dto.getPeso())
                .idCohorte(dto.getIdCohorte())
                .cohorte(dto.getCohorte() != null ? cohorteMap.toOutput(dto.getCohorte()) : null)
                .build();
    }

    public List<CriterioevaluacionOutput> toOutputList(List<CriterioevaluacionDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
