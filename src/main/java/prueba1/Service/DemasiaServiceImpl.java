package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.models.Demasia;
import prueba1.models.Inventario;
import prueba1.models.Movimiento;
import prueba1.repository.DemasiaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DemasiaServiceImpl implements DemasiaService{

    @Autowired
    private final DemasiaRepository repository;

    @Autowired
    private final MovimientoService movimientoService;
    @Autowired
    private final InventarioService inventarioService;
    public DemasiaServiceImpl(DemasiaRepository repository, MovimientoService movimientoService, InventarioService inventarioService) {
        this.repository = repository;
        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
    }

    @Override
    public void save(Demasia demasia){
        demasia.setFecha(new Date());
        //REGISTRO INVENTARIO
        Inventario inventario=new Inventario();
        inventario.setId_usuario(demasia.getId_usuario());
        inventario.setCod_almacen(demasia.getCod_uniOpe());
        Movimiento mv = movimientoService.findById(3);
        inventario.setId_movimiento(mv);
        inventario.setDocumento(demasia.getDocumento());
        inventario.setCod_tipoHoja(demasia.getCod_tipoHoja());
        inventario.setPesoNeto(demasia.getCantidadNeta());
        //STOCK INICIAL--STOCK FINAL
        List<Inventario> inventarios = inventarioService.listByProductAlmacen(demasia.getCod_tipoHoja().getCod_tipoHoja(), demasia.getCod_uniOpe().getCod_uniOpe());
        if (inventarios.size()==0){
            inventario.setStockInicial(0.00);
            inventario.setStockFinal(+demasia.getCantidadNeta());
        }else {
            List<Inventario> inv1 = inventarioService.listByProductAlmacenOne(demasia.getCod_tipoHoja(), demasia.getCod_uniOpe());
            Inventario inventario1 = inv1.get(0);
            inventario.setStockInicial(inventario1.getStockFinal());
            inventario.setStockFinal(inventario1.getStockFinal()+demasia.getCantidadNeta());
        }
        inventarioService.save(inventario);
        repository.save(demasia);
    }
    @Override
    public List<Demasia> numMovimiento(){
        return repository.list(PageRequest.of(0,1));
    }

    @Override
    public List<Demasia> listByUni(String cod){
        return repository.lisByUni(cod);
    }
    @Override
    public List<Demasia> registrosFechaDemasia(String inicio, String fin, String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaDemasiaHc(ini,fn,cod);
    }
    @Override
    public List<Demasia> listByProductDemasia(String cod_tipoHoja, String cod_uniOpe){
        return repository.listByProductDemasia(cod_tipoHoja,cod_uniOpe);
    }
    @Override
    public List<Demasia> registrosFechaDemasiaHc(String inicio, String fin, String cod, String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaDemasiaHc(ini,fn,cod,codHc);
    }

}
