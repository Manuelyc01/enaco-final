package prueba1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Decomiso;
import prueba1.models.Demasia;
import prueba1.models.Movimiento;

import java.util.Date;
import java.util.List;

@Repository
public interface DecomisoRepository extends JpaRepository<Decomiso,Integer> {

    @Query("SELECT u FROM Decomiso u order by u.id_decomiso desc")
    public List<Decomiso> list(Pageable pageable);

    @Query("SELECT u from Decomiso u where u.cod_uniOpe.cod_uniOpe=?1 order by u.fecha desc")
    List<Decomiso> lisByUni(String cod);

    @Query("SELECT u from Decomiso u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 order by u.fecha desc")
    List<Decomiso> filterFechaDecomisoHc(Date ini, Date fn, String cod);

    @Query("SELECT u from Decomiso u where  u.cod_tipoHoja.cod_tipoHoja=?1 and  u.cod_uniOpe.cod_uniOpe=?2 order by u.fecha desc ")
    List<Decomiso> listByProductDecomiso(String cod_tipoHoja, String cod_uniOpe);

    @Query("select u from Decomiso  u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 and u.cod_tipoHoja.cod_tipoHoja=?4 order by u.fecha desc ")
    List<Decomiso> filterFechaDecomisoHc(Date ini, Date fn, String cod, String codHc);
}
