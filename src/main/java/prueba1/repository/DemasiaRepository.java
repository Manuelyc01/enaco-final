package prueba1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Compra;
import prueba1.models.Decomiso;
import prueba1.models.Demasia;

import java.util.Date;
import java.util.List;

@Repository
public interface DemasiaRepository extends JpaRepository<Demasia,Integer> {

    @Query("SELECT u FROM Demasia u order by u.id_demasia desc")
    public List<Demasia> list(Pageable pageable);

    @Query("SELECT u from Demasia u where u.cod_uniOpe.cod_uniOpe=?1 order by u.fecha desc")
    List<Demasia> lisByUni(String cod);

    @Query("SELECT u from Demasia u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 order by u.fecha desc")
    List<Demasia> filterFechaDemasiaHc(Date ini, Date fn, String cod);

    @Query("SELECT u from Demasia u where  u.cod_tipoHoja.cod_tipoHoja=?1 and  u.cod_uniOpe.cod_uniOpe=?2 order by u.fecha desc ")
    List<Demasia> listByProductDemasia(String cod_tipoHoja, String cod_uniOpe);

    @Query("select u from Demasia  u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 and u.cod_tipoHoja.cod_tipoHoja=?4 order by u.fecha desc ")
    List<Demasia> filterFechaDemasiaHc(Date ini, Date fn, String cod, String codHc);
}
