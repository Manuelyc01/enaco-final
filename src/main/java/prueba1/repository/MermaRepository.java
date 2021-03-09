package prueba1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Decomiso;
import prueba1.models.Merma;

import java.util.List;

@Repository
public interface MermaRepository extends JpaRepository<Merma,Integer> {

    @Query("SELECT u FROM Merma u order by u.id_merma desc")
    public List<Merma> list(Pageable pageable);
}
