package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.ModalidadInput.*;
import ufps.edu.co.records.output.entity.ModalidadOutput;
import ufps.edu.co.rest.dto.ModalidadDTO;

@Component
@UniversalMapping(create = MODALIDAD_CREATE.class, update = MODALIDAD_UPDATE.class, delete = MODALIDAD_DELETE.class, patch = MODALIDAD_PATCH.class, find = MODALIDAD_FIND.class)
public class ModalidadMap extends UniversalMapper<ModalidadOutput, ModalidadDTO> {

    public ModalidadDTO toDtoCreate(MODALIDAD_CREATE create) {
        ModalidadDTO dto = ModalidadDTO.builder()
                .nombre(create.nombre())
                .build();
        return dto;
    }

    public ModalidadDTO toDtoUpdate(MODALIDAD_UPDATE input) {
        ModalidadDTO dto = ModalidadDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .build();
        return dto;
    }

    public ModalidadDTO toDtoPatch(MODALIDAD_PATCH input) {
        ModalidadDTO dto = ModalidadDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .build();
        return dto;
    }

    public ModalidadDTO toDtoDelete(MODALIDAD_DELETE input) {
        ModalidadDTO dto = ModalidadDTO.builder()
                .id(input.id())
                .build();
        return dto;
    }

    public ModalidadDTO toDtoFind(MODALIDAD_FIND input) {
        ModalidadDTO dto = ModalidadDTO.builder()
                .id(input.id())
                .build();
        return dto;
    }

    @Override
    public ModalidadOutput toOutput(ModalidadDTO dto) {
        if (dto != null) {
            // Se declara aquí para evitar la inyección circular entre ModalidadMap y
            // CohorteMap
            CohorteMap cohorteMap = new CohorteMap();
            return ModalidadOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .cohorteList(
                    dto.getCohorteList() != null ? (dto.getCohorteList().stream()
                        .map(
                            cohorteDto -> {
                                cohorteDto.setModalidad(null);
                                return cohorteMap.toOutput(cohorteDto);
                            }).toList()
                        ) : null)
                .build();
        }
        return null;
    }

    public List<ModalidadOutput> toOutputList(List<ModalidadDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}