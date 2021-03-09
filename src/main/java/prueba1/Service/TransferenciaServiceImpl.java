package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.Inventario;
import prueba1.models.Movimiento;
import prueba1.models.Transferencia;
import prueba1.repository.TransferenciaRepository;

import java.util.Date;
import java.util.List;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {
    @Autowired
    private  final TransferenciaRepository repository;

    @Autowired
    private final MovimientoService movimientoService;
    @Autowired
    private final InventarioService inventarioService;

    public TransferenciaServiceImpl(TransferenciaRepository repository, MovimientoService movimientoService, InventarioService inventarioService) {
        this.repository = repository;
        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
    }

    @Override
    public void save(Transferencia transferencia){
        transferencia.setFecha(new Date());
        //REGISTRO INVENTARIO ORIGEN
        Inventario inventario=new Inventario();
        inventario.setId_usuario(transferencia.getId_usuario());
        inventario.setCod_almacen(transferencia.getOrigen());
        Movimiento mv = movimientoService.findById(5);
        inventario.setId_movimiento(mv);
        inventario.setCod_tipoHoja(transferencia.getCod_tipoHoja());
        inventario.setPesoNeto(transferencia.getCantidad());
        //STOCK INICIAL--STOCK FINAL
        List<Inventario> inv1 = inventarioService.listByProductAlmacenOne(transferencia.getCod_tipoHoja(), transferencia.getOrigen());
        Inventario inventario1 = inv1.get(0);
        inventario.setStockInicial(inventario1.getStockFinal());
        inventario.setStockFinal(inventario1.getStockFinal()-transferencia.getCantidad());//RESTAR STOCK ORIGEN

        //REGISTRO INVENTARIO DESTINO
        Inventario inventario2=new Inventario();
        inventario2.setId_usuario(transferencia.getId_usuario());
        inventario2.setCod_almacen(transferencia.getDestino());
        Movimiento mv1 = movimientoService.findById(5);
        inventario2.setId_movimiento(mv1);
        inventario2.setCod_tipoHoja(transferencia.getCod_tipoHoja());
        inventario2.setPesoNeto(transferencia.getCantidad());
        //STOCK INICIAL--STOCK FINAL
        List<Inventario> inv2 = inventarioService.listByProductAlmacenOne(transferencia.getCod_tipoHoja(), transferencia.getDestino());
        if(inv2.size()==0){
            inventario2.setStockInicial(0.00);
            inventario2.setStockFinal(+transferencia.getCantidad());//SUMAR STOCK DESTINO
        }else {
            Inventario invent2 = inv2.get(0);
            inventario2.setStockInicial(invent2.getStockFinal());
            inventario2.setStockFinal(invent2.getStockFinal()+transferencia.getCantidad());//SUMAR STOCK DESTINO
        }
        inventarioService.save(inventario);
        inventarioService.save(inventario2);
        repository.save(transferencia);
    }
}
