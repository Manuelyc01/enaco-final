package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Compra;
import prueba1.models.CostoHojaCoca;
import prueba1.models.Usuario;

import java.util.Date;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer> {

    @Query("SELECT u FROM Compra u where u.id_usuario.id_usuario=?1 order by u.id_compra desc ")
    public List<Compra> listCompraByIdUsuario(Integer id);

    @Query("SELECT u from Compra u order by u.id_compra desc ")
    public List<Compra> list();

    @Query("SELECT u from Compra u where u.cod_uniOpe.cod_uniOpe=?1 order by u.fecha desc")
    List<Compra> lisByUni(String cod);

    @Query("SELECT u from Compra u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 order by u.fecha desc")
    List<Compra> filterFechaCompraHc(Date inicio, Date fin, String cod);

    @Query("SELECT u from Compra u where  u.cod_tipoHoja.cod_tipoHoja=?1 and  u.cod_uniOpe.cod_uniOpe=?2 order by u.fecha desc ")
    List<Compra> listByProductCompra(String cod_tipoHoja, String cod_uniOpe);

    @Query("select u from Compra  u where u.fecha between ?1 and ?2 and u.cod_uniOpe.cod_uniOpe=?3 and u.cod_tipoHoja.cod_tipoHoja=?4 order by u.fecha desc ")
    List<Compra> filterFechaCompraHc(Date ini, Date fn, String cod, String codHc);
}
