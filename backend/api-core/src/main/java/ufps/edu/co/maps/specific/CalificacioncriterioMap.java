package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.CalificacioncriterioInput.*;
import ufps.edu.co.records.output.entity.CalificacioncriterioOutput;
import ufps.edu.co.rest.dto.CalificacioncriterioDTO;

@Component
public class CalificacioncriterioMap extends
        GlobalMapper<CALIFICACIONCRITERIO_CREATE, CALIFICACIONCRITERIO_UPDATE, CALIFICACIONCRITERIO_DELETE, CALIFICACIONCRITERIO_PATCH, CALIFICACIONCRITERIO_FIND, CalificacioncriterioOutput, CalificacioncriterioDTO> {

    @Autowired private AspiranteMap aspiranteMap;
    @Autowired private CriterioevaluacionMap criterioevaluacionMap;

    public CalificacioncriterioMap() {
        super(CALIFICACIONCRITERIO_CREATE.class, CALIFICACIONCRITERIO_UPDATE.class,
                CALIFICACIONCRITERIO_DELETE.class, CALIFICACIONCRITERIO_PATCH.class,
                CALIFICACIONCRITERIO_FIND.class);
    }

    @Override
    protected CalificacioncriterioDTO toDtoCreate(CALIFICACIONCRITERIO_CREATE input) {
        return CalificacioncriterioDTO.builder()
                .idAspirante(input.idAspirante())
                .idCriterio(input.idCriterio())
                .puntuacion(input.puntuacion())
                .observaciones(input.observaciones())
                .build();
    }

    @Override
    protected CalificacioncriterioDTO toDtoUpdate(CALIFICACIONCRITERIO_UPDATE input) {
        return CalificacioncriterioDTO.builder()
                .id(input.id())
                .idAspirante(input.idAspirante())
                .idCriterio(input.idCriterio())
                .puntuacion(input.puntuacion())
                .observaciones(input.observaciones())
                .build();
    }

    @Override
    protected CalificacioncriterioDTO toDtoDelete(CALIFICACIONCRITERIO_DELETE input) {
        return CalificacioncriterioDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected CalificacioncriterioDTO toDtoPatch(CALIFICACIONCRITERIO_PATCH input) {
        CalificacioncriterioDTO.CalificacioncriterioDTOBuilder builder = CalificacioncriterioDTO.builder()
                .id(input.id());

        if (input.idAspirante() != null) builder.idAspirante(input.idAspirante());
        if (input.idCriterio() != null) builder.idCriterio(input.idCriterio());
        if (input.puntuacion() != null) builder.puntuacion(input.puntuacion());
        if (input.pesoSnapshot() != null) builder.pesoSnapshot(input.pesoSnapshot());
        if (input.observaciones() != null) builder.observaciones(input.observaciones());

        return builder.build();
    }

    @Override
    protected CalificacioncriterioDTO toDtoFind(CALIFICACIONCRITERIO_FIND input) {
        return CalificacioncriterioDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    public CalificacioncriterioOutput toOutput(CalificacioncriterioDTO dto) {
        if (dto == null) return null;
        return CalificacioncriterioOutput.builder()
                .id(dto.getId())
                .idAspirante(dto.getIdAspirante())
                .idCriterio(dto.getIdCriterio())
                .puntuacion(dto.getPuntuacion())
                .pesoSnapshot(dto.getPesoSnapshot())
                .observaciones(dto.getObservaciones())
                .aspirante(dto.getAspirante() != null ? aspiranteMap.toOutput(dto.getAspirante()) : null)
                .criterioevaluacion(dto.getCriterioevaluacion() != null ? criterioevaluacionMap.toOutput(dto.getCriterioevaluacion()) : null)
                .build();
    }

    public List<CalificacioncriterioOutput> toOutputList(List<CalificacioncriterioDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
