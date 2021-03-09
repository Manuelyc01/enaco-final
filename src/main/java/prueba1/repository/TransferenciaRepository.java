package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia,Integer> {
}
