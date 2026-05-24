package ufps.edu.co.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufps.edu.co.persistence.entities.DocumentosrequisitoprogramacohorteEntity;

@Repository
public interface DocumentosrequisitoprogramacohorteRepository extends JpaRepository<DocumentosrequisitoprogramacohorteEntity, Integer> {

    List<DocumentosrequisitoprogramacohorteEntity> findByIdCohorte(Integer idCohorte);
}
