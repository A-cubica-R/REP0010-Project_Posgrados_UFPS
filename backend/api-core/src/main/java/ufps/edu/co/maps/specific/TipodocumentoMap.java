package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.TipodocumentoInput.*;
import ufps.edu.co.records.output.entity.TipodocumentoOutput;
import ufps.edu.co.rest.dto.TipodocumentoDTO;

@Component
public class TipodocumentoMap extends
        GlobalMapper<TIPODOCUMENTO_CREATE, TIPODOCUMENTO_UPDATE, TIPODOCUMENTO_DELETE, TIPODOCUMENTO_PATCH, TIPODOCUMENTO_FIND, TipodocumentoOutput, TipodocumentoDTO> {

    public TipodocumentoMap() {
        super(TIPODOCUMENTO_CREATE.class, TIPODOCUMENTO_UPDATE.class, TIPODOCUMENTO_DELETE.class,
                TIPODOCUMENTO_PATCH.class,
                TIPODOCUMENTO_FIND.class);
    }

    @Override
    protected TipodocumentoDTO toDtoCreate(TIPODOCUMENTO_CREATE input) {
        TipodocumentoDTO dto = new TipodocumentoDTO();
        dto.setTipo(input.nombre());
        dto.setDescripcion(input.descripcion());
        dto.setExtension(input.extension());
        dto.setTamanomaximo(input.tamanomaximo());
        return dto;
    }

    @Override
    protected TipodocumentoDTO toDtoUpdate(TIPODOCUMENTO_UPDATE input) {
        TipodocumentoDTO dto = new TipodocumentoDTO();
        dto.setId(input.id());
        dto.setTipo(input.nombre());
        dto.setDescripcion(input.descripcion());
        dto.setExtension(input.extension());
        dto.setTamanomaximo(input.tamanomaximo());
        return dto;
    }

    @Override
    protected TipodocumentoDTO toDtoDelete(TIPODOCUMENTO_DELETE input) {
        TipodocumentoDTO dto = new TipodocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected TipodocumentoDTO toDtoPatch(TIPODOCUMENTO_PATCH input) {
        TipodocumentoDTO.TipodocumentoDTOBuilder builder = TipodocumentoDTO.builder()
                .id(input.id());

        if (input.nombre() != null) {
            builder.tipo(input.nombre());
        }
        if (input.descripcion() != null) {
            builder.descripcion(input.descripcion());
        }
        if (input.extension() != null) {
            builder.extension(input.extension());
        }
        if (input.tamanomaximo() != null) {
            builder.tamanomaximo(input.tamanomaximo());
        }

        return builder.build();
    }

    @Override
    protected TipodocumentoDTO toDtoFind(TIPODOCUMENTO_FIND input) {
        TipodocumentoDTO dto = new TipodocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public TipodocumentoOutput toOutput(TipodocumentoDTO dto) {
        if (dto == null) {
            return null;
        }
        return TipodocumentoOutput.builder()
                .id(dto.getId())
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .extension(dto.getExtension())
                .tamanomaximo(dto.getTamanomaximo())
                .build();
    }

    public List<TipodocumentoOutput> toOutputList(List<TipodocumentoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
