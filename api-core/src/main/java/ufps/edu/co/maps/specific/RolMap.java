package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.RolInput.*;
import ufps.edu.co.records.output.entity.RolOutput;
import ufps.edu.co.rest.dto.RolDTO;

@Component
public class RolMap extends
        GlobalMapper<ROL_CREATE, ROL_UPDATE, ROL_DELETE, ROL_PATCH, ROL_FIND, RolOutput, RolDTO> {

    public RolMap() {
        super(ROL_CREATE.class, ROL_UPDATE.class, ROL_DELETE.class, ROL_PATCH.class,
                ROL_FIND.class);
    }

    @Override
    protected RolDTO toDtoCreate(ROL_CREATE input) {
        RolDTO dto = new RolDTO();
        dto.setNombre(input.nombre());
        return dto;
    }

    @Override
    protected RolDTO toDtoUpdate(ROL_UPDATE input) {
        RolDTO dto = new RolDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        return dto;
    }

    @Override
    protected RolDTO toDtoDelete(ROL_DELETE input) {
        RolDTO dto = new RolDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected RolDTO toDtoPatch(ROL_PATCH input) {
        RolDTO.RolDTOBuilder builder = RolDTO.builder()
                .id(input.id());

        if (input.nombre() != null) {
            builder.nombre(input.nombre());
        }

        return builder.build();
    }

    @Override
    protected RolDTO toDtoFind(ROL_FIND input) {
        RolDTO dto = new RolDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public RolOutput toOutput(RolDTO dto) {
        if (dto == null) {
            return null;
        }
        return new RolOutput(dto.getId(), dto.getNombre());
    }

    public List<RolOutput> toOutputList(List<RolDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
