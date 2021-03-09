package prueba1.Service;

import prueba1.models.Inventario;
import prueba1.models.TipoHojaCoca;
import prueba1.models.UnidadOperativa;

import java.util.List;

public interface InventarioService {
    void save(Inventario inventario);

    List<Inventario> list();

    List<Inventario> listByProductAlmacen(String cod_tipoHoja, String cod_uniOpe);

    List<Inventario> listByProductAlmacenOne(TipoHojaCoca cod_tipoHoja, UnidadOperativa cod_uniOpe);

    List<Inventario> stockHcAlmacen(String cod_tipoHoja, String cod_uniOpe);

    List<Inventario> listByUni(String cod_uniOpe);
}
