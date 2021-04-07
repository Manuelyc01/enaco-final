package prueba1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import prueba1.Service.*;
import prueba1.models.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping(value = {"/private",""})
public class AjaxRestController {
    @Autowired
    private final ProductorService productorService;
    @Autowired
    private final RepresentanteService representanteService;
    @Autowired
    private final UnidadOpeService unidadOpeService;
    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final CostoHcService costoHcService;
    @Autowired
    private final TipoHcService tipoHcService;
    @Autowired
    private final CompraService compraService;
    @Autowired
    private final CajaBovedaService cajaBovedaService;
    @Autowired
    private final InventarioService inventarioService;
    @Autowired
    private final privateController privateController;

    public AjaxRestController(ProductorService productorService, RepresentanteService representanteService, UnidadOpeService unidadOpeService, UsuarioService usuarioService, CostoHcService costoHcService, TipoHcService tipoHcService, CompraService compraService, CajaBovedaService cajaBovedaService, InventarioService inventarioService, prueba1.controllers.privateController privateController) {
        this.productorService = productorService;
        this.representanteService = representanteService;
        this.unidadOpeService = unidadOpeService;
        this.usuarioService = usuarioService;
        this.costoHcService = costoHcService;
        this.tipoHcService = tipoHcService;
        this.compraService = compraService;
        this.cajaBovedaService = cajaBovedaService;
        this.inventarioService = inventarioService;
        this.privateController = privateController;
    }

