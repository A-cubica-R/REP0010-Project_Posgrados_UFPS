package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.CriterioaceptacionInput.*;
import ufps.edu.co.records.output.entity.CriterioaceptacionOutput;
import ufps.edu.co.rest.dto.CriterioaceptacionDTO;

@Component
public class CriterioaceptacionMap extends
        GlobalMapper<CRITERIOACEPTACION_CREATE, CRITERIOACEPTACION_UPDATE, CRITERIOACEPTACION_DELETE, CRITERIOACEPTACION_PATCH, CRITERIOACEPTACION_FIND, CriterioaceptacionOutput, CriterioaceptacionDTO> {

    @Autowired private CohorteMap cohorteMap;

    public CriterioaceptacionMap() {
        super(CRITERIOACEPTACION_CREATE.class, CRITERIOACEPTACION_UPDATE.class, CRITERIOACEPTACION_DELETE.class,
                CRITERIOACEPTACION_PATCH.class, CRITERIOACEPTACION_FIND.class);
    }

    @Override
    protected CriterioaceptacionDTO toDtoCreate(CRITERIOACEPTACION_CREATE input) {
        return CriterioaceptacionDTO.builder()
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .peso(input.peso())
                .idCohorte(input.idCohorte())
                .build();
    }

    @Override
    protected CriterioaceptacionDTO toDtoUpdate(CRITERIOACEPTACION_UPDATE input) {
        return CriterioaceptacionDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .peso(input.peso())
                .idCohorte(input.idCohorte())
                .build();
    }

    @Override
    protected CriterioaceptacionDTO toDtoDelete(CRITERIOACEPTACION_DELETE input) {
        return CriterioaceptacionDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected CriterioaceptacionDTO toDtoPatch(CRITERIOACEPTACION_PATCH input) {
        return CriterioaceptacionDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .peso(input.peso())
                .idCohorte(input.idCohorte())
                .build();
    }

    @Override
    protected CriterioaceptacionDTO toDtoFind(CRITERIOACEPTACION_FIND input) {
        return CriterioaceptacionDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    public CriterioaceptacionOutput toOutput(CriterioaceptacionDTO dto) {
        if (dto == null) return null;
        return CriterioaceptacionOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .peso(dto.getPeso())
                .idCohorte(dto.getIdCohorte())
                .cohorte(dto.getCohorte() != null ? cohorteMap.toOutput(dto.getCohorte()) : null)
                .build();
    }

    public List<CriterioaceptacionOutput> toOutputList(List<CriterioaceptacionDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
