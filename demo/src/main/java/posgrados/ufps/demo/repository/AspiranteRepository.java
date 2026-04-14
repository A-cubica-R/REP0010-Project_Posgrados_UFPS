package posgrados.ufps.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import posgrados.ufps.demo.entity.AspiranteEntity;

@Repository
public interface AspiranteRepository extends JpaRepository<AspiranteEntity, Long> {

}