package ufps.edu.co.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ufps.edu.co.persistence.entities.DocumentosrequisitoprogramaEntity;

@Repository
public interface DocumentosrequisitoprogramaRepository extends JpaRepository<DocumentosrequisitoprogramaEntity, Integer> {

    @Query(value = """
            SELECT DISTINCT drp.*
            FROM documentosrequisitoprograma drp
            JOIN documentosrequisitoprogramacohorte drpc ON drp.id = drpc.id_docrequisito
            JOIN cohorte c ON c.id = drpc.id_cohorte
            WHERE c.id_programa = :idPrograma
            """, nativeQuery = true)
    List<DocumentosrequisitoprogramaEntity> findByIdPrograma(@Param("idPrograma") Integer idPrograma);
}
