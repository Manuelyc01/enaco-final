package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.CostoHojaCoca;
import prueba1.models.TipoHojaCoca;

import java.util.List;

@Repository
public interface TipoHcRepository extends JpaRepository<TipoHojaCoca,String> {
}
