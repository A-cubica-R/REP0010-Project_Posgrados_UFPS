package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.TipoentrevistaInput.*;
import ufps.edu.co.records.output.entity.TipoentrevistaOutput;
import ufps.edu.co.rest.dto.TipoentrevistaDTO;

@Component
public class TipoentrevistaMap extends
        GlobalMapper<TIPOENTREVISTA_CREATE, TIPOENTREVISTA_UPDATE, TIPOENTREVISTA_DELETE, TIPOENTREVISTA_PATCH, TIPOENTREVISTA_FIND, TipoentrevistaOutput, TipoentrevistaDTO> {

    public TipoentrevistaMap() {
        super(TIPOENTREVISTA_CREATE.class, TIPOENTREVISTA_UPDATE.class, TIPOENTREVISTA_DELETE.class, TIPOENTREVISTA_PATCH.class,
                TIPOENTREVISTA_FIND.class);
    }

    @Override
    protected TipoentrevistaDTO toDtoCreate(TIPOENTREVISTA_CREATE input) {
        TipoentrevistaDTO dto = new TipoentrevistaDTO();
        dto.setTipo(input.nombre());
        dto.setDescripcion(input.descripcion());
        return dto;
    }

    @Override
    protected TipoentrevistaDTO toDtoUpdate(TIPOENTREVISTA_UPDATE input) {
        TipoentrevistaDTO dto = new TipoentrevistaDTO();
        dto.setId(input.id());
        dto.setTipo(input.nombre());
        dto.setDescripcion(input.descripcion());
        return dto;
    }

    @Override
    protected TipoentrevistaDTO toDtoDelete(TIPOENTREVISTA_DELETE input) {
        TipoentrevistaDTO dto = new TipoentrevistaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected TipoentrevistaDTO toDtoPatch(TIPOENTREVISTA_PATCH input) {
        TipoentrevistaDTO.TipoentrevistaDTOBuilder builder = TipoentrevistaDTO.builder()
                .id(input.id());

        if (input.nombre() != null) {
            builder.tipo(input.nombre());
        }
        if (input.descripcion() != null) {
            builder.descripcion(input.descripcion());
        }

        return builder.build();
    }

    @Override
    protected TipoentrevistaDTO toDtoFind(TIPOENTREVISTA_FIND input) {
        TipoentrevistaDTO dto = new TipoentrevistaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public TipoentrevistaOutput toOutput(TipoentrevistaDTO dto) {
        if (dto == null) {
            return null;
        }
        return TipoentrevistaOutput.builder()
                .id(dto.getId())
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .build();
    }

    public List<TipoentrevistaOutput> toOutputList(List<TipoentrevistaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
