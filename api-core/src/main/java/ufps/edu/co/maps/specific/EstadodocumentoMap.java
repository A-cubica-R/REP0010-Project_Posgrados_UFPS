package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.EstadodocumentoInput.*;
import ufps.edu.co.records.output.entity.EstadodocumentoOutput;
import ufps.edu.co.rest.dto.EstadodocumentoDTO;

@Component
public class EstadodocumentoMap extends
        GlobalMapper<ESTADODOCUMENTO_CREATE, ESTADODOCUMENTO_UPDATE, ESTADODOCUMENTO_DELETE, ESTADODOCUMENTO_PATCH, ESTADODOCUMENTO_FIND, EstadodocumentoOutput, EstadodocumentoDTO> {

    public EstadodocumentoMap() {
        super(ESTADODOCUMENTO_CREATE.class, ESTADODOCUMENTO_UPDATE.class, ESTADODOCUMENTO_DELETE.class, ESTADODOCUMENTO_PATCH.class,
                ESTADODOCUMENTO_FIND.class);
    }

    @Override
    protected EstadodocumentoDTO toDtoCreate(ESTADODOCUMENTO_CREATE input) {
        EstadodocumentoDTO dto = new EstadodocumentoDTO();
        dto.setEstado(input.estado());
        return dto;
    }

    @Override
    protected EstadodocumentoDTO toDtoUpdate(ESTADODOCUMENTO_UPDATE input) {
        EstadodocumentoDTO dto = new EstadodocumentoDTO();
        dto.setId(input.id());
        dto.setEstado(input.estado());
        return dto;
    }

    @Override
    protected EstadodocumentoDTO toDtoDelete(ESTADODOCUMENTO_DELETE input) {
        EstadodocumentoDTO dto = new EstadodocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected EstadodocumentoDTO toDtoPatch(ESTADODOCUMENTO_PATCH input) {
        EstadodocumentoDTO.EstadodocumentoDTOBuilder builder = EstadodocumentoDTO.builder()
                .id(input.id());

        if (input.estado() != null) {
            builder.estado(input.estado());
        }

        return builder.build();
    }

    @Override
    protected EstadodocumentoDTO toDtoFind(ESTADODOCUMENTO_FIND input) {
        EstadodocumentoDTO dto = new EstadodocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EstadodocumentoOutput toOutput(EstadodocumentoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new EstadodocumentoOutput(dto.getId(), dto.getEstado());
    }

    public List<EstadodocumentoOutput> toOutputList(List<EstadodocumentoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
