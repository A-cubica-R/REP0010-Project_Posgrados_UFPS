package ufps.edu.co.maps;

import org.springframework.stereotype.Component;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.input.DepartamentoInput;
import ufps.edu.co.records.output.DepartamentoOutput;
import ufps.edu.co.rest.dto.DepartamentoDTO;

import java.util.List;

@Component
public class DepartamentoMap {

    public DepartamentoDTO toDto(DepartamentoInput.CREATE input) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setNombre(input.nombre());
        dto.setIdPais(input.idPais());
        return dto;
    }

    public DepartamentoDTO toDto(DepartamentoInput.UPDATE input, Integer id) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(id);
        dto.setNombre(input.nombre());
        dto.setIdPais(input.idPais());
        return dto;
    }

    public DepartamentoDTO toPatchDto(DepartamentoInput.PATCH input, Integer id, DepartamentoDTO currentDto) {
        // Preserve current values and apply only fields provided in PATCH.
        DepartamentoDTO patched = new DepartamentoDTO();
        patched.setId(currentDto.getId());
        patched.setNombre(currentDto.getNombre());
        patched.setIdPais(currentDto.getIdPais());
        patched.setPais(currentDto.getPais());
        patched.setMunicipioList(currentDto.getMunicipioList());
        patched.setId(id);

        if (input.nombre() != null) {
            patched.setNombre(input.nombre());
        }
        if (input.idPais() != null) {
            patched.setIdPais(input.idPais());
        }

        return patched;
    }

    public DepartamentoDTO toDto(InputRequest input, Integer id) {
        if (input instanceof DepartamentoInput.CREATE create) {
            return toDto(create);
        }
        if (input instanceof DepartamentoInput.UPDATE update) {
            return toDto(update, id);
        }

        throw new IllegalArgumentException(
                "Unsupported input type for DepartamentoDTO mapping: " + input.getClass().getName());
    }

    public DepartamentoOutput toOutput(DepartamentoDTO dto) {
        return new DepartamentoOutput(dto.getId(), dto.getNombre(), dto.getIdPais());
    }

    public List<DepartamentoOutput> toOutputList(List<DepartamentoDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }
}
