package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.CostoHojaCoca;

@Repository
public interface CostoHcRepository extends JpaRepository<CostoHojaCoca,Integer> {
}