    //LISTS
    @RequestMapping(
            value = "listCostoHc",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CostoHojaCoca>> listCostoHc() {
        List<CostoHojaCoca> list = costoHcService.list();
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<CostoHojaCoca>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listCostoHcF/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CostoHojaCoca>> listCostoHcF(@PathVariable("cod") String cod) {
        List<CostoHojaCoca> list = costoHcService.filterCostoHc(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<CostoHojaCoca>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listCompras/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Compra>> listCompras(@PathVariable("id") Integer id) {
        Usuario u = usuarioService.findById(id);
        List<Compra> list;
        if(u.getId_rol().getId_rol()==1){
            list = compraService.list();
        }else{
            list = compraService.listByIdUsuario(u.getId_usuario());
        }
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<Compra>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listTipoHc",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TipoHojaCoca>> listTipoHc() {
        List<TipoHojaCoca> list = tipoHcService.list();
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<TipoHojaCoca>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "unidadesOpe",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UnidadOperativa>> listUni() {
        List<UnidadOperativa> unidadOperativas = unidadOpeService.listar();
        try {
            return ResponseEntity.accepted().body(unidadOperativas);
        } catch (Exception e) {
            return new ResponseEntity<List<UnidadOperativa>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "cajabovedas",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CajaBoveda>> listCajaBoveda() {
        List<CajaBoveda> cajaBovedas = cajaBovedaService.list() ;
        try {
            return ResponseEntity.accepted().body(cajaBovedas);
        } catch (Exception e) {
            return new ResponseEntity<List<CajaBoveda>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "productores",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Productor>> listProduc() {
        List<Productor> productors = productorService.list();
        try {
            return ResponseEntity.accepted().body(productors);
        } catch (Exception e) {
            return new ResponseEntity<List<Productor>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "usuarios",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Usuario>> listUsu() {
        List<Usuario> usuarios = usuarioService.listar();
        try {
            return ResponseEntity.accepted().body(usuarios);
        } catch (Exception e) {
            return new ResponseEntity<List<Usuario>>(HttpStatus.BAD_REQUEST);
        }
    }

    //BUSCAR PRODUCTOR
    @RequestMapping(
            value = "buscarP/{id}",
            method = RequestMethod.GET,
            produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> buscarProd(@PathVariable("id") String id) {
        Productor productor = productorService.findByCedula(id);
        try {
            ResponseEntity<String> responseEntity =
                    new ResponseEntity<String>(productor.getNombre(), HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return new ResponseEntity<String>("Productor no registrado", HttpStatus.OK);
        }
    }
    //BUSCAR REPRESENTANTE
    @RequestMapping(
            value = "buscarR/{dni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Representante> buscarRepre(@PathVariable("dni") String dni) {
        Representante representante = representanteService.findByDni(dni);
        try {
            return ResponseEntity.accepted().body(representante);
        } catch (Exception e) {

            return new ResponseEntity<Representante>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "viewStock/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> stockHcAlmacen(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<Inventario> inventario = inventarioService.stockHcAlmacen(hoj, uni);

        try {
            Double stockFinal=0.0;
            if(inventario.size()==1){
                Inventario i = inventario.get(0);
                stockFinal= i.getStockFinal();
            }
            return ResponseEntity.accepted().body(stockFinal+"");
        } catch (Exception e) {

            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }


    //REGISTRO POR ALMACEN
    @RequestMapping(
            value = "listRegistrosUni/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Inventario>> listRegistrosUni(@PathVariable("cod") String cod) {
        List<Inventario> list = inventarioService.listByUni(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<Inventario>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listRegistrosUniCompras/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Compra>> listRegistrosUniCompras(@PathVariable("cod") String cod) {
        List<Compra> list = compraService.listByUni(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<Compra>>(HttpStatus.BAD_REQUEST);
        }
    }
    //REGISTRO POR ALMACEN Y HOJA DE COCA
    @RequestMapping(
            value = "viewRegisters/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Inventario>> registrosAlmacen(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<Inventario> inventarios = inventarioService.listByProductAlmacen(hoj, uni);
        try {
            return ResponseEntity.accepted().body(inventarios);
        } catch (Exception e) {

            return new ResponseEntity<List<Inventario>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "viewRegistersCompras/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Compra>> registrosAlmacenCompras(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<Compra> inventarios = compraService.listByProductCompra(hoj, uni);
        try {
            return ResponseEntity.accepted().body(inventarios);
        } catch (Exception e) {

            return new ResponseEntity<List<Compra>>(HttpStatus.BAD_REQUEST);
        }
    }
    //FILTRADO FECHA REPORTE ALMACEN
    @RequestMapping(
            value = "filterDate/{inicio}/{fin}/{codUni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Inventario>> registrosFechaAlmacen(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod) throws ParseException {
        List<Inventario> inventarios = inventarioService.registrosFechaAlmacen(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(inventarios);
        } catch (Exception e) {

            return new ResponseEntity<List<Inventario>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateCompras/{inicio}/{fin}/{codUni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Compra>> registrosFechaAlmacenCompras(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod) throws ParseException {
        List<Compra> inventarios = compraService.registrosFechaCompra(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(inventarios);
        } catch (Exception e) {

            return new ResponseEntity<List<Compra>>(HttpStatus.BAD_REQUEST);
        }
    }
    //FILTRADO FECHA REPORTE INVENTARIO POR PRODUCTO
    @RequestMapping(
            value = "filterDate/{inicio}/{fin}/{codUni}/{codHc}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Inventario>> registrosFechaAlmacenHc(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod,@PathVariable("codHc") String codHc) throws ParseException {
        List<Inventario> inventarios = inventarioService.registrosFechaAlmacenHc(inicio, fin,cod,codHc);
        try {
            return ResponseEntity.accepted().body(inventarios);
        } catch (Exception e) {

            return new ResponseEntity<List<Inventario>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateCompras/{inicio}/{fin}/{codUni}/{codHc}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Compra>> registrosFechaComprasHc(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod,@PathVariable("codHc") String codHc) throws ParseException {
        List<Compra> inventarios = compraService.registrosFechaCompraHc(inicio, fin,cod,codHc);
        try {
            return ResponseEntity.accepted().body(inventarios);
        } catch (Exception e) {

            return new ResponseEntity<List<Compra>>(HttpStatus.BAD_REQUEST);
        }
    }
}
