package ufps.edu.co.rest.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufps.edu.co.persistence.entities.DocumentosrequisitoprogramacohorteEntity;
import ufps.edu.co.persistence.repositories.DocumentosrequisitoprogramacohorteRepository;
import ufps.edu.co.rest.dto.DocumentosrequisitoprogramacohorteDTO;
import ufps.edu.co.rest.services.commons.GenericService;

@Service
@Transactional
public class DocumentosrequisitoprogramacohorteService extends GenericService<DocumentosrequisitoprogramacohorteEntity, DocumentosrequisitoprogramacohorteDTO> {

    @Autowired
    private DocumentosrequisitoprogramacohorteRepository repository;

    public DocumentosrequisitoprogramacohorteService() {
        super(DocumentosrequisitoprogramacohorteEntity.class, DocumentosrequisitoprogramacohorteDTO.class);
    }

    @Transactional(readOnly = true)
    public List<DocumentosrequisitoprogramacohorteDTO> findAll() {
        return entityListToDtoList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public DocumentosrequisitoprogramacohorteDTO findById(Integer id) {
        return entityToDto(repository.findById(id));
    }

    @Transactional(readOnly = true)
    public List<DocumentosrequisitoprogramacohorteDTO> findByIdCohorte(Integer idCohorte) {
        return entityListToDtoList(repository.findByIdCohorte(idCohorte));
    }

    public DocumentosrequisitoprogramacohorteDTO create(DocumentosrequisitoprogramacohorteDTO dto) {
        return entityToDto(repository.save(dtoToEntity(dto)));
    }

    public DocumentosrequisitoprogramacohorteDTO update(Integer id, DocumentosrequisitoprogramacohorteDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentosrequisitoprogramacohorte no encontrado con id: " + id));
        dto.setId(id);
        return entityToDto(repository.save(dtoToEntity(dto)));
    }

    public void deleteById(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentosrequisitoprogramacohorte no encontrado con id: " + id));
        repository.deleteById(id);
    }
}
