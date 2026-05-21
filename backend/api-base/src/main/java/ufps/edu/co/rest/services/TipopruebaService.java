package ufps.edu.co.rest.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufps.edu.co.persistence.entities.TipopruebaEntity;
import ufps.edu.co.persistence.repositories.TipopruebaRepository;
import ufps.edu.co.rest.dto.TipopruebaDTO;
import ufps.edu.co.rest.services.commons.GenericService;

@Service
@Transactional
public class TipopruebaService extends GenericService<TipopruebaEntity, TipopruebaDTO> {

    @Autowired
    private TipopruebaRepository repository;

    public TipopruebaService() {
        super(TipopruebaEntity.class, TipopruebaDTO.class);
    }

    @Transactional(readOnly = true)
    public List<TipopruebaDTO> findAll() {
        return entityListToDtoList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public TipopruebaDTO findById(Integer id) {
        return entityToDto(repository.findById(id));
    }
}
