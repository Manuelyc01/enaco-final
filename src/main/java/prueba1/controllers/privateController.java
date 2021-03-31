package prueba1.controllers;

import net.sf.jasperreports.engine.JRException;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prueba1.Service.*;
import prueba1.models.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = {"/private","" })
public class privateController {

    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final UnidadOpeService unidadOpeService;
    @Autowired
    private final ProductorService productorService;
    @Autowired
    private final CostoHcService costoHcService;
    @Autowired
    private final SucursalService sucursalService;
    @Autowired
    private final AgenciaService agenciaService;
    @Autowired
    private final CompraService compraService;
    @Autowired
    private final TipoHcService tipoHcService;
    @Autowired
    private final TipoTransacService tipoTransacService;
    @Autowired
    private final CajaBovedaService cajaBovedaService;
    @Autowired
    private final RepresentanteService representanteService;
    @Autowired
    private final DecomisoService decomisoService;
    @Autowired
    private final MermaService mermaService;
    @Autowired
    private final TransferenciaService transferenciaService;
    @Autowired
    private final MovimientoService movimientoService;
    @Autowired
    private final InventarioService inventarioService;
    @Autowired
    private final DemasiaService demasiaService;
    @Autowired
    private final EstadoService estadoService;
    public privateController(UsuarioService usuarioService, UnidadOpeService unidadOpeService, ProductorService productorService, CostoHcService costoHcService, SucursalService sucursalService, AgenciaService agenciaService, CompraService compraService, TipoHcService tipoHcService, TipoTransacService tipoTransacService, CajaBovedaService cajaBovedaService, RepresentanteService representanteService, DecomisoService decomisoService, MermaService mermaService, TransferenciaService transferenciaService, MovimientoService movimientoService, InventarioService inventarioService, DemasiaService demasiaService, EstadoService estadoService) {
        this.usuarioService = usuarioService;
        this.unidadOpeService = unidadOpeService;
        this.productorService = productorService;
        this.costoHcService = costoHcService;
        this.sucursalService = sucursalService;
        this.agenciaService = agenciaService;
        this.compraService = compraService;
        this.tipoHcService = tipoHcService;
        this.tipoTransacService = tipoTransacService;
        this.cajaBovedaService = cajaBovedaService;
        this.representanteService = representanteService;
        this.decomisoService = decomisoService;
        this.mermaService = mermaService;
        this.transferenciaService = transferenciaService;
        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
        this.demasiaService = demasiaService;
        this.estadoService = estadoService;
    }

