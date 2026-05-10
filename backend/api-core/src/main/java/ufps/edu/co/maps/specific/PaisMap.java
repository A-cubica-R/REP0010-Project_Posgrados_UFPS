package ufps.edu.co.maps.specific;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.PaisInput.*;
import ufps.edu.co.records.output.entity.PaisOutput;
import ufps.edu.co.rest.dto.PaisDTO;

import java.util.List;

@Component
public class PaisMap
        extends GlobalMapper<PAIS_CREATE, PAIS_UPDATE, PAIS_DELETE, PAIS_PATCH, PAIS_FIND, PaisOutput, PaisDTO> {

    public PaisMap() {
        super(PAIS_CREATE.class, PAIS_UPDATE.class, PAIS_DELETE.class, PAIS_PATCH.class, PAIS_FIND.class);
    }

    @Autowired
    DepartamentoMap departamentoMap;

    @Override
    protected PaisDTO toDtoCreate(PAIS_CREATE input) {
        PaisDTO dto = new PaisDTO();
        dto.setNombre(input.nombre());
        return dto;
    }

    @Override
    protected PaisDTO toDtoUpdate(PAIS_UPDATE input) {
        PaisDTO dto = new PaisDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        return dto;
    }

    @Override
    protected PaisDTO toDtoDelete(PAIS_DELETE input) {
        PaisDTO dto = new PaisDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected PaisDTO toDtoPatch(PAIS_PATCH input) {
        PaisDTO dto = new PaisDTO();
        dto.setId(input.id());
        // El nombre solo se actualiza si viene en el PATCH
        if (input.nombre() != null) {
            dto.setNombre(input.nombre());
        }
        return dto;
    }

    @Override
    protected PaisDTO toDtoFind(PAIS_FIND input) {
        PaisDTO dto = new PaisDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public PaisOutput toOutput(PaisDTO dto) {
        return PaisOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .departamentoList(
                    dto.getDepartamentoList() != null ? (
                        !dto.getDepartamentoList().isEmpty() ? (
                            dto.getDepartamentoList().stream().peek(
                                departamento -> {
                                    departamento.setPais(null);
                                })
                                .map(departamentoMap::toOutput)
                                .toList()
                        ) : null
                    ) : null
                )
                .build();
    }

    public List<PaisOutput> toOutputList(List<PaisDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }
}
