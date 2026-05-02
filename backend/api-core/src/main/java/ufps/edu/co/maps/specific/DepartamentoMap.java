package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.DepartamentoInput.*;
import ufps.edu.co.records.output.entity.DepartamentoOutput;
import ufps.edu.co.rest.dto.DepartamentoDTO;

import java.util.List;

@Component
public class DepartamentoMap extends GlobalMapper<DEPARTAMENTO_CREATE, DEPARTAMENTO_UPDATE, DEPARTAMENTO_DELETE, DEPARTAMENTO_PATCH, DEPARTAMENTO_FIND, DepartamentoOutput, DepartamentoDTO> {

    public DepartamentoMap() {
        super(DEPARTAMENTO_CREATE.class, DEPARTAMENTO_UPDATE.class, DEPARTAMENTO_DELETE.class, DEPARTAMENTO_PATCH.class, DEPARTAMENTO_FIND.class);
    }

    @Override
    protected DepartamentoDTO toDtoCreate(DEPARTAMENTO_CREATE input) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setNombre(input.nombre());
        dto.setIdPais(input.idPais());
        return dto;
    }

    @Override
    protected DepartamentoDTO toDtoUpdate(DEPARTAMENTO_UPDATE input) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        dto.setIdPais(input.idPais());
        return dto;
    }

    @Override
    protected DepartamentoDTO toDtoDelete(DEPARTAMENTO_DELETE input) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected DepartamentoDTO toDtoPatch(DEPARTAMENTO_PATCH input) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(input.id());
        // Solo se actualizan los campos que no son null
        if (input.nombre() != null) {
            dto.setNombre(input.nombre());
        }
        if (input.idPais() != null) {
            dto.setIdPais(input.idPais());
        }
        return dto;
    }

    @Override
    protected DepartamentoDTO toDtoFind(DEPARTAMENTO_FIND input) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public DepartamentoOutput toOutput(DepartamentoDTO dto) {
        return new DepartamentoOutput(dto.getId(), dto.getNombre());
    }

    public List<DepartamentoOutput> toOutputList(List<DepartamentoDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }
}
