package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Productor;

@Repository
public interface ProductorRepository extends JpaRepository<Productor,String> {
}
