package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.PoblacionindigenaInput.*;
import ufps.edu.co.records.output.entity.PoblacionindigenaOutput;
import ufps.edu.co.rest.dto.PoblacionindigenaDTO;

@Component
public class PoblacionindigenaMap extends GlobalMapper<POBLACIONINDIGENA_CREATE, POBLACIONINDIGENA_UPDATE, POBLACIONINDIGENA_DELETE, POBLACIONINDIGENA_PATCH, POBLACIONINDIGENA_FIND, PoblacionindigenaOutput, PoblacionindigenaDTO> {

    public PoblacionindigenaMap() {
        super(POBLACIONINDIGENA_CREATE.class, POBLACIONINDIGENA_UPDATE.class, POBLACIONINDIGENA_DELETE.class, POBLACIONINDIGENA_PATCH.class, POBLACIONINDIGENA_FIND.class);
    }

    @Override
    protected PoblacionindigenaDTO toDtoCreate(POBLACIONINDIGENA_CREATE input) {
        PoblacionindigenaDTO dto = new PoblacionindigenaDTO();
        dto.setPoblacion(input.poblacion());
        return dto;
    }

    @Override
    protected PoblacionindigenaDTO toDtoUpdate(POBLACIONINDIGENA_UPDATE input) {
        PoblacionindigenaDTO dto = new PoblacionindigenaDTO();
        dto.setId(input.id());
        dto.setPoblacion(input.poblacion());
        return dto;
    }

    @Override
    protected PoblacionindigenaDTO toDtoPatch(POBLACIONINDIGENA_PATCH input) {
        PoblacionindigenaDTO dto = new PoblacionindigenaDTO();
        dto.setId(input.id());
        if (input.poblacion() != null) dto.setPoblacion(input.poblacion());
        return dto;
    }

    @Override
    protected PoblacionindigenaDTO toDtoDelete(POBLACIONINDIGENA_DELETE input) {
        PoblacionindigenaDTO dto = new PoblacionindigenaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected PoblacionindigenaDTO toDtoFind(POBLACIONINDIGENA_FIND input) {
        PoblacionindigenaDTO dto = new PoblacionindigenaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public PoblacionindigenaOutput toOutput(PoblacionindigenaDTO dto) {
        if (dto == null) return null;
        return PoblacionindigenaOutput.builder()
                .id(dto.getId())
                .nombre(dto.getPoblacion())
                .build();
    }

    public List<PoblacionindigenaOutput> toOutputList(List<PoblacionindigenaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
