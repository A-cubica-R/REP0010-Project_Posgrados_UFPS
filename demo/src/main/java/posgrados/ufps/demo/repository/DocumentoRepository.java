package posgrados.ufps.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import posgrados.ufps.demo.entity.DocumentoEntity;

public interface DocumentoRepository extends CrudRepository<DocumentoEntity, Integer>{

    public List<DocumentoEntity> findByAspirante(Integer idAspirante);

}
