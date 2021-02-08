package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.CajaBoveda;

@Repository
public interface CajaBovedaRepository extends JpaRepository<CajaBoveda,Integer> {
}
