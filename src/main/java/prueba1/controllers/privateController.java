package prueba1.controllers;

import net.sf.jasperreports.engine.JRException;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prueba1.Service.*;
import prueba1.models.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    private final EstadoService estadoService;
    public privateController(UsuarioService usuarioService, UnidadOpeService unidadOpeService, ProductorService productorService, CostoHcService costoHcService, SucursalService sucursalService, AgenciaService agenciaService, CompraService compraService, TipoHcService tipoHcService, TipoTransacService tipoTransacService, CajaBovedaService cajaBovedaService, RepresentanteService representanteService, EstadoService estadoService) {
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
            List<CostoHojaCoca> costoHojaCocas = costoHcService.list();
            List<UnidadOperativa> listarU = unidadOpeService.listar();

            model.addAttribute("c","yes");

            model.addAttribute("unidadesOpe", listarU);
            model.addAttribute("tiposHc",costoHojaCocas);

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
    public String comprar(Compra compra,Authentication auth,Model model){
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
            String name = auth.getName();
            Usuario u = usuarioService.findByUsua(name);
            compra.setId_usuario(u);
            compraService.save(u.getId_usuario(),compra);
            return "redirect:/auth/listCompras";
        }
    }

    //CAJA BOVEDA
    @GetMapping("/auth/cajaBoveda")
    public String cajaBoveda(Model model){
        List<UnidadOperativa> listarU = unidadOpeService.listar();
        List<TipoTransaccion> listTransac = tipoTransacService.list();


        model.addAttribute("cajaBoveda",new CajaBoveda());
        model.addAttribute("unidadesOpe", listarU);
        model.addAttribute("listTransac", listTransac);
        model.addAttribute("fc", new Date());

        model.addAttribute("cB","yes");

        return "menu";
    }
    @PostMapping("/auth/saveCaja")
    public String saveCaja(CajaBoveda cajaBoveda,Authentication auth){
        String name = auth.getName();
        Usuario u = usuarioService.findByUsua(name);
        cajaBoveda.setId_usuario(u);
        cajaBovedaService.save(cajaBoveda);
        return "menu";
    }
    //REPORT PDF
    @GetMapping("/auth/report/{id}")
    public void generar(@PathVariable Integer id, HttpServletResponse response) throws IOException, JRException {
        Compra c = compraService.getById(id);
          compraService.exportReport(c,response);
    }

}
