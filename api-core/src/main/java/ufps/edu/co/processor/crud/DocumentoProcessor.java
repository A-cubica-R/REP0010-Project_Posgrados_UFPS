package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.DocumentoMap;
import ufps.edu.co.records.input.entity.DocumentoInput.*;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.rest.dto.DocumentoDTO;
import ufps.edu.co.rest.services.DocumentoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class DocumentoProcessor implements
        GlobalUseCase<DOCUMENTO_CREATE, DOCUMENTO_UPDATE, DOCUMENTO_DELETE, DOCUMENTO_PATCH, DOCUMENTO_FIND, DocumentoOutput> {

    @Autowired
    private DocumentoService service;

    @Autowired
    private DocumentoMap map;

    @Override
    public DocumentoOutput create(DOCUMENTO_CREATE input) {
        try {
            DocumentoDTO dto = map.toDto(input);
            DocumentoDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Documento: " + e.getMessage(), e);
        }
    }

    @Override
    public DocumentoOutput update(DOCUMENTO_UPDATE input) {
        try {
            DocumentoDTO dto = map.toDto(input);
            DocumentoDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Documento: " + e.getMessage(), e);
        }
    }

    @Override
    public DocumentoOutput patch(DOCUMENTO_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Documento");
    }

    @Override
    public DocumentoOutput findById(DOCUMENTO_FIND input) {
        try {
            DocumentoDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documento by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentoOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Documentos: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(DOCUMENTO_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Documento by ID: " + e.getMessage(), e);
        }
    }
}