    //MENU PRINCIPAL
    @GetMapping("/index")
    public String index(Model model,Authentication auth, HttpSession session){
        String username =auth.getName();
        if(session.getAttribute("usuario")==null){
            Usuario usuario = usuarioService.findByUsua(username);
            session.setAttribute("usuario",usuario);
        }
        model.addAttribute("inicio","yes");
        return "menu";
    }
    //REGISTRO
    @GetMapping("/auth/registrar")
    public String registroForm(Model model, Authentication auth ){
        String username=auth.getName();
        Usuario usuario = usuarioService.findByUsua(username);
        List<UnidadOperativa> listar = unidadOpeService.listar();
        
        //Only admin "1" can register new users
        if(usuario.getId_rol().getId_rol()==1){
            model.addAttribute("user", new Usuario());
            model.addAttribute("unidadesOpe", listar);
            model.addAttribute("r", "yes");
            return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //ROL SUP & OPE FOR ADMIN
    @GetMapping("/auth/rolSupervisor")
    public String rolSupervisor(Model model, Authentication auth ){
        String username=auth.getName();
        Usuario usuario = usuarioService.findByUsua(username);
        //Only admin "1" can register new users
        if(usuario.getId_rol().getId_rol()==1){
            model.addAttribute("rolSupervisor", "yes");
            return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    @GetMapping("/auth/rolOperador")
    public String rolOperador(Model model, Authentication auth ){
        String username=auth.getName();
        Usuario usuario = usuarioService.findByUsua(username);
        //Only admin "1" can register new users
        if(usuario.getId_rol().getId_rol()==1 || usuario.getId_rol().getId_rol()==2 ){
            model.addAttribute("rolOperador", "yes");
            return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    @PostMapping("/auth/registro")
    public String registro(Usuario usuario,Model model,Authentication auth){
        String username=auth.getName();
        Usuario u = usuarioService.findByUsua(username);
        if(u.getId_rol().getId_rol()==1){
            String user= usuario.getUsua();
            if(usuarioService.findByUsua(user)!=null){
                //SI EL USUARIO EXISTE
                List<UnidadOperativa> listar = unidadOpeService.listar();
                model.addAttribute("user",usuario);
                model.addAttribute("unidadesOpe", listar);
                model.addAttribute("userExist", "usuario existe");
                model.addAttribute("r", 1);
                return "menu";
            }else {
                usuarioService.registrar(usuario);
                return "redirect:/private/index";
            }
        }else{
            return "redirect:/private/index";
        }
    }

    //LISTAR USUARIO
    @GetMapping("/auth/listaUsuarios")
    public String listarUsuario(Model model,Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
            model.addAttribute("list","yes");
            return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //LISTAR PRODUCTOR
    @GetMapping("/auth/listProductor")
    public String listarProductor(Model model, Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        model.addAttribute("listP","yes");
        return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //LISTAR COSTO HOJA COCA
    @GetMapping("/auth/listCostoHC")
    public String listarCostoHC(Model model, Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        model.addAttribute("listC","list");
        return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //LISTAR SUCURSAL
    @GetMapping("/auth/listSucursal")
    public String listarSucursal(Model model,Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        List<Sucursal> list = sucursalService.list();
        model.addAttribute("listS",list);
        return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //LISTAR AGENCIA
    @GetMapping("/auth/listAgencia")
    public String listarAgencia(Model model,Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        List<Agencia> list = agenciaService.list();
        model.addAttribute("listA",list);
        return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //LISTAR UNIDAD
    @GetMapping("/auth/listUnidadOpe")
    public String listarUnidadOpe(Model model, Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
       model.addAttribute("listU","listar");
        return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //LISTAR TIPO HC
    @GetMapping("/auth/listTipoHc")
    public String listarTipoHc(Model model, Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        model.addAttribute("listT","yes");
        return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    //LISTAR COMPRAS
    @GetMapping("/auth/listCompras")
    public String listarCompras(Model model, Authentication auth){
        Usuario usuario = usuarioService.findByUsua(auth.getName());
        model.addAttribute("listCompras","yes");
        model.addAttribute("id",usuario.getId_usuario());
        return "menu";
    }
    //ELIMINAR USUARIO
    @GetMapping("/auth/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Integer id,Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        usuarioService.eliminar(id);
        return "redirect:/auth/listaUsuarios";
    }else{
        return "redirect:/private/index";
    }
    }

    //EDITAR USUARIO
    @GetMapping("/auth/editUsuario/{id}")
    public String editarUsuario(@PathVariable Integer id,Model model,Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        Usuario u = usuarioService.findById(id);

        List<UnidadOperativa> listar = unidadOpeService.listar();
        model.addAttribute("user",u);
        model.addAttribute("e", 1);

        model.addAttribute("unidadesOpe", listar);
        return "menu";
        }else{
            return "redirect:/private/index";
        }
    }
    @PostMapping("/auth/update/{id}")
    public String actualizarUsuario(@PathVariable Integer id,Usuario usuario, Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        if(byUsua.getId_rol().getId_rol()==1){
        usuarioService.update(id,usuario);
        return "redirect:/auth/listaUsuarios";
            }else{
                return "redirect:/private/index";
            }
    }
    //COMPRA
    @GetMapping("/auth/compraUsuario")
    public String compraUsuario(Model model,Authentication auth){
        String name = auth.getName();
        Usuario byUsua = usuarioService.findByUsua(name);
        List<UnidadOperativa> listarU;

            model.addAttribute("c","yes");//
        //FILTRADO POR LOCALIDAD EN SUPERVISOR Y OPERADOR
            if(byUsua.getId_rol().getId_rol()==3||byUsua.getId_rol().getId_rol()==2){

                Agencia cod_agencia = byUsua.getCod_uniOpe().getCod_agencia();
                Sucursal cod_sucursal = byUsua.getCod_uniOpe().getCod_sucursal();

                if (cod_agencia!= null && cod_sucursal != null){
                    listarU = unidadOpeService.listarAgeSu(cod_agencia.getCod_agencia(),cod_sucursal.getCod_sucursal());
                    model.addAttribute("unidadesOpe", listarU);
                }else if (cod_agencia!= null && cod_sucursal == null){
                    listarU=unidadOpeService.listarAgencia(cod_agencia.getCod_agencia());
                    model.addAttribute("unidadesOpe", listarU);
                }else if (cod_agencia== null && cod_sucursal != null){
                    listarU=unidadOpeService.listarSucursal(cod_sucursal.getCod_sucursal());
                    model.addAttribute("unidadesOpe", listarU);
                }
            }else{
                listarU = unidadOpeService.listar();
                model.addAttribute("unidadesOpe", listarU);
            }


            model.addAttribute("realizarCompra", "yes");//
            model.addAttribute("id", byUsua.getId_usuario());//

            model.addAttribute("compra",nuevaCompra(byUsua.getId_usuario()));
            return "menu";
    }

    //DATOS COMPRA
    public Compra nuevaCompra(Integer id){
        //OBTENER NUM COMPRAS DE USUARIO
        Usuario u = usuarioService.findById(id);
        Integer num;
        if (u.getNum_compras()!=null){
            num=u.getNum_compras()+1;
        }else {
            num=1;
        }

        //GENERAR NUEVO OBJETO COMPRA
        Compra compra=new Compra();
        int n=num.toString().length();//TAMAÑO DEL NUMERO
        switch (n)//CANTIDAD DE DIGITOS (6)
        {
            case 1:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"00000"+num.toString());
                break;
            case 2:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"0000"+num.toString());
                break;
            case 3:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"000"+num.toString());
                break;
            case 4:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"00"+num.toString());
                break;
            case 5:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+"0"+num.toString());
                break;
            case 6:
                compra.setNum_liquidacion(u.getSerie_compra()+"-"+num.toString());

        }
        //ENVIO FECHA HOY
        Date date=new Date();
        compra.setFecha(date);
        //TARA y HUMEDAD
        compra.setTara(0.);
        compra.setHumedad(0.);

        return compra;
    }
    @PostMapping("/auth/comprar")
    public String comprar(Compra compra, Authentication auth, Model model, RedirectAttributes attributes){
        Productor cedula_productor = compra.getCedula_productor();
        String dni_repre = compra.getDni_repre();
        Representante d = representanteService.findByDni(dni_repre);
        if(cedula_productor==null && d==null){//ERROR DIGITACION CEDULA Y DNI
            compra.setPesoBruto(0.0);
            model.addAttribute("msg","Digitar cedula y dni correctamente");
            model.addAttribute("c","yes");
            model.addAttribute("unidadesOpe", unidadOpeService.listar());
            List<CostoHojaCoca> costoHojaCocas = costoHcService.filterCostoHc(compra.getCod_uniOpe().getCod_uniOpe());
            model.addAttribute("tiposHc",costoHojaCocas);
            model.addAttribute("compra",compra);
            return "menu";
        }else if(cedula_productor!=null && d==null){//ERROR DIGITACION  DNI
            compra.setPesoBruto(0.0);
            List<CostoHojaCoca> costoHojaCocas = costoHcService.filterCostoHc(compra.getCod_uniOpe().getCod_uniOpe());
            model.addAttribute("tiposHc",costoHojaCocas);
            model.addAttribute("msg","Digitar dni correctamente");
            model.addAttribute("c","yes");
            model.addAttribute("unidadesOpe", unidadOpeService.listar());
            model.addAttribute("compra",compra);
            return "menu";
        }else if (cedula_productor==null && d!=null){//ERROR DIGITACION  DNI
            compra.setPesoBruto(0.0);
            model.addAttribute("msg","Digitar cedula correctamente");
            model.addAttribute("unidadesOpe", unidadOpeService.listar());
            model.addAttribute("c","yes");
            List<CostoHojaCoca> costoHojaCocas = costoHcService.filterCostoHc(compra.getCod_uniOpe().getCod_uniOpe());
            model.addAttribute("tiposHc",costoHojaCocas);
            model.addAttribute("compra",compra);
            return "menu";
        }else {
            UnidadOperativa uni = unidadOpeService.findByCod(compra.getCod_uniOpe().getCod_uniOpe());
            Double cb = uni.getCajaBoveda();
            Double vc = compra.getValorCompra();
            //VALOR DE COMPRA MENOR O IGUAL A CAJA BOVEDA
            if (vc<=cb){
                String name = auth.getName();
                Usuario u = usuarioService.findByUsua(name);
                compra.setId_usuario(u);
                compraService.save(u.getId_usuario(),compra);

                return "redirect:/compraRealizada/"+compra.getId_compra();
            }else {
                model.addAttribute("msg","Digitar cedula correctamente");
                model.addAttribute("unidadesOpe", unidadOpeService.listar());
                model.addAttribute("c","yes");
                List<CostoHojaCoca> costoHojaCocas = costoHcService.filterCostoHc(compra.getCod_uniOpe().getCod_uniOpe());
                model.addAttribute("tiposHc",costoHojaCocas);
                model.addAttribute("compra",compra);
                return "menu";
            }
        }
    }

    @GetMapping("/compraRealizada/{id}")
    public String compraRealizada(@PathVariable Integer id, Model model){
        Compra compra = compraService.findById(id);
        model.addAttribute("compraRealizada","yes");//

        model.addAttribute("cedula",compra.getCedula_productor().getCedula());
        model.addAttribute("nombP",compra.getCedula_productor().getNombre());
        model.addAttribute("dni",compra.getDni_repre());
        model.addAttribute("nombR",representanteService.findByDni(compra.getDni_repre()).getNombre());
        model.addAttribute("uni",compra.getCod_uniOpe().getNom_uniOpe());
        model.addAttribute("hc",compra.getCod_tipoHoja().getNombre());
        model.addAttribute("total",compra.getTotalCompra());
        model.addAttribute("id",compra.getId_compra());
        return "menu";
    }

    //CAJA BOVEDA
    @GetMapping("/auth/cajaBoveda")
    public String cajaBoveda(Model model,Authentication auth){
        Usuario byUsua = usuarioService.findByUsua(auth.getName());
        List<UnidadOperativa> listarU=new ArrayList<>();
        //FILTRADO POR LOCALIDAD EN SUPERVISOR Y OPERADOR
        if(byUsua.getId_rol().getId_rol()==3||byUsua.getId_rol().getId_rol()==2){

            Agencia cod_agencia = byUsua.getCod_uniOpe().getCod_agencia();
            Sucursal cod_sucursal = byUsua.getCod_uniOpe().getCod_sucursal();

            if (cod_agencia!= null && cod_sucursal != null){
                listarU = unidadOpeService.listarAgeSu(cod_agencia.getCod_agencia(),cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia!= null && cod_sucursal == null){
                listarU=unidadOpeService.listarAgencia(cod_agencia.getCod_agencia());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia== null && cod_sucursal != null){
                listarU=unidadOpeService.listarSucursal(cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }
        }else{
            listarU = unidadOpeService.listar();
            model.addAttribute("unidadesOpe", listarU);
        }
        String username=auth.getName();
        Usuario usuario = usuarioService.findByUsua(username);
        //Only admin "1" can register new users
        if(usuario.getId_rol().getId_rol()==1 || usuario.getId_rol().getId_rol()==2){
            model.addAttribute("cajaBoveda",new CajaBoveda());
            model.addAttribute("unidadesOpe", listarU);
            model.addAttribute("fc", new Date());

            model.addAttribute("cB","yes");
            return "menu";
        }else {
            return "redirect:/private/index";
        }
    }
    @PostMapping("/auth/saveCaja")
    public String saveCaja(CajaBoveda cajaBoveda,Authentication auth){
        String name = auth.getName();
        Usuario u = usuarioService.findByUsua(name);
        cajaBoveda.setId_usuario(u);
        cajaBovedaService.save(cajaBoveda);
        return "redirect:/auth/cajaBoveda";
    }
    //REPORT PDF
    @GetMapping("/auth/report/{id}")
    public void generar(@PathVariable Integer id, HttpServletResponse response) throws IOException, JRException {
        Compra c = compraService.getById(id);
          compraService.exportReport(c,response);
    }
    //AlMACEN
    @GetMapping("/auth/almacen")
    public String almacen(Model model,Authentication auth){
        Usuario byUsua = usuarioService.findByUsua(auth.getName());
        List<UnidadOperativa> listarU =new ArrayList<>();
        //FILTRADO POR LOCALIDAD EN SUPERVISOR Y OPERADOR
        if(byUsua.getId_rol().getId_rol()==3||byUsua.getId_rol().getId_rol()==2){

            Agencia cod_agencia = byUsua.getCod_uniOpe().getCod_agencia();
            Sucursal cod_sucursal = byUsua.getCod_uniOpe().getCod_sucursal();

            if (cod_agencia!= null && cod_sucursal != null){
                listarU = unidadOpeService.listarAgeSu(cod_agencia.getCod_agencia(),cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia!= null && cod_sucursal == null){
                listarU=unidadOpeService.listarAgencia(cod_agencia.getCod_agencia());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia== null && cod_sucursal != null){
                listarU=unidadOpeService.listarSucursal(cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }
        }else{
            listarU = unidadOpeService.listar();
            model.addAttribute("unidadesOpe", listarU);
        }

        if (byUsua.getId_rol().getId_rol()==2 ||byUsua.getId_rol().getId_rol()==1){
            List<TipoHojaCoca> tipoHojaCocas = tipoHcService.list();
            model.addAttribute("almacen","almacen");
            model.addAttribute("unidadesOpe",listarU);
            model.addAttribute("tiposHc",tipoHojaCocas);
            model.addAttribute("reporte",new Reporte());
            return "menu";
        }else {
            return "redirect:/private/index";
        }


    }

    //INGRESO POR DECOMISO
    @GetMapping("/auth/decomiso")
    public String decomiso(Model model,Authentication authentication){
        Usuario byUsua = usuarioService.findByUsua(authentication.getName());
        List<Decomiso> decomiso = decomisoService.numMoviento();
        List<UnidadOperativa> listarU;
        List<TipoHojaCoca> tipoHojaCocas = tipoHcService.list();

        Integer num;
        if(decomiso.size()==0){
            num=1;
        }else {
            Decomiso d = decomiso.get(0);
            num=d.getId_decomiso()+1;
        }
        //FILTRADO POR LOCALIDAD EN SUPERVISOR Y OPERADOR
        if(byUsua.getId_rol().getId_rol()==3||byUsua.getId_rol().getId_rol()==2){

            Agencia cod_agencia = byUsua.getCod_uniOpe().getCod_agencia();
            Sucursal cod_sucursal = byUsua.getCod_uniOpe().getCod_sucursal();

            if (cod_agencia!= null && cod_sucursal != null){
                listarU = unidadOpeService.listarAgeSu(cod_agencia.getCod_agencia(),cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia!= null && cod_sucursal == null){
                listarU=unidadOpeService.listarAgencia(cod_agencia.getCod_agencia());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia== null && cod_sucursal != null){
                listarU=unidadOpeService.listarSucursal(cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }
        }else{
            listarU = unidadOpeService.listar();
            model.addAttribute("unidadesOpe", listarU);
        }
        model.addAttribute("decom","decomiso");
        model.addAttribute("decomiso",new Decomiso());
        model.addAttribute("fc", new Date());
        model.addAttribute("tiposHc",tipoHojaCocas);
        model.addAttribute("num_Movimiento",num);
        return "menu";
    }
    @PostMapping("/auth/saveDecomiso")
    public String saveDecomiso(Decomiso decomiso,Authentication auth){
        Usuario u = usuarioService.findByUsua(auth.getName());
        decomiso.setId_usuario(u);
        decomisoService.save(decomiso);
        return "redirect:/auth/decomiso";
    }
    @GetMapping("/auth/merma")
    public String merma(Model model,Authentication authentication){
        List<Merma> merma = mermaService.numMovimiento();
        List<UnidadOperativa> listarU;
        List<TipoHojaCoca> tipoHojaCocas = tipoHcService.list();
        Usuario byUsua = usuarioService.findByUsua(authentication.getName());
        Integer num;
        if(merma.size()==0){
            num=1;
        }else {
            Merma m = merma.get(0);
            num=m.getId_merma()+1;
        }
        //FILTRADO POR LOCALIDAD EN SUPERVISOR Y OPERADOR
        if(byUsua.getId_rol().getId_rol()==3||byUsua.getId_rol().getId_rol()==2){

            Agencia cod_agencia = byUsua.getCod_uniOpe().getCod_agencia();
            Sucursal cod_sucursal = byUsua.getCod_uniOpe().getCod_sucursal();

            if (cod_agencia!= null && cod_sucursal != null){
                listarU = unidadOpeService.listarAgeSu(cod_agencia.getCod_agencia(),cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia!= null && cod_sucursal == null){
                listarU=unidadOpeService.listarAgencia(cod_agencia.getCod_agencia());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia== null && cod_sucursal != null){
                listarU=unidadOpeService.listarSucursal(cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }
        }else{
            listarU = unidadOpeService.listar();
            model.addAttribute("unidadesOpe", listarU);
        }
        model.addAttribute("merm","merma");
        model.addAttribute("merma",new Merma());
        model.addAttribute("fc", new Date());
        model.addAttribute("tiposHc",tipoHojaCocas);
        model.addAttribute("num_Movimiento",num);
        return "menu";
    }
    @PostMapping("/auth/saveMerma")
    public String saveMerma(Merma merma,Authentication auth,Model model){
        UnidadOperativa cod_uniOpe = merma.getCod_uniOpe();
        TipoHojaCoca cod_tipoHoja = merma.getCod_tipoHoja();
        List<Inventario> inv = inventarioService.listByProductAlmacenOne(cod_tipoHoja, cod_uniOpe);
        Inventario inventario = inv.get(0);
        //STOCK MAYOR A cantidad de MERMA
        if (inventario.getStockFinal()<merma.getCantidadNeta()){
            List<Merma> merm = mermaService.numMovimiento();
            List<UnidadOperativa> listarU = unidadOpeService.listar();
            List<TipoHojaCoca> tipoHojaCocas = tipoHcService.list();

            Integer num;
            if(merm.size()==0){
                num=1;
            }else {
                Merma m = merm.get(0);
                num=m.getId_merma()+1;
            }
            model.addAttribute("merm","merma");
            model.addAttribute("msg","CANTIDAD DE STOCK MENOR A CANTIDAD DE MERMA");
            model.addAttribute("merma",new Merma());
            model.addAttribute("fc", new Date());
            model.addAttribute("unidadesOpe", listarU);
            model.addAttribute("tiposHc",tipoHojaCocas);
            model.addAttribute("num_Movimiento",num);
        }else {
            Usuario u = usuarioService.findByUsua(auth.getName());
            merma.setId_usuario(u);
            mermaService.save(merma);
        }
        return "menu";
    }
    //TRANSFERENCIA HOJA DE COCA
    @GetMapping("/auth/transferencia")
    public String transferencia(Model model,Authentication authentication){
        Usuario byUsua = usuarioService.findByUsua(authentication.getName());
        List<UnidadOperativa> listarU = new ArrayList<>();
        List<TipoHojaCoca> tipoHojaCocas = tipoHcService.list();
        //FILTRADO POR LOCALIDAD EN SUPERVISOR Y OPERADOR
        if(byUsua.getId_rol().getId_rol()==3||byUsua.getId_rol().getId_rol()==2){

            Agencia cod_agencia = byUsua.getCod_uniOpe().getCod_agencia();
            Sucursal cod_sucursal = byUsua.getCod_uniOpe().getCod_sucursal();

            if (cod_agencia!= null && cod_sucursal != null){
                listarU = unidadOpeService.listarAgeSu(cod_agencia.getCod_agencia(),cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia!= null && cod_sucursal == null){
                listarU=unidadOpeService.listarAgencia(cod_agencia.getCod_agencia());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia== null && cod_sucursal != null){
                listarU=unidadOpeService.listarSucursal(cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }
        }else{
            listarU = unidadOpeService.listar();
            model.addAttribute("unidadesOpe", listarU);
        }

        model.addAttribute("transf","transferencia");
        model.addAttribute("transferencia",new Transferencia());
        model.addAttribute("fc", new Date());
        model.addAttribute("unidadesOpe", listarU);
        model.addAttribute("tiposHc",tipoHojaCocas);
        return "menu";
    }
    @PostMapping("/auth/saveTransferencia")
    public String saveTransferencia(Transferencia transferencia,Authentication auth,Model model){
            UnidadOperativa cod_uniOpe = transferencia.getOrigen();
            TipoHojaCoca cod_tipoHoja = transferencia.getCod_tipoHoja();
            List<Inventario> inv = inventarioService.listByProductAlmacenOne(cod_tipoHoja, cod_uniOpe);
            Inventario inventario = inv.get(0);
            if(inventario.getStockFinal()<transferencia.getCantidad()){
                List<UnidadOperativa> listarU = unidadOpeService.listar();
                List<TipoHojaCoca> tipoHojaCocas = tipoHcService.list();

                model.addAttribute("msg","Cantidad superior al Stock de Origen");
                model.addAttribute("transf","transferencia");
                model.addAttribute("transferencia",new Transferencia());
                model.addAttribute("fc", new Date());
                model.addAttribute("unidadesOpe", listarU);
                model.addAttribute("tiposHc",tipoHojaCocas);
            }else {
                Usuario u = usuarioService.findByUsua(auth.getName());
                transferencia.setId_usuario(u);
                transferenciaService.save(transferencia);
            }
        return "menu";

    }
    //INGRESO POR DEMASIA
    @GetMapping("/auth/demasia")
    public String demasia(Model model,Authentication authentication){
        Usuario byUsua = usuarioService.findByUsua(authentication.getName());
        List<Demasia> demasia = demasiaService.numMovimiento();
        List<UnidadOperativa> listarU;
        List<TipoHojaCoca> tipoHojaCocas = tipoHcService.list();

        Integer num;
        if(demasia.size()==0){
            num=1;
        }else {
            Demasia d = demasia.get(0);
            num=d.getId_demasia()+1;
        }
        //FILTRADO POR LOCALIDAD EN SUPERVISOR Y OPERADOR
        if(byUsua.getId_rol().getId_rol()==3||byUsua.getId_rol().getId_rol()==2){

            Agencia cod_agencia = byUsua.getCod_uniOpe().getCod_agencia();
            Sucursal cod_sucursal = byUsua.getCod_uniOpe().getCod_sucursal();

            if (cod_agencia!= null && cod_sucursal != null){
                listarU = unidadOpeService.listarAgeSu(cod_agencia.getCod_agencia(),cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia!= null && cod_sucursal == null){
                listarU=unidadOpeService.listarAgencia(cod_agencia.getCod_agencia());
                model.addAttribute("unidadesOpe", listarU);
            }else if (cod_agencia== null && cod_sucursal != null){
                listarU=unidadOpeService.listarSucursal(cod_sucursal.getCod_sucursal());
                model.addAttribute("unidadesOpe", listarU);
            }
        }else{
            listarU = unidadOpeService.listar();
            model.addAttribute("unidadesOpe", listarU);
        }
        model.addAttribute("demas","demasia");
        model.addAttribute("demasia",new Demasia());
        model.addAttribute("fc", new Date());
        model.addAttribute("tiposHc",tipoHojaCocas);
        model.addAttribute("num_Movimiento",num);
        return "menu";
    }
    @PostMapping("/auth/saveDemasia")
    public String saveDemasia(Demasia demasia,Authentication auth){
        Usuario u = usuarioService.findByUsua(auth.getName());
        demasia.setId_usuario(u);
        demasiaService.save(demasia);
        return "menu";
    }

    //REPORTES
    @PostMapping("/download/excel")
    public void downloadCsv(Reporte reporte,HttpServletResponse response) throws IOException, ParseException {
        if (reporte.getCodRep()==1){
            List<Inventario> inventarios= new ArrayList<>();
            if (reporte.getCodHc()==null && reporte.getFcInicio()=="" && reporte.getFcFin()=="" && reporte.getCodUni()!=null){
                inventarios =inventarioService.listByUni(reporte.getCodUni().getCod_uniOpe());
            }else if (reporte.getCodHc()==null && reporte.getFcInicio()!="" && reporte.getFcFin()!="" && reporte.getCodUni()!=null){
                inventarios =inventarioService.registrosFechaAlmacen(reporte.getFcInicio(),reporte.getFcFin(),reporte.getCodUni().getCod_uniOpe());
            }else if (reporte.getCodHc()!=null && reporte.getFcInicio()=="" && reporte.getFcFin()=="" && reporte.getCodUni()!=null){
                inventarios=inventarioService.listByProductAlmacen(reporte.getCodHc().getCod_tipoHoja(),reporte.getCodUni().getCod_uniOpe());
            }else if (reporte.getCodHc()!=null && reporte.getFcInicio()!="" && reporte.getFcFin()!="" && reporte.getCodUni()!=null){
                inventarios=inventarioService.registrosFechaAlmacenHc(reporte.getFcInicio(),reporte.getFcFin(),reporte.getCodUni().getCod_uniOpe(),reporte.getCodHc().getCod_tipoHoja());
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=KARDEX.xlsx");
            ByteArrayInputStream stream = ExportExcelKardex.listToExcelFile(inventarios,reporte);
            IOUtils.copy(stream, response.getOutputStream());
        }else if (reporte.getCodRep()==2){
        }else if (reporte.getCodRep()==3){
        }else if (reporte.getCodRep()==4){
        }else if (reporte.getCodRep()==5){
        }else if (reporte.getCodRep()==6){
        }else if (reporte.getCodRep()==7){
        }else if (reporte.getCodRep()==8){
        }else if (reporte.getCodRep()==9){
        }else if (reporte.getCodRep()==10){
            List<ActaRegistro> actaRegistros=new ArrayList<>();

            List<TipoHojaCoca> tipoHojaCocas = inventarioService.actaHojas(reporte.getFcInicio(), reporte.getFcFin(), reporte.getCodUni().getCod_uniOpe());//HOJAS DE COCA
            List<Ingreso> ingresos;
            List<IngresoSalida> ingresoSalidas;
            for (int i=0;i<tipoHojaCocas.size();i++){
                Double s = inventarioService.actaSaldo(reporte.getFcInicio(), reporte.getFcFin(), reporte.getCodUni().getCod_uniOpe(), tipoHojaCocas.get(i).getCod_tipoHoja());
                ingresos= inventarioService.actaIngreso(reporte.getFcInicio(), reporte.getFcFin(), reporte.getCodUni().getCod_uniOpe(), tipoHojaCocas.get(i).getCod_tipoHoja());//INGRESOS(1,2,3) SALIDA(4)
                ingresoSalidas= inventarioService.actaIngresoSalida(reporte.getFcInicio(), reporte.getFcFin(), reporte.getCodUni().getCod_uniOpe(), tipoHojaCocas.get(i).getCod_tipoHoja());
                    ActaRegistro actaRegistro= new ActaRegistro();
                        actaRegistro.setCodHc(tipoHojaCocas.get(i).getCod_tipoHoja());//CODHC
                        actaRegistro.setSaldoMesAnterior(s);//SALDO
                        actaRegistro.setIngresoCompra(ingresos.get(0).getMonto());//COMPRAS
                        actaRegistro.setIngresoDecomiso(ingresos.get(1).getMonto());//DECOMISO
                        actaRegistro.setIngresoDemasia(ingresos.get(2).getMonto());//DEMASIA
                        actaRegistro.setSalidaMerma(ingresos.get(3).getMonto());//SALIDA MERMA

                        actaRegistro.setIngresoTransferencia(ingresoSalidas.get(0).getMonto());//INGRESO TRANSF
                        actaRegistro.setSalidaTransferencia(ingresoSalidas.get(1).getMonto());//SALIDA TRANSF

                    actaRegistros.add(actaRegistro);
            }
        }
    }

    @PostMapping("/reportActaInventario")
    private String reporteActaInventario(Reporte reporte) {
        return "menu";
    }

}
