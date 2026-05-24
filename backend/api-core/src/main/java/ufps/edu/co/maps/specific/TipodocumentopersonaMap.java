package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.TipodocumentopersonaInput.*;
import ufps.edu.co.records.output.entity.TipodocumentopersonaOutput;
import ufps.edu.co.rest.dto.TipodocumentopersonaDTO;

@Component
@UniversalMapping(
    create = TIPODOCUMENTOPERSONA_CREATE.class,
    update = TIPODOCUMENTOPERSONA_UPDATE.class,
    delete = TIPODOCUMENTOPERSONA_DELETE.class,
    patch = TIPODOCUMENTOPERSONA_PATCH.class,
    find = TIPODOCUMENTOPERSONA_FIND.class
)
public class TipodocumentopersonaMap extends UniversalMapper<TipodocumentopersonaOutput, TipodocumentopersonaDTO> {

    @Override
    public TipodocumentopersonaOutput toOutput(TipodocumentopersonaDTO dto) {
        if (dto != null) {
            return TipodocumentopersonaOutput.builder()
                    .id(dto.getId())
                    .tipo(dto.getTipo())
                    .build();
        }
        return null;
    }

}
