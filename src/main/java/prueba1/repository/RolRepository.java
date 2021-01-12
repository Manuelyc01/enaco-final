package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
}
