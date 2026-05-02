package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.ModalidadInput.*;
import ufps.edu.co.records.output.entity.ModalidadOutput;
import ufps.edu.co.rest.dto.ModalidadDTO;

import java.util.List;

@Component
public class ModalidadMap extends
        GlobalMapper<MODALIDAD_CREATE, MODALIDAD_UPDATE, MODALIDAD_DELETE, MODALIDAD_PATCH, MODALIDAD_FIND, ModalidadOutput, ModalidadDTO> {

    public ModalidadMap() {
        super(MODALIDAD_CREATE.class, MODALIDAD_UPDATE.class, MODALIDAD_DELETE.class, MODALIDAD_PATCH.class,
                MODALIDAD_FIND.class);
    }

    public ModalidadDTO toDtoCreate(MODALIDAD_CREATE create) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setNombre(create.nombre());
        return dto;
    }

    public ModalidadDTO toDtoUpdate(MODALIDAD_UPDATE input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        return dto;
    }

    public ModalidadDTO toDtoPatch(MODALIDAD_PATCH input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        return dto;
    }

    public ModalidadDTO toDtoDelete(MODALIDAD_DELETE input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        return dto;
    }

    public ModalidadDTO toDtoFind(MODALIDAD_FIND input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        return dto;
    }

    public ModalidadOutput toOutput(ModalidadDTO dto) {
        if (dto == null)
            return null;
        return new ModalidadOutput(dto.getId(), dto.getNombre());
    }

    public List<ModalidadOutput> toOutputList(List<ModalidadDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
