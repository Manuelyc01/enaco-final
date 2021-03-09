package prueba1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Decomiso;
import prueba1.models.Movimiento;

import java.util.List;

@Repository
public interface DecomisoRepository extends JpaRepository<Decomiso,Integer> {

    @Query("SELECT u FROM Decomiso u order by u.id_decomiso desc")
    public List<Decomiso> list(Pageable pageable);
}
