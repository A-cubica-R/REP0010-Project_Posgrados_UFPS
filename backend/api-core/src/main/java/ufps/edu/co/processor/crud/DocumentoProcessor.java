package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.DocumentoMap;
import ufps.edu.co.maps.specific.PersonaMap;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.*;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.rest.dto.DocumentoDTO;
import ufps.edu.co.rest.dto.EstadodocumentoDTO;
import ufps.edu.co.rest.dto.PersonaDTO;
import ufps.edu.co.rest.services.DocumentoService;
import ufps.edu.co.rest.services.EstadodocumentoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class DocumentoProcessor implements
        GlobalUseCase<DOCUMENTO_CREATE, DOCUMENTO_UPDATE, DOCUMENTO_DELETE, DOCUMENTO_PATCH, DOCUMENTO_FIND, DocumentoOutput> {

    @Autowired
    private DocumentoService service;

    @Autowired
    private DocumentoMap map;

    @Autowired
    private EstadodocumentoService estadodocumentoService;

    @Autowired
    private PersonaMap personaMap;

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

    public DocumentoOutput approveDocument(DOCUMENTO_FIND input) {
        try {
            DocumentoDTO dto = service.findById(input.id());
            EstadodocumentoDTO estadodocumentoDTO = estadodocumentoService.findById(2);
            dto.setEstadodocumento(estadodocumentoDTO);
            dto.setIdEstadodocumento(2);
            DocumentoDTO approve = service.update(input.id(), dto);
            return map.toOutput(approve);
        } catch (Exception e) {
            throw new RuntimeException("Error approving Documento: " + e.getMessage(), e);
        }
    }

    public DocumentoOutput rejectDocument(DOCUMENTO_REJECT input) {
        try {
            DocumentoDTO dto = service.findById(input.id());
            EstadodocumentoDTO estadodocumentoDTO = estadodocumentoService.findById(3);
            dto.setEstadodocumento(estadodocumentoDTO);
            dto.setObservaciones(input.observaciones());
            dto.setIdEstadodocumento(3);
            DocumentoDTO reject = service.update(input.id(), dto);
            return map.toOutput(reject);
        } catch (Exception e) {
            throw new RuntimeException("Error rejecting Documento: " + e.getMessage(), e);
        }
    }

    public List<DocumentoOutput> findByAspiranteId(ASPIRANTE_FIND input) {
        try {
            return service.findAll().stream()
                    .filter(dto -> dto.getAspirante() != null && dto.getAspirante().getId().equals(input.id()))
                    .map(map::toOutput)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documentos by Aspirante ID: " + e.getMessage(), e);
        }
    }

    public PersonaOutput findPersonByDocument(DOCUMENTO_FIND input){
        DocumentoDTO documento = service.findById(input.id());
        PersonaDTO persona = documento.getAspirante().getPersona();
        return personaMap.toOutput(persona);
    }
}
