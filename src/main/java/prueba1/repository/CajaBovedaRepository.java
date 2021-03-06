package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.CajaBoveda;
import org.springframework.data.domain.Pageable;
import prueba1.models.Demasia;

import java.util.Date;
import java.util.List;

@Repository
public interface CajaBovedaRepository extends JpaRepository<CajaBoveda,Integer> {

    @Query("SELECT u FROM CajaBoveda u order by u.id_cajaBoveda desc")
    public List<CajaBoveda> list();

    @Query("select u from  CajaBoveda u where u.cod_uniOpe.cod_uniOpe=?1 order by u.fecha desc")
    public List<CajaBoveda> getStockFinal(String cod,Pageable pageable);

    @Query("SELECT u from CajaBoveda u where u.cod_uniOpe.cod_uniOpe=?1 order by u.fecha desc")
    List<CajaBoveda> lisByUni(String cod);

    @Query("SELECT u from CajaBoveda u where u.cod_uniOpe.cod_uniOpe=?1 and u.id_tipoTransac.id_tipoTransac=?2 order by u.fecha desc")
    List<CajaBoveda> lisByUniT(String cod,Integer t);

    @Query("SELECT u from CajaBoveda u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 order by u.fecha desc")
    List<CajaBoveda> filterFechaCajaBoveda(Date ini, Date fn, String cod);

    @Query("SELECT u from CajaBoveda u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 and u.id_tipoTransac.id_tipoTransac=?4 order by u.fecha desc")
    List<CajaBoveda> filterFechaCajaBovedaT(Date ini, Date fn, String cod,Integer transaccion);

}
