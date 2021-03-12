package prueba1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Movimiento;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {

}
