package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal,String> {
}
