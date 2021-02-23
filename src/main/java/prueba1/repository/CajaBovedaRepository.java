package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.CajaBoveda;
import prueba1.models.CostoHojaCoca;

import java.util.List;

@Repository
public interface CajaBovedaRepository extends JpaRepository<CajaBoveda,Integer> {

    @Query("SELECT u FROM CajaBoveda u order by u.id_cajaBoveda desc")
    public List<CajaBoveda> list();
}
