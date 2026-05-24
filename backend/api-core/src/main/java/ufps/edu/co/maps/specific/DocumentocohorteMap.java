package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.DocumentocohorteInput.*;
import ufps.edu.co.records.output.entity.DocumentocohorteOutput;
import ufps.edu.co.rest.dto.DocumentocohorteDTO;

@Component
@UniversalMapping(
    create = DOCUMENTOCOHORTE_CREATE.class, 
    update = DOCUMENTOCOHORTE_UPDATE.class, 
    delete = DOCUMENTOCOHORTE_DELETE.class, 
    patch = DOCUMENTOCOHORTE_PATCH.class, 
    find = DOCUMENTOCOHORTE_FIND.class)
public class DocumentocohorteMap extends UniversalMapper<DocumentocohorteOutput, DocumentocohorteDTO> {

    @Override
    public DocumentocohorteOutput toOutput(DocumentocohorteDTO dto) {
        return DocumentocohorteOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .obligatorio((dto.getObligatorio()))
                .idCohorte(dto.getIdCohorte())
                .build();
    }
}
