package ufps.edu.co.rest.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufps.edu.co.persistence.entities.DocumentosrequisitoconcejocohorteEntity;
import ufps.edu.co.persistence.repositories.DocumentosrequisitoconcejocohorteRepository;
import ufps.edu.co.rest.dto.DocumentosrequisitoconcejocohorteDTO;
import ufps.edu.co.rest.services.commons.GenericService;

@Service
@Transactional
public class DocumentosrequisitoconcejocohorteService extends GenericService<DocumentosrequisitoconcejocohorteEntity, DocumentosrequisitoconcejocohorteDTO> {

    @Autowired
    private DocumentosrequisitoconcejocohorteRepository repository;

    public DocumentosrequisitoconcejocohorteService() {
        super(DocumentosrequisitoconcejocohorteEntity.class, DocumentosrequisitoconcejocohorteDTO.class);
    }

    @Transactional(readOnly = true)
    public List<DocumentosrequisitoconcejocohorteDTO> findAll() {
        return entityListToDtoList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public DocumentosrequisitoconcejocohorteDTO findById(Integer id) {
        return entityToDto(repository.findById(id));
    }

    @Transactional(readOnly = true)
    public List<DocumentosrequisitoconcejocohorteDTO> findByIdCohorte(Integer idCohorte) {
        return entityListToDtoList(repository.findByIdCohorte(idCohorte));
    }

    public DocumentosrequisitoconcejocohorteDTO create(DocumentosrequisitoconcejocohorteDTO dto) {
        return entityToDto(repository.save(dtoToEntity(dto)));
    }

    public DocumentosrequisitoconcejocohorteDTO update(Integer id, DocumentosrequisitoconcejocohorteDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentosrequisitoconcejocohorte no encontrado con id: " + id));
        dto.setId(id);
        return entityToDto(repository.save(dtoToEntity(dto)));
    }

    public void deleteById(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentosrequisitoconcejocohorte no encontrado con id: " + id));
        repository.deleteById(id);
    }
}
