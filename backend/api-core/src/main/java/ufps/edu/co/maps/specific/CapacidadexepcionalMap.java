package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.CapacidadexepcionalInput.*;
import ufps.edu.co.records.output.entity.CapacidadexepcionalOutput;
import ufps.edu.co.rest.dto.CapacidadexepcionalDTO;

@Component
public class CapacidadexepcionalMap extends GlobalMapper<CAPACIDADEXEPCIONAL_CREATE, CAPACIDADEXEPCIONAL_UPDATE, CAPACIDADEXEPCIONAL_DELETE, CAPACIDADEXEPCIONAL_PATCH, CAPACIDADEXEPCIONAL_FIND, CapacidadexepcionalOutput, CapacidadexepcionalDTO> {

    public CapacidadexepcionalMap() {
        super(CAPACIDADEXEPCIONAL_CREATE.class, CAPACIDADEXEPCIONAL_UPDATE.class, CAPACIDADEXEPCIONAL_DELETE.class, CAPACIDADEXEPCIONAL_PATCH.class, CAPACIDADEXEPCIONAL_FIND.class);
    }

    @Override
    protected CapacidadexepcionalDTO toDtoCreate(CAPACIDADEXEPCIONAL_CREATE input) {
        CapacidadexepcionalDTO dto = new CapacidadexepcionalDTO();
        dto.setTipocapacidad(input.tipocapacidad());
        return dto;
    }

    @Override
    protected CapacidadexepcionalDTO toDtoUpdate(CAPACIDADEXEPCIONAL_UPDATE input) {
        CapacidadexepcionalDTO dto = new CapacidadexepcionalDTO();
        dto.setId(input.id());
        dto.setTipocapacidad(input.tipocapacidad());
        return dto;
    }

    @Override
    protected CapacidadexepcionalDTO toDtoPatch(CAPACIDADEXEPCIONAL_PATCH input) {
        CapacidadexepcionalDTO dto = new CapacidadexepcionalDTO();
        dto.setId(input.id());
        if (input.tipocapacidad() != null) dto.setTipocapacidad(input.tipocapacidad());
        return dto;
    }

    @Override
    protected CapacidadexepcionalDTO toDtoDelete(CAPACIDADEXEPCIONAL_DELETE input) {
        CapacidadexepcionalDTO dto = new CapacidadexepcionalDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected CapacidadexepcionalDTO toDtoFind(CAPACIDADEXEPCIONAL_FIND input) {
        CapacidadexepcionalDTO dto = new CapacidadexepcionalDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public CapacidadexepcionalOutput toOutput(CapacidadexepcionalDTO dto) {
        if (dto == null) return null;
        return CapacidadexepcionalOutput.builder()
                .id(dto.getId())
                .nombre(dto.getTipocapacidad())
                .build();
    }

    public List<CapacidadexepcionalOutput> toOutputList(List<CapacidadexepcionalDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
