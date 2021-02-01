package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer> {
}
