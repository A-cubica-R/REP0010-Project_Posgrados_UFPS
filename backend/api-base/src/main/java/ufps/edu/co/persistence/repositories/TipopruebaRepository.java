package ufps.edu.co.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufps.edu.co.persistence.entities.TipopruebaEntity;

@Repository
public interface TipopruebaRepository extends JpaRepository<TipopruebaEntity, Integer> {
}
