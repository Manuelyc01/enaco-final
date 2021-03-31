package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.models.Ingreso;
import prueba1.models.Inventario;
import prueba1.models.TipoHojaCoca;
import prueba1.models.UnidadOperativa;
import prueba1.repository.InventarioRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    //ACTA DE INVERTARIO
    @Override
    public List<TipoHojaCoca> actaHojas(String inicio, String fin, String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.actaHojas(ini,fn,cod);
    }
    @Override
    public Double actaSaldo(String inicio, String fin, String cod, String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        List<Inventario> in = repository.actaSaldo(ini, fn, cod, codHc, PageRequest.of(0, 1));
        return in.get(0).getStockInicial();
    }
    @Override
    public List<Ingreso> actaIngreso(String inicio, String fin, String cod, String codHc) throws ParseException {
        List<Ingreso> ingresos=new ArrayList<>();
        for (int i=1;i<4;i++){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date ini= format.parse(inicio.replace("T"," "));
            Date fn= format.parse(fin.replace("T"," "));
            List<Double> d = repository.actaIngreso(ini, fn, cod, codHc,i, PageRequest.of(0, 1));
            Ingreso ingreso=new Ingreso();
            ingreso.setId(i);
            if (d.get(0)==null){
               ingreso.setMonto(0.00);
            }else {
                ingreso.setMonto(d.get(0));
            }
            ingresos.add(ingreso);
        }
        return ingresos;
    }


}
