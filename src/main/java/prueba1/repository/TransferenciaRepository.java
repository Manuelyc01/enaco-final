package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Demasia;
import prueba1.models.Transferencia;

import java.util.Date;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia,Integer> {

    @Query("SELECT u from Transferencia u where u.origen.cod_uniOpe=?1 order by u.fecha desc")
    List<Transferencia> lisByUni(String cod);

    @Query("SELECT u from Transferencia u where u.fecha between ?1 and ?2 and u.origen.cod_uniOpe=?3 order by u.fecha desc")
    List<Transferencia> filterFechaTransferenciaHc(Date ini, Date fn, String cod);

    @Query("SELECT u from Transferencia u where  u.cod_tipoHoja.cod_tipoHoja=?1 and  u.origen.cod_uniOpe=?2 order by u.fecha desc ")
    List<Transferencia> listByProductTransferencia(String cod_tipoHoja, String cod_uniOpe);

    @Query("select u from Transferencia  u where u.fecha between ?1 and ?2 and u.origen.cod_uniOpe=?3 and u.cod_tipoHoja.cod_tipoHoja=?4 order by u.fecha desc ")
    List<Transferencia> filterFechaTransferenciaHc(Date ini, Date fn, String cod, String codHc);
}
