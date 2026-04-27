package ufps.edu.co.maps;

import org.springframework.stereotype.Component;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.input.PaisInput;
import ufps.edu.co.records.input.PaisInput.CREATE;
import ufps.edu.co.records.output.PaisOutput;
import ufps.edu.co.rest.dto.PaisDTO;

import java.util.List;

@Component
public class PaisMap {

    public PaisDTO toDto(CREATE create) {
        PaisDTO dto = new PaisDTO();
        dto.setNombre(create.nombre());
        return dto;
    }

    public PaisDTO toDto(PaisInput.UPDATE input, Integer id) {
        PaisDTO dto = new PaisDTO();
        dto.setId(id);
        dto.setNombre(input.nombre());
        return dto;
    }

    public PaisDTO toPatchDto(PaisInput.PATCH input, Integer id, PaisDTO currentDto) {
        // Preserve current values and apply only fields provided in PATCH.
        PaisDTO patched = new PaisDTO();
        patched.setId(currentDto.getId());
        patched.setNombre(currentDto.getNombre());
        patched.setDepartamentoList(currentDto.getDepartamentoList());
        patched.setId(id);

        if (input.nombre() != null) {
            patched.setNombre(input.nombre());
        }

        return patched;
    }

    public PaisDTO toDto(InputRequest input, Integer id) {
        if (input instanceof PaisInput.CREATE create) {
            return toDto(create);
        }
        if (input instanceof PaisInput.UPDATE update) {
            return toDto(update, id);
        }
        if (input instanceof PaisInput.PATCH patch) {
            return toPatchDto(patch, id, new PaisDTO());
        }
        throw new IllegalArgumentException(
                "Unsupported input type for PaisDTO mapping: " + input.getClass().getName());
    }

    public PaisOutput toOutput(PaisDTO dto) {
        return new PaisOutput(dto.getId(), dto.getNombre());
    }

    public List<PaisOutput> toOutputList(List<PaisDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }
}
