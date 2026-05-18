package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.CargoInput.*;
import ufps.edu.co.records.output.entity.CargoOutput;
import ufps.edu.co.rest.dto.CargoDTO;

@Component
public class CargoMap
        extends GlobalMapper<CARGO_CREATE, CARGO_UPDATE, CARGO_DELETE, CARGO_PATCH, CARGO_FIND, CargoOutput, CargoDTO> {

    public CargoMap() {
        super(CARGO_CREATE.class, CARGO_UPDATE.class, CARGO_DELETE.class, CARGO_PATCH.class, CARGO_FIND.class);
    }

    @Override
    protected CargoDTO toDtoCreate(CARGO_CREATE input) {
        CargoDTO dto = new CargoDTO();
        dto.setNombre(input.nombre());
        dto.setDescripcion(input.descripcion());
        dto.setIdPrograma(input.idPrograma());
        return dto;
    }

    @Override
    protected CargoDTO toDtoUpdate(CARGO_UPDATE input) {
        CargoDTO dto = new CargoDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        dto.setDescripcion(input.descripcion());
        dto.setIdPrograma(input.idPrograma());
        return dto;
    }

    @Override
    protected CargoDTO toDtoDelete(CARGO_DELETE input) {
        CargoDTO dto = new CargoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected CargoDTO toDtoPatch(CARGO_PATCH input) {
        CargoDTO dto = new CargoDTO();
        dto.setId(input.id());
        if (input.nombre() != null)
            dto.setNombre(input.nombre());
        if (input.descripcion() != null)
            dto.setDescripcion(input.descripcion());
        if (input.idPrograma() != null)
            dto.setIdPrograma(input.idPrograma());
        return dto;
    }

    @Override
    protected CargoDTO toDtoFind(CARGO_FIND input) {
        CargoDTO dto = new CargoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public CargoOutput toOutput(CargoDTO dto) {
        if (dto == null)
            return null;

        FacultadMap facultadMap = new FacultadMap();
        ProgramaMap programaMap = new ProgramaMap();

        return CargoOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .facultad(dto.getFacultad() != null ? facultadMap.toOutput(dto.getFacultad()) : null)
                .programa(dto.getPrograma() != null ? programaMap.toOutput(dto.getPrograma()) : null)
                .build();
    }

    public List<CargoOutput> toOutputList(List<CargoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
