package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.PagoconceptoInput.*;
import ufps.edu.co.records.output.entity.PagoconceptoOutput;
import ufps.edu.co.rest.dto.PagoconceptoDTO;

@Component
public class PagoconceptoMap extends
        GlobalMapper<PAGOCONCEPTO_CREATE, PAGOCONCEPTO_UPDATE, PAGOCONCEPTO_DELETE, PAGOCONCEPTO_PATCH, PAGOCONCEPTO_FIND, PagoconceptoOutput, PagoconceptoDTO> {

    public PagoconceptoMap() {
        super(PAGOCONCEPTO_CREATE.class, PAGOCONCEPTO_UPDATE.class, PAGOCONCEPTO_DELETE.class, PAGOCONCEPTO_PATCH.class,
                PAGOCONCEPTO_FIND.class);
    }

    @Override
    protected PagoconceptoDTO toDtoCreate(PAGOCONCEPTO_CREATE input) {
        return PagoconceptoDTO.builder()
                .tipo(input.tipo())
                .build();
    }

    @Override
    protected PagoconceptoDTO toDtoUpdate(PAGOCONCEPTO_UPDATE input) {
        return PagoconceptoDTO.builder()
                .id(input.id())
                .tipo(input.tipo())
                .build();
    }

    @Override
    protected PagoconceptoDTO toDtoDelete(PAGOCONCEPTO_DELETE input) {
        return PagoconceptoDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected PagoconceptoDTO toDtoPatch(PAGOCONCEPTO_PATCH input) {
        return PagoconceptoDTO.builder()
                .id(input.id())
                .tipo(input.tipo())
                .build();
    }

    @Override
    protected PagoconceptoDTO toDtoFind(PAGOCONCEPTO_FIND input) {
        return PagoconceptoDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    public PagoconceptoOutput toOutput(PagoconceptoDTO dto) {
        if (dto != null) {
            return PagoconceptoOutput.builder()
                    .id(dto.getId())
                    .tipo(dto.getTipo())
                    .build();
        }
        return null;
    }

    public List<PagoconceptoOutput> toOutputList(List<PagoconceptoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
