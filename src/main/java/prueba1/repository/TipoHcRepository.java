package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.TipoHojaCoca;

@Repository
public interface TipoHcRepository extends JpaRepository<TipoHojaCoca,String> {
}
