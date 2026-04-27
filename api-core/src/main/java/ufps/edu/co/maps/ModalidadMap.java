package ufps.edu.co.maps;

import org.springframework.stereotype.Component;
import ufps.edu.co.records.input.ModalidadInput.*;
import ufps.edu.co.records.output.ModalidadOutput;
import ufps.edu.co.rest.dto.ModalidadDTO;

import java.util.List;

@Component
public class ModalidadMap {

    public ModalidadDTO toDtoCreate(CREATE create) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setNombre(create.nombre());
        return dto;
    }

    public ModalidadDTO toDto(UPDATE input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        return dto;
    }

    public ModalidadDTO toDto(PATCH input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        return dto;
    }

    public ModalidadDTO toDto(DELETE input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        return dto;
    }

    public ModalidadDTO toDto(FIND input) {
        ModalidadDTO dto = new ModalidadDTO();
        dto.setId(input.id());
        return dto;
    }

    public ModalidadOutput toOutput(ModalidadDTO dto) {
        if (dto == null) return null;
        return new ModalidadOutput(dto.getId(), dto.getNombre());
    }

    public List<ModalidadOutput> toOutputList(List<ModalidadDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
