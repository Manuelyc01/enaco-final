package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia,String> {
}
