package prueba1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import prueba1.Service.*;
import prueba1.models.*;

import java.text.ParseException;
import java.util.List;

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
    private final DemasiaService demasiaService;
    @Autowired
    private final DecomisoService decomisoService;
    @Autowired
    private final MermaService mermaService;
    @Autowired
    private final TransferenciaService transferenciaService;

    public AjaxRestController(ProductorService productorService, RepresentanteService representanteService, UnidadOpeService unidadOpeService, UsuarioService usuarioService, CostoHcService costoHcService, TipoHcService tipoHcService, CompraService compraService, CajaBovedaService cajaBovedaService, InventarioService inventarioService, privateController privateController, DemasiaService demasiaService, DecomisoService decomisoService, MermaService mermaService, TransferenciaService transferenciaService) {
        this.productorService = productorService;
        this.representanteService = representanteService;
        this.unidadOpeService = unidadOpeService;
        this.usuarioService = usuarioService;
        this.costoHcService = costoHcService;
        this.tipoHcService = tipoHcService;
        this.compraService = compraService;
        this.cajaBovedaService = cajaBovedaService;
        this.inventarioService = inventarioService;
        this.demasiaService = demasiaService;
        this.decomisoService = decomisoService;
        this.mermaService = mermaService;
        this.transferenciaService = transferenciaService;
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
    @RequestMapping(
            value = "listRegistrosUniDemasia/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Demasia>> listRegistrosUniDemasia(@PathVariable("cod") String cod) {
        List<Demasia> list = demasiaService.listByUni(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<Demasia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listRegistrosUniDecomiso/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Decomiso>> listRegistrosUniDecomiso(@PathVariable("cod") String cod) {
        List<Decomiso> list = decomisoService.listByUni(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<Decomiso>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listRegistrosUniMerma/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Merma>> listRegistrosUniMerma(@PathVariable("cod") String cod) {
        List<Merma> list = mermaService.listByUni(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<Merma>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listRegistrosUniTransferencia/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Transferencia>> listRegistrosUniTransferencia(@PathVariable("cod") String cod) {
        List<Transferencia> list = transferenciaService.listByUni(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<Transferencia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "listRegistrosUniCajaBoveda/{cod}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CajaBoveda>> listRegistrosUniCajaBoveda(@PathVariable("cod") String cod) {
        List<CajaBoveda> list = cajaBovedaService.listByUni(cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {
            return new ResponseEntity<List<CajaBoveda>>(HttpStatus.BAD_REQUEST);
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
        List<Compra> list = compraService.listByProductCompra(hoj, uni);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Compra>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "viewRegistersDemasias/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Demasia>> registrosAlmacenDemasia(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<Demasia> list = demasiaService.listByProductDemasia(hoj, uni);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Demasia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "viewRegistersDecomisos/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Decomiso>> registrosAlmacenDecomisos(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<Decomiso> list = decomisoService.listByProductDecomiso(hoj, uni);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Decomiso>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "viewRegistersMerma/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Merma>> registrosAlmacenMerma(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<Merma> list = mermaService.listByProductMerma(hoj, uni);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Merma>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "viewRegistersTransferencia/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Transferencia>> registrosAlmacenTransferencia(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<Transferencia> list = transferenciaService.listByProductTransferencia(hoj, uni);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Transferencia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "viewRegistersCajaBoveda/{uni}/{hoj}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CajaBoveda>> registrosAlmacenCajaBoveda(@PathVariable("uni") String uni,@PathVariable("hoj") String hoj) {
        List<CajaBoveda> list = cajaBovedaService.listByUniT(uni,Integer.parseInt(hoj));
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<CajaBoveda>>(HttpStatus.BAD_REQUEST);
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
        List<Compra> list = compraService.registrosFechaCompra(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Compra>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateDemasias/{inicio}/{fin}/{codUni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Demasia>> registrosFechaAlmacenDemasia(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod) throws ParseException {
        List<Demasia> list = demasiaService.registrosFechaDemasia(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Demasia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateDecomisos/{inicio}/{fin}/{codUni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Decomiso>> registrosFechaAlmacenDecomiso(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod) throws ParseException {
        List<Decomiso> list = decomisoService.registrosFechaDecomiso(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Decomiso>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateMerma/{inicio}/{fin}/{codUni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Merma>> registrosFechaAlmacenMerma(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod) throws ParseException {
        List<Merma> list = mermaService.registrosFechaMerma(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Merma>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateTransferencia/{inicio}/{fin}/{codUni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Transferencia>> registrosFechaAlmacenTransferencia(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod) throws ParseException {
        List<Transferencia> list = transferenciaService.registrosFechaTransferencia(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Transferencia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateCajaBoveda/{inicio}/{fin}/{codUni}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CajaBoveda>> registrosFechaAlmacenCajaBoveda(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod) throws ParseException {
        List<CajaBoveda> list = cajaBovedaService.registrosFechaCajaBoveda(inicio, fin,cod);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<CajaBoveda>>(HttpStatus.BAD_REQUEST);
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
    @RequestMapping(
            value = "filterDateDemasias/{inicio}/{fin}/{codUni}/{codHc}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Demasia>> registrosFechaDemasiasHc(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod,@PathVariable("codHc") String codHc) throws ParseException {
        List<Demasia> list = demasiaService.registrosFechaDemasiaHc(inicio, fin,cod,codHc);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Demasia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateDecomisos/{inicio}/{fin}/{codUni}/{codHc}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Decomiso>> registrosFechaDecomisosHc(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod,@PathVariable("codHc") String codHc) throws ParseException {
        List<Decomiso> list = decomisoService.registrosFechaDecomisoHc(inicio, fin,cod,codHc);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Decomiso>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateMerma/{inicio}/{fin}/{codUni}/{codHc}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Merma>> registrosFechaMermaHc(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod,@PathVariable("codHc") String codHc) throws ParseException {
        List<Merma> list = mermaService.registrosFechaMermaHc(inicio, fin,cod,codHc);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Merma>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateTransferencia/{inicio}/{fin}/{codUni}/{codHc}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Transferencia>> registrosFechaTransferenciaHc(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod,@PathVariable("codHc") String codHc) throws ParseException {
        List<Transferencia> list = transferenciaService.registrosFechaTransferenciaHc(inicio, fin,cod,codHc);
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<Transferencia>>(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(
            value = "filterDateCajaBoveda/{inicio}/{fin}/{codUni}/{codHc}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CajaBoveda>> registrosFechaCajaBovedaT(@PathVariable("inicio") String inicio,@PathVariable("fin") String fin,@PathVariable("codUni") String cod,@PathVariable("codHc") String codHc) throws ParseException {
        List<CajaBoveda> list = cajaBovedaService.registrosFechaCajaBovedaT(inicio, fin,cod,Integer.parseInt(codHc));
        try {
            return ResponseEntity.accepted().body(list);
        } catch (Exception e) {

            return new ResponseEntity<List<CajaBoveda>>(HttpStatus.BAD_REQUEST);
        }
    }
}
