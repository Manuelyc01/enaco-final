package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.models.Inventario;
import prueba1.models.TipoHojaCoca;
import prueba1.models.UnidadOperativa;
import prueba1.repository.InventarioRepository;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService{

    @Autowired
    private final InventarioRepository repository;

    public InventarioServiceImpl(InventarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Inventario inventario){
        repository.save(inventario);
    }

    @Override
    public List<Inventario> list(){
        return repository.list();
    }

    @Override
    public List<Inventario> listByProductAlmacen(String cod_tipoHoja, String cod_uniOpe){
        return repository.listByProductAlmacen(cod_tipoHoja,cod_uniOpe);
    }
    @Override
    public List<Inventario> listByProductAlmacenOne(TipoHojaCoca cod_tipoHoja, UnidadOperativa cod_uniOpe){
        return repository.listByProductAlmacenOne(cod_tipoHoja,cod_uniOpe,PageRequest.of(0,1));
    }
    @Override
    public List<Inventario> stockHcAlmacen(String cod_tipoHoja, String cod_uniOpe){
        return repository.stockHcAlmacen(cod_tipoHoja,cod_uniOpe,PageRequest.of(0,1));
    }
    @Override
    public List<Inventario> listByUni(String cod_uniOpe){
        return repository.listByUni(cod_uniOpe);
    }
}
