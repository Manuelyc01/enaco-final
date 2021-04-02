package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.models.Decomiso;
import prueba1.models.Demasia;
import prueba1.models.Inventario;
import prueba1.models.Movimiento;
import prueba1.repository.DecomisoRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DecomisoServiceImpl implements DecomisoService {
    @Autowired
    private final DecomisoRepository repository;

    @Autowired
    private final MovimientoService movimientoService;
    @Autowired
    private final InventarioService inventarioService;

    public DecomisoServiceImpl(DecomisoRepository repository, MovimientoService movimientoService, InventarioService inventarioService) {
        this.repository = repository;
        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
    }

    @Override
    public List<Decomiso> numMoviento(){
        return repository.list(PageRequest.of(0,1));
    }
    @Override
    public void save(Decomiso decomiso) {
        decomiso.setFecha(new Date());
        //REGISTRO INVENTARIO
        Inventario inventario=new Inventario();
        inventario.setId_usuario(decomiso.getId_usuario());
        inventario.setCod_almacen(decomiso.getCod_uniOpe());
        Movimiento mv = movimientoService.findById(2);
        inventario.setId_movimiento(mv);
        inventario.setDocumento(decomiso.getDocReferencia());
        inventario.setCod_tipoHoja(decomiso.getCod_tipoHoja());
        inventario.setPesoNeto(decomiso.getCantidadNeta());
        //STOCK INICIAL--STOCK FINAL
        List<Inventario> inventarios = inventarioService.listByProductAlmacen(decomiso.getCod_tipoHoja().getCod_tipoHoja(), decomiso.getCod_uniOpe().getCod_uniOpe());
        if (inventarios.size()==0){
            inventario.setStockInicial(0.00);
            inventario.setStockFinal(+decomiso.getCantidadNeta());
        }else {
            List<Inventario> inv1 = inventarioService.listByProductAlmacenOne(decomiso.getCod_tipoHoja(), decomiso.getCod_uniOpe());
            Inventario inventario1 = inv1.get(0);
            inventario.setStockInicial(inventario1.getStockFinal());
            inventario.setStockFinal(inventario1.getStockFinal()+decomiso.getCantidadNeta());
        }
        inventarioService.save(inventario);
        repository.save(decomiso);
    }

    @Override
    public List<Decomiso> listByUni(String cod){
        return repository.lisByUni(cod);
    }
    @Override
    public List<Decomiso> registrosFechaDecomiso(String inicio, String fin, String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaDecomisoHc(ini,fn,cod);
    }
    @Override
    public List<Decomiso> listByProductDecomiso(String cod_tipoHoja, String cod_uniOpe){
        return repository.listByProductDecomiso(cod_tipoHoja,cod_uniOpe);
    }
    @Override
    public List<Decomiso> registrosFechaDecomisoHc(String inicio, String fin, String cod, String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaDecomisoHc(ini,fn,cod,codHc);
    }

}
