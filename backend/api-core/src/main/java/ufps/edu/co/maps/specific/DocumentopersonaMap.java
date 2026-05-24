package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.DocumentopersonaInput.*;
import ufps.edu.co.records.output.entity.DocumentopersonaOutput;
import ufps.edu.co.rest.dto.DocumentopersonaDTO;

@Component
@UniversalMapping(
    create = DOCUMENTOPERSONA_CREATE.class,
    update = DOCUMENTOPERSONA_UPDATE.class,
    delete = DOCUMENTOPERSONA_DELETE.class,
    patch = DOCUMENTOPERSONA_PATCH.class,
    find = DOCUMENTOPERSONA_FIND.class)
public class DocumentopersonaMap extends UniversalMapper<DocumentopersonaOutput, DocumentopersonaDTO> {

    @Override
    public DocumentopersonaOutput toOutput(DocumentopersonaDTO dto) {
        return DocumentopersonaOutput.builder()
                .id(dto.getId())
                .numerodocumento(dto.getNumerodocumento())
                .idTipodocumento(dto.getIdTipodocumento())
                .idLugarexpedicion(dto.getIdLugarexpedicion())
                .build();
    }
}
