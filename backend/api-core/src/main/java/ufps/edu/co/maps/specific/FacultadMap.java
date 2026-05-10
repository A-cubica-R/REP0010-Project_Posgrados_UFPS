package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.FacultadInput.*;
import ufps.edu.co.records.output.entity.FacultadOutput;
import ufps.edu.co.rest.dto.FacultadDTO;

@Component
public class FacultadMap extends GlobalMapper<FACULTAD_CREATE, FACULTAD_UPDATE, FACULTAD_DELETE, FACULTAD_PATCH, FACULTAD_FIND, FacultadOutput, FacultadDTO> {

    public FacultadMap() {
        super(FACULTAD_CREATE.class, FACULTAD_UPDATE.class, FACULTAD_DELETE.class, FACULTAD_PATCH.class, FACULTAD_FIND.class);
    }

    @Override
    protected FacultadDTO toDtoCreate(FACULTAD_CREATE input) {
        FacultadDTO dto = FacultadDTO.builder()
                .nombre(input.nombre())
                .correo(input.correo())
                .build();
        return dto;
    }

    @Override
    protected FacultadDTO toDtoUpdate(FACULTAD_UPDATE input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        dto.setCorreo(input.correo());
        return dto;
    }

    @Override
    protected FacultadDTO toDtoDelete(FACULTAD_DELETE input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected FacultadDTO toDtoPatch(FACULTAD_PATCH input) {
        FacultadDTO dto = FacultadDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .correo(input.correo())
                .build();
        return dto;
    }

    @Override
    protected FacultadDTO toDtoFind(FACULTAD_FIND input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public FacultadOutput toOutput(FacultadDTO dto) {
        if (dto == null) return null;
        return FacultadOutput.builder()
            .id(dto.getId())
            .nombre(dto.getNombre())
            .correo(dto.getCorreo())
            .cargoList(
                dto.getCargoList() != null ? (
                    !dto.getCargoList().isEmpty() ? (
                        new CargoMap().toOutputList(dto.getCargoList())
                    ) : null
                ) : null
            )
            .build();
    }

    public List<FacultadOutput> toOutputList(List<FacultadDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
