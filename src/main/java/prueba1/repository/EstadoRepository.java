package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,Integer> {
}
