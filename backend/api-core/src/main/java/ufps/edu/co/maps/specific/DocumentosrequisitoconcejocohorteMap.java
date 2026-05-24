package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.DocumentosrequisitoconcejocohorteInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoconcejocohorteOutput;
import ufps.edu.co.rest.dto.DocumentosrequisitoconcejocohorteDTO;

@Component
@UniversalMapping(
    create = DOCUMENTOSREQUISITOCONCEJOCOHORTE_CREATE.class,
    update = DOCUMENTOSREQUISITOCONCEJOCOHORTE_UPDATE.class,
    delete = DOCUMENTOSREQUISITOCONCEJOCOHORTE_DELETE.class,
    patch = DOCUMENTOSREQUISITOCONCEJOCOHORTE_PATCH.class,
    find = DOCUMENTOSREQUISITOCONCEJOCOHORTE_FIND.class)
public class DocumentosrequisitoconcejocohorteMap extends UniversalMapper<DocumentosrequisitoconcejocohorteOutput, DocumentosrequisitoconcejocohorteDTO> {

    @Override
    public DocumentosrequisitoconcejocohorteOutput toOutput(DocumentosrequisitoconcejocohorteDTO dto) {
        return DocumentosrequisitoconcejocohorteOutput.builder()
                .id(dto.getId())
                .idDocrequisito(dto.getIdDocrequisito())
                .idCohorte(dto.getIdCohorte())
                .build();
    }
}
