package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.OtrosvaloresInput.*;
import ufps.edu.co.records.output.entity.OtrosvaloresOutput;
import ufps.edu.co.rest.dto.OtrosvaloresDTO;

@Component
public class OtrosvaloresMap extends
        GlobalMapper<OTROSVALORES_CREATE, OTROSVALORES_UPDATE, OTROSVALORES_DELETE, OTROSVALORES_PATCH, OTROSVALORES_FIND, OtrosvaloresOutput, OtrosvaloresDTO> {

    public OtrosvaloresMap() {
        super(OTROSVALORES_CREATE.class, OTROSVALORES_UPDATE.class,
            OTROSVALORES_DELETE.class, OTROSVALORES_PATCH.class, OTROSVALORES_FIND.class);
        }

    @Override
    public OtrosvaloresDTO toDtoCreate(OTROSVALORES_CREATE create) {
        OtrosvaloresDTO dto = new OtrosvaloresDTO();
        dto.setCarnet(create.carnet());
        dto.setEstampilla(create.estampilla());
        dto.setSeguro(create.seguro());
        return dto;
    }

    @Override
    public OtrosvaloresDTO toDtoUpdate(OTROSVALORES_UPDATE input) {
        OtrosvaloresDTO dto = new OtrosvaloresDTO();
        dto.setId(input.id());
        dto.setCarnet(input.carnet());
        dto.setEstampilla(input.estampilla());
        dto.setSeguro(input.seguro());
        return dto;
    }

    @Override
    public OtrosvaloresDTO toDtoPatch(OTROSVALORES_PATCH input) {
        OtrosvaloresDTO patched = new OtrosvaloresDTO();
        patched.setCarnet(input.carnet());
        patched.setEstampilla(input.estampilla());
        patched.setSeguro(input.seguro());

        return patched;
    }

    @Override
    protected OtrosvaloresDTO toDtoFind(OTROSVALORES_FIND input) {
        OtrosvaloresDTO dto = new OtrosvaloresDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public OtrosvaloresOutput toOutput(OtrosvaloresDTO dto) {
        return OtrosvaloresOutput.builder()
                .id(dto.getId())
                .carnet(dto.getCarnet())
                .estampilla(dto.getEstampilla())
                .seguro(dto.getSeguro())
                .build();
    }

    public List<OtrosvaloresOutput> toOutputList(List<OtrosvaloresDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }

    @Override
    protected OtrosvaloresDTO toDtoDelete(OTROSVALORES_DELETE input) {
        OtrosvaloresDTO dto = new OtrosvaloresDTO();
        dto.setId(input.id());
        return dto;
    }

}
