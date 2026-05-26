package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.DocumentosrequisitoconsejocohorteInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoconsejocohorteOutput;
import ufps.edu.co.rest.dto.DocumentosrequisitoconsejocohorteDTO;

@Component
@UniversalMapping(
    create = DOCUMENTOSREQUISITOCONSEJOCOHORTE_CREATE.class,
    update = DOCUMENTOSREQUISITOCONSEJOCOHORTE_UPDATE.class,
    delete = DOCUMENTOSREQUISITOCONSEJOCOHORTE_DELETE.class,
    patch = DOCUMENTOSREQUISITOCONSEJOCOHORTE_PATCH.class,
    find = DOCUMENTOSREQUISITOCONSEJOCOHORTE_FIND.class)
public class DocumentosrequisitoconsejocohorteMap extends UniversalMapper<DocumentosrequisitoconsejocohorteOutput, DocumentosrequisitoconsejocohorteDTO> {

    @Override
    public DocumentosrequisitoconsejocohorteOutput toOutput(DocumentosrequisitoconsejocohorteDTO dto) {
        return DocumentosrequisitoconsejocohorteOutput.builder()
                .id(dto.getId())
                .idDocrequisito(dto.getIdDocrequisito())
                .idCohorte(dto.getIdCohorte())
                .build();
    }
}
