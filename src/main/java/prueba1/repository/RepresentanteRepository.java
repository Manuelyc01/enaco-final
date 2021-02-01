package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Representante;
import prueba1.models.Usuario;

@Repository
public interface RepresentanteRepository extends JpaRepository<Representante,Integer> {
    public Representante findByDni(String dni);
}
