package ufps.edu.co.maps;

import org.springframework.stereotype.Component;
import ufps.edu.co.records.input.JornadaInput;
import ufps.edu.co.records.output.JornadaOutput;
import ufps.edu.co.rest.dto.JornadaDTO;
import java.util.List;

@Component
public class JornadaMap {

    public JornadaDTO toDto(JornadaInput.CREATE create) {
        JornadaDTO dto = new JornadaDTO();
        dto.setTipo(create.tipo());
        return dto;
    }

    public JornadaDTO toDto(JornadaInput.UPDATE input) {
        JornadaDTO dto = new JornadaDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    public JornadaDTO toDto(JornadaInput.PATCH input) {
        JornadaDTO dto = new JornadaDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    public JornadaDTO toDto(JornadaInput.DELETE input) {
        JornadaDTO dto = new JornadaDTO();
        dto.setId(input.id());
        return dto;
    }

    public JornadaDTO toDto(JornadaInput.FIND input) {
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
