package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.UnidadOperativa;

@Repository
public interface UnidadOperativaRepository extends JpaRepository<UnidadOperativa,String> {
}
