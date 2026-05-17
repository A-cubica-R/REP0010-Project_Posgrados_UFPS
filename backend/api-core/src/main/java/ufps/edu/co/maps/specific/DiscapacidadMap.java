package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.DiscapacidadInput.*;
import ufps.edu.co.records.output.entity.DiscapacidadOutput;
import ufps.edu.co.rest.dto.DiscapacidadDTO;

@Component
public class DiscapacidadMap extends GlobalMapper<DISCAPACIDAD_CREATE, DISCAPACIDAD_UPDATE, DISCAPACIDAD_DELETE, DISCAPACIDAD_PATCH, DISCAPACIDAD_FIND, DiscapacidadOutput, DiscapacidadDTO> {

    public DiscapacidadMap() {
        super(DISCAPACIDAD_CREATE.class, DISCAPACIDAD_UPDATE.class, DISCAPACIDAD_DELETE.class, DISCAPACIDAD_PATCH.class, DISCAPACIDAD_FIND.class);
    }

    @Override
    protected DiscapacidadDTO toDtoCreate(DISCAPACIDAD_CREATE input) {
        DiscapacidadDTO dto = new DiscapacidadDTO();
        dto.setTipodiscapacidad(input.tipodiscapacidad());
        return dto;
    }

    @Override
    protected DiscapacidadDTO toDtoUpdate(DISCAPACIDAD_UPDATE input) {
        DiscapacidadDTO dto = new DiscapacidadDTO();
        dto.setId(input.id());
        dto.setTipodiscapacidad(input.tipodiscapacidad());
        return dto;
    }

    @Override
    protected DiscapacidadDTO toDtoPatch(DISCAPACIDAD_PATCH input) {
        DiscapacidadDTO dto = new DiscapacidadDTO();
        dto.setId(input.id());
        if (input.tipodiscapacidad() != null) dto.setTipodiscapacidad(input.tipodiscapacidad());
        return dto;
    }

    @Override
    protected DiscapacidadDTO toDtoDelete(DISCAPACIDAD_DELETE input) {
        DiscapacidadDTO dto = new DiscapacidadDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected DiscapacidadDTO toDtoFind(DISCAPACIDAD_FIND input) {
        DiscapacidadDTO dto = new DiscapacidadDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public DiscapacidadOutput toOutput(DiscapacidadDTO dto) {
        if (dto == null) return null;
        return DiscapacidadOutput.builder()
                .id(dto.getId())
                .tipodiscapacidad(dto.getTipodiscapacidad())
                .build();
    }

    public List<DiscapacidadOutput> toOutputList(List<DiscapacidadDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
