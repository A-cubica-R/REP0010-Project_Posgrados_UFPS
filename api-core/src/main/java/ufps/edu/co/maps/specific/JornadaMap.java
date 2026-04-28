package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.JornadaInput.*;
import ufps.edu.co.records.output.JornadaOutput;
import ufps.edu.co.rest.dto.JornadaDTO;
import java.util.List;

@Component
public class JornadaMap extends GlobalMapper<JORNADA_CREATE, JORNADA_UPDATE, JORNADA_DELETE, JORNADA_PATCH, JORNADA_FIND, JornadaOutput, JornadaDTO> {

    public JornadaMap() {
        super(JORNADA_CREATE.class, JORNADA_UPDATE.class, JORNADA_DELETE.class, JORNADA_PATCH.class,
                JORNADA_FIND.class);
    }

    public JornadaDTO toDtoCreate(JORNADA_CREATE create) {
        JornadaDTO dto = new JornadaDTO();
        dto.setTipo(create.tipo());
        return dto;
    }

    public JornadaDTO toDtoUpdate(JORNADA_UPDATE input) {
        JornadaDTO dto = new JornadaDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    public JornadaDTO toDtoPatch(JORNADA_PATCH input) {
        JornadaDTO dto = new JornadaDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    public JornadaDTO toDtoDelete(JORNADA_DELETE input) {
        JornadaDTO dto = new JornadaDTO();
        dto.setId(input.id());
        return dto;
    }

    public JornadaDTO toDtoFind(JORNADA_FIND input) {
        JornadaDTO dto = new JornadaDTO();
        dto.setId(input.id());
        return dto;
    }

    public JornadaOutput toOutput(JornadaDTO dto) {
        if (dto == null) return null;
        return new JornadaOutput(dto.getId(), dto.getTipo());
    }

    public List<JornadaOutput> toOutputList(List<JornadaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
