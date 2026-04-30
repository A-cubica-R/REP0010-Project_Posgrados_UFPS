package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.OfertaacademicaInput.*;
import ufps.edu.co.records.output.OfertaacademicaOutput;
import ufps.edu.co.rest.dto.OfertaacademicaDTO;

@Component
public class OfertaacademicaMap extends GlobalMapper<OFERTAACADEMICA_CREATE, OFERTAACADEMICA_UPDATE, OFERTAACADEMICA_DELETE, OFERTAACADEMICA_PATCH, OFERTAACADEMICA_FIND, OfertaacademicaOutput, OfertaacademicaDTO> {

    public OfertaacademicaMap() {
        super(OFERTAACADEMICA_CREATE.class, OFERTAACADEMICA_UPDATE.class, OFERTAACADEMICA_DELETE.class, OFERTAACADEMICA_PATCH.class, OFERTAACADEMICA_FIND.class);
    }

    @Override
    protected OfertaacademicaDTO toDtoCreate(OFERTAACADEMICA_CREATE input) {
        OfertaacademicaDTO dto = new OfertaacademicaDTO();
        dto.setEncuentros(input.encuentros());
        dto.setIdPrograma(input.idPrograma());
        dto.setIdModalidad(input.idModalidad());
        dto.setIdJornada(input.idJornada());
        return dto;
    }

    @Override
    protected OfertaacademicaDTO toDtoUpdate(OFERTAACADEMICA_UPDATE input) {
        OfertaacademicaDTO dto = new OfertaacademicaDTO();
        dto.setId(input.id());
        dto.setEncuentros(input.encuentros());
        dto.setIdPrograma(input.idPrograma());
        dto.setIdModalidad(input.idModalidad());
        dto.setIdJornada(input.idJornada());
        return dto;
    }

    @Override
    protected OfertaacademicaDTO toDtoDelete(OFERTAACADEMICA_DELETE input) {
        OfertaacademicaDTO dto = new OfertaacademicaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected OfertaacademicaDTO toDtoPatch(OFERTAACADEMICA_PATCH input) {
        OfertaacademicaDTO dto = new OfertaacademicaDTO();
        dto.setId(input.id());
        if (input.encuentros() != null) dto.setEncuentros(input.encuentros());
        if (input.idPrograma() != null) dto.setIdPrograma(input.idPrograma());
        if (input.idModalidad() != null) dto.setIdModalidad(input.idModalidad());
        if (input.idJornada() != null) dto.setIdJornada(input.idJornada());
        return dto;
    }

    @Override
    protected OfertaacademicaDTO toDtoFind(OFERTAACADEMICA_FIND input) {
        OfertaacademicaDTO dto = new OfertaacademicaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public OfertaacademicaOutput toOutput(OfertaacademicaDTO dto) {
        if (dto == null) return null;
        return OfertaacademicaOutput.builder()
                .id(dto.getId())
                .encuentros(dto.getEncuentros())
                .idPrograma(dto.getIdPrograma())
                .idModalidad(dto.getIdModalidad())
                .idJornada(dto.getIdJornada())
                .build();
    }

    public List<OfertaacademicaOutput> toOutputList(List<OfertaacademicaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
