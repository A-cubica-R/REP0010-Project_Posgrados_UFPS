package ufps.edu.co.rest.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufps.edu.co.persistence.entities.DocumentosrequisitoprogramaEntity;
import ufps.edu.co.persistence.repositories.DocumentosrequisitoprogramaRepository;
import ufps.edu.co.rest.dto.DocumentosrequisitoprogramaDTO;
import ufps.edu.co.rest.services.commons.GenericService;

@Service
@Transactional
public class DocumentosrequisitoprogramaService extends GenericService<DocumentosrequisitoprogramaEntity, DocumentosrequisitoprogramaDTO> {

    @Autowired
    private DocumentosrequisitoprogramaRepository repository;

    public DocumentosrequisitoprogramaService() {
        super(DocumentosrequisitoprogramaEntity.class, DocumentosrequisitoprogramaDTO.class);
    }

    @Transactional(readOnly = true)
    public List<DocumentosrequisitoprogramaDTO> findAll() {
        return entityListToDtoList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public DocumentosrequisitoprogramaDTO findById(Integer id) {
        return entityToDto(repository.findById(id));
    }

    public DocumentosrequisitoprogramaDTO create(DocumentosrequisitoprogramaDTO dto) {
        return entityToDto(repository.save(dtoToEntity(dto)));
    }

    public DocumentosrequisitoprogramaDTO update(Integer id, DocumentosrequisitoprogramaDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentosrequisitoprograma no encontrado con id: " + id));
        dto.setId(id);
        return entityToDto(repository.save(dtoToEntity(dto)));
    }

    public void deleteById(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentosrequisitoprograma no encontrado con id: " + id));
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DocumentosrequisitoprogramaDTO> findByIdPrograma(Integer idPrograma) {
        return entityListToDtoList(repository.findByIdPrograma(idPrograma));
    }
}
