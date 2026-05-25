package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.DocumentosrequisitoprogramaMap;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramaOutput;
import ufps.edu.co.rest.dto.DocumentosrequisitoprogramaDTO;
import ufps.edu.co.rest.services.DocumentosrequisitoprogramaService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class DocumentosrequisitoprogramaProcessor implements
        GlobalUseCase<
            DOCUMENTOSREQUISITOPROGRAMA_CREATE, 
            DOCUMENTOSREQUISITOPROGRAMA_UPDATE, 
            DOCUMENTOSREQUISITOPROGRAMA_DELETE, 
            DOCUMENTOSREQUISITOPROGRAMA_PATCH, 
            DOCUMENTOSREQUISITOPROGRAMA_FIND, 
            DocumentosrequisitoprogramaOutput> {

    @Autowired
    private DocumentosrequisitoprogramaService service;

    @Autowired
    private DocumentosrequisitoprogramaMap map;

    @Override
    public DocumentosrequisitoprogramaOutput create(DOCUMENTOSREQUISITOPROGRAMA_CREATE input) {
        try {
            DocumentosrequisitoprogramaDTO dto = map.toDto(input);
            return map.toOutput(service.create(dto));
        } catch (Exception e) {
            throw new RuntimeException("Error creating Documentosrequisitoprograma: " + e.getMessage(), e);
        }
    }

    @Override
    public DocumentosrequisitoprogramaOutput update(DOCUMENTOSREQUISITOPROGRAMA_UPDATE input) {
        try {
            DocumentosrequisitoprogramaDTO dto = map.toDto(input);
            return map.toOutput(service.update(input.id(), dto));
        } catch (Exception e) {
            throw new RuntimeException("Error updating Documentosrequisitoprograma: " + e.getMessage(), e);
        }
    }

    @Override
    public DocumentosrequisitoprogramaOutput patch(DOCUMENTOSREQUISITOPROGRAMA_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Documentosrequisitoprograma");
    }

    @Override
    public DocumentosrequisitoprogramaOutput findById(DOCUMENTOSREQUISITOPROGRAMA_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documentosrequisitoprograma by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentosrequisitoprogramaOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Documentosrequisitoprograma: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(DOCUMENTOSREQUISITOPROGRAMA_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Documentosrequisitoprograma by ID: " + e.getMessage(), e);
        }
    }

    public List<DocumentosrequisitoprogramaOutput> findByIdPrograma(Integer idPrograma) {
        try {
            return service.findByIdPrograma(idPrograma).stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documentosrequisitoprograma by programa: " + e.getMessage(), e);
        }
    }
}
