package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.TipopruebaInput.*;
import ufps.edu.co.records.output.entity.TipopruebaOutput;
import ufps.edu.co.rest.dto.TipopruebaDTO;

@Component
public class TipopruebaMap extends
        GlobalMapper<TIPOPRUEBA_CREATE, TIPOPRUEBA_UPDATE, TIPOPRUEBA_DELETE, TIPOPRUEBA_PATCH, TIPOPRUEBA_FIND, TipopruebaOutput, TipopruebaDTO> {

    public TipopruebaMap() {
        super(TIPOPRUEBA_CREATE.class, TIPOPRUEBA_UPDATE.class, TIPOPRUEBA_DELETE.class,
                TIPOPRUEBA_PATCH.class,
                TIPOPRUEBA_FIND.class);
    }

    @Override
    protected TipopruebaDTO toDtoCreate(TIPOPRUEBA_CREATE input) {
        TipopruebaDTO dto = new TipopruebaDTO();
        dto.setTipo(input.tipo());
        dto.setDescripcion(input.descripcion());
        return dto;
    }

    @Override
    protected TipopruebaDTO toDtoUpdate(TIPOPRUEBA_UPDATE input) {
        TipopruebaDTO dto = new TipopruebaDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        dto.setDescripcion(input.descripcion());
        return dto;
    }

    @Override
    protected TipopruebaDTO toDtoDelete(TIPOPRUEBA_DELETE input) {
        TipopruebaDTO dto = new TipopruebaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected TipopruebaDTO toDtoPatch(TIPOPRUEBA_PATCH input) {
        TipopruebaDTO.TipopruebaDTOBuilder builder = TipopruebaDTO.builder()
                .id(input.id());

        if (input.tipo() != null) {
            builder.tipo(input.tipo());
        }
        if (input.descripcion() != null) {
            builder.descripcion(input.descripcion());
        }

        return builder.build();
    }

    @Override
    protected TipopruebaDTO toDtoFind(TIPOPRUEBA_FIND input) {
        TipopruebaDTO dto = new TipopruebaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public TipopruebaOutput toOutput(TipopruebaDTO dto) {
        if (dto == null) {
            return null;
        }
        return TipopruebaOutput.builder()
                .id(dto.getId())
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .build();
    }

    public List<TipopruebaOutput> toOutputList(List<TipopruebaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
