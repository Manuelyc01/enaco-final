package prueba1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Decomiso;
import prueba1.models.Demasia;

import java.util.List;

@Repository
public interface DemasiaRepository extends JpaRepository<Demasia,Integer> {

    @Query("SELECT u FROM Demasia u order by u.id_demasia desc")
    public List<Demasia> list(Pageable pageable);
}
