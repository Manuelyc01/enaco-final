package prueba1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Inventario;
import prueba1.models.TipoHojaCoca;
import prueba1.models.UnidadOperativa;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Integer> {

    @Query("SELECT u FROM Inventario u order by u.id_inventario desc")
    public List<Inventario> list();

    @Query("SELECT u FROM Inventario u where u.cod_tipoHoja.cod_tipoHoja=?1 and u.cod_almacen.cod_uniOpe=?2 order by u.id_inventario desc ")
    public List<Inventario> listByProductAlmacen(String cod_tipoHoja, String cod_uniOpe);

    @Query("SELECT u FROM Inventario u where u.cod_tipoHoja=?1 and u.cod_almacen=?2 order by u.id_inventario desc ")
    public List<Inventario> listByProductAlmacenOne(TipoHojaCoca cod_tipoHoja, UnidadOperativa cod_uniOpe,Pageable pageable);

    @Query("SELECT u FROM Inventario u where u.cod_tipoHoja.cod_tipoHoja=?1 and u.cod_almacen.cod_uniOpe=?2 order by u.id_inventario desc ")
    public List<Inventario> stockHcAlmacen(String cod_tipoHoja, String cod_uniOpe,Pageable pageable);

    @Query("SELECT u FROM Inventario u where u.cod_almacen.cod_uniOpe=?1 order by u.id_inventario desc ")
    public List<Inventario> listByUni(String cod);


}
