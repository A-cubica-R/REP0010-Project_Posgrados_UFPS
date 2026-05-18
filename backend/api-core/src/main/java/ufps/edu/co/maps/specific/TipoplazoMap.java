package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.TipoplazoInput.*;
import ufps.edu.co.records.output.entity.TipoplazoOutput;
import ufps.edu.co.rest.dto.TipoplazoDTO;

@Component
public class TipoplazoMap extends
        GlobalMapper<TIPOPLAZO_CREATE, TIPOPLAZO_UPDATE, TIPOPLAZO_DELETE, TIPOPLAZO_PATCH, TIPOPLAZO_FIND, TipoplazoOutput, TipoplazoDTO> {

    public TipoplazoMap() {
        super(TIPOPLAZO_CREATE.class, TIPOPLAZO_UPDATE.class, TIPOPLAZO_DELETE.class, TIPOPLAZO_PATCH.class,
                TIPOPLAZO_FIND.class);
    }

    @Override
    protected TipoplazoDTO toDtoCreate(TIPOPLAZO_CREATE input) {
        TipoplazoDTO dto = new TipoplazoDTO();
        dto.setTipo(input.nombre());
        dto.setDescripcion(input.descripcion());
        return dto;
    }

    @Override
    protected TipoplazoDTO toDtoUpdate(TIPOPLAZO_UPDATE input) {
        TipoplazoDTO dto = new TipoplazoDTO();
        dto.setId(input.id());
        dto.setTipo(input.nombre());
        dto.setDescripcion(input.descripcion());
        return dto;
    }

    @Override
    protected TipoplazoDTO toDtoDelete(TIPOPLAZO_DELETE input) {
        TipoplazoDTO dto = new TipoplazoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected TipoplazoDTO toDtoPatch(TIPOPLAZO_PATCH input) {
        TipoplazoDTO.TipoplazoDTOBuilder builder = TipoplazoDTO.builder()
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
    protected TipoplazoDTO toDtoFind(TIPOPLAZO_FIND input) {
        TipoplazoDTO dto = new TipoplazoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public TipoplazoOutput toOutput(TipoplazoDTO dto) {
        if (dto == null)
            return null;
        return TipoplazoOutput.builder()
                .id(dto.getId())
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .build();
    }

    public List<TipoplazoOutput> toOutputList(List<TipoplazoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
