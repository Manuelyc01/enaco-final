package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.models.Demasia;
import prueba1.models.Inventario;
import prueba1.models.Merma;
import prueba1.models.Movimiento;
import prueba1.repository.MermaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MermaServiceImpl implements MermaService{

    @Autowired
    private final MermaRepository repository;

    @Autowired
    private final MovimientoService movimientoService;
    @Autowired
    private final InventarioService inventarioService;

    public MermaServiceImpl(MermaRepository repository, MovimientoService movimientoService, InventarioService inventarioService) {
        this.repository = repository;
        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
    }
    @Override
    public List<Merma> numMovimiento(){
        return repository.list(PageRequest.of(0,1));
    }

    @Override
    public void save(Merma merma){
        merma.setFecha(new Date());
        //REGISTRO INVENTARIO
        Inventario inventario=new Inventario();
        inventario.setId_usuario(merma.getId_usuario());
        inventario.setCod_almacen(merma.getCod_uniOpe());
        Movimiento mv = movimientoService.findById(4);
        inventario.setId_movimiento(mv);
        inventario.setCod_tipoHoja(merma.getCod_tipoHoja());
        inventario.setPesoNeto(merma.getCantidadNeta());
        //STOCK INICIAL--STOCK FINAL

            List<Inventario> inv1 = inventarioService.listByProductAlmacenOne(merma.getCod_tipoHoja(), merma.getCod_uniOpe());
            Inventario inventario1 = inv1.get(0);
            inventario.setStockInicial(inventario1.getStockFinal());
            inventario.setStockFinal(inventario1.getStockFinal()-merma.getCantidadNeta());//RESTA POR MERMA

        inventarioService.save(inventario);
        repository.save(merma);
    }

    @Override
    public List<Merma> listByUni(String cod){
        return repository.lisByUni(cod);
    }
    @Override
    public List<Merma> registrosFechaMerma(String inicio, String fin, String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaMermaHc(ini,fn,cod);
    }
    @Override
    public List<Merma> listByProductMerma(String cod_tipoHoja, String cod_uniOpe){
        return repository.listByProductMerma(cod_tipoHoja,cod_uniOpe);
    }
    @Override
    public List<Merma> registrosFechaMermaHc(String inicio, String fin, String cod, String codHc) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaMermaHc(ini,fn,cod,codHc);
    }

}
