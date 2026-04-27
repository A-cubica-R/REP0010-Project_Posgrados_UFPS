package ufps.edu.co.maps;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.records.input.EstadoInput;
import ufps.edu.co.records.output.EstadoOutput;
import ufps.edu.co.rest.dto.EstadoDTO;

@Component
public class EstadoMap {

    public EstadoDTO toDto(EstadoInput.CREATE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setTipo(input.tipo());
        return dto;
    }

    public EstadoDTO toDto(EstadoInput.UPDATE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    public EstadoDTO toDto(EstadoInput.PATCH input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    public EstadoDTO toDto(EstadoInput.DELETE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        return dto;
    }

    public EstadoDTO toDto(EstadoInput.FIND input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        return dto;
    }

    public EstadoOutput toOutput(EstadoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new EstadoOutput(dto.getId(), dto.getTipo());
    }

    public List<EstadoOutput> toOutputList(List<EstadoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
