package prueba1.Service;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.controllers.ExportExcelKardex;
import prueba1.models.Inventario;
import prueba1.models.TipoHojaCoca;
import prueba1.models.UnidadOperativa;
import prueba1.repository.InventarioRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public List<Inventario> registrosFechaAlmacen(String inicio, String fin,String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaAlmacen(ini,fn,cod);
    }
    @Override
    public List<Inventario> registrosFechaAlmacenHc(String inicio, String fin,String cod,String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaAlmacenHc(ini,fn,cod,codHc);
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
