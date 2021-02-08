package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.TipoTransaccion;

@Repository
public interface TipoTransacRepository extends JpaRepository<TipoTransaccion,Integer> {
}
