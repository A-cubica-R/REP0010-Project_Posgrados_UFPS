package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.EstadocivilInput.*;
import ufps.edu.co.records.output.entity.EstadocivilOutput;
import ufps.edu.co.rest.dto.EstadocivilDTO;

@Component
public class EstadocivilMap extends GlobalMapper<ESTADOCIVIL_CREATE, ESTADOCIVIL_UPDATE, ESTADOCIVIL_DELETE, ESTADOCIVIL_PATCH, ESTADOCIVIL_FIND, EstadocivilOutput, EstadocivilDTO> {

    public EstadocivilMap() {
        super(ESTADOCIVIL_CREATE.class, ESTADOCIVIL_UPDATE.class, ESTADOCIVIL_DELETE.class, ESTADOCIVIL_PATCH.class, ESTADOCIVIL_FIND.class);
    }

    @Override
    protected EstadocivilDTO toDtoCreate(ESTADOCIVIL_CREATE input) {
        EstadocivilDTO dto = new EstadocivilDTO();
        dto.setEstado(input.estado());
        return dto;
    }

    @Override
    protected EstadocivilDTO toDtoUpdate(ESTADOCIVIL_UPDATE input) {
        EstadocivilDTO dto = new EstadocivilDTO();
        dto.setId(input.id());
        dto.setEstado(input.estado());
        return dto;
    }

    @Override
    protected EstadocivilDTO toDtoPatch(ESTADOCIVIL_PATCH input) {
        EstadocivilDTO dto = new EstadocivilDTO();
        dto.setId(input.id());
        if (input.estado() != null) dto.setEstado(input.estado());
        return dto;
    }

    @Override
    protected EstadocivilDTO toDtoDelete(ESTADOCIVIL_DELETE input) {
        EstadocivilDTO dto = new EstadocivilDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected EstadocivilDTO toDtoFind(ESTADOCIVIL_FIND input) {
        EstadocivilDTO dto = new EstadocivilDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EstadocivilOutput toOutput(EstadocivilDTO dto) {
        if (dto == null) return null;
        return EstadocivilOutput.builder()
                .id(dto.getId())
                .tipo(dto.getEstado())
                .build();
    }

    public List<EstadocivilOutput> toOutputList(List<EstadocivilDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
