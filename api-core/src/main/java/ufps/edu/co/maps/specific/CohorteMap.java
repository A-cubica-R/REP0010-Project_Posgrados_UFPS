package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.records.input.CohorteInput.*;
import ufps.edu.co.records.output.CohorteOutput;

@Component
public class CohorteMap extends GlobalMapper<COHORTE_CREATE, COHORTE_UPDATE, COHORTE_DELETE, COHORTE_PATCH, COHORTE_FIND, CohorteOutput, CohorteDTO> {

    public CohorteMap() {
        super(COHORTE_CREATE.class, COHORTE_UPDATE.class, COHORTE_DELETE.class, COHORTE_PATCH.class, COHORTE_FIND.class);
    }

    @Override
    protected CohorteDTO toDtoCreate(COHORTE_CREATE input) {
        CohorteDTO dto = new CohorteDTO();
        dto.setNombre(input.nombre());
        dto.setIdEstado(input.idEstado());
        dto.setFechaInicio(input.fechaInicio());
        dto.setFechaFin(input.fechaFin());
        return dto;
    }

    @Override
    protected CohorteDTO toDtoUpdate(COHORTE_UPDATE input) {
        CohorteDTO dto = new CohorteDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        dto.setIdEstado(input.idEstado());
        dto.setFechaInicio(input.fechaInicio());
        dto.setFechaFin(input.fechaFin());
        return dto;
    }

    @Override
    protected CohorteDTO toDtoDelete(COHORTE_DELETE input) {
        CohorteDTO dto = new CohorteDTO();
        dto.setId(input.id());
        return dto; 
    }

    @Override
    protected CohorteDTO toDtoPatch(COHORTE_PATCH input) {
        CohorteDTO patched = new CohorteDTO();
        patched.setId(input.id());
        patched.setNombre(input.nombre());
        patched.setIdEstado(input.idEstado());
        patched.setFechaInicio(input.fechaInicio());
        patched.setFechaFin(input.fechaFin());

        if (input.nombre() != null) {
            patched.setNombre(input.nombre());
        }
        if (input.idEstado() != null) {
            patched.setIdEstado(input.idEstado());
        }
        if (input.fechaInicio() != null) {
            patched.setFechaInicio(input.fechaInicio());
        }
        if (input.fechaFin() != null) {
            patched.setFechaFin(input.fechaFin());
        }

        return patched;
    }

    @Override
    protected CohorteDTO toDtoFind(COHORTE_FIND input) {
        CohorteDTO dto = new CohorteDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public CohorteOutput toOutput(CohorteDTO dto) {
        return new CohorteOutput(dto.getId(), dto.getNombre(), dto.getIdEstado(), dto.getFechaInicio().toString(), dto.getFechaFin().toString());
    }

    public List<CohorteOutput> toOutputList(List<CohorteDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }
}
