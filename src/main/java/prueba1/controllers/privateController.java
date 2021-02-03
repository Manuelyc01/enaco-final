package prueba1.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prueba1.Service.*;
import prueba1.models.*;

import javax.servlet.http.HttpSession;
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
    private final EstadoService estadoService;
    public privateController(UsuarioService usuarioService, UnidadOpeService unidadOpeService, ProductorService productorService, CostoHcService costoHcService, SucursalService sucursalService, AgenciaService agenciaService, CompraService compraService, TipoHcService tipoHcService, EstadoService estadoService) {
        this.usuarioService = usuarioService;
        this.unidadOpeService = unidadOpeService;
        this.productorService = productorService;
        this.costoHcService = costoHcService;
        this.sucursalService = sucursalService;
        this.agenciaService = agenciaService;
        this.compraService = compraService;
        this.tipoHcService = tipoHcService;
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
    public String registro(Usuario usuario,Model model){
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
    }

    //LISTAR USUARIO
    @GetMapping("/auth/listaUsuarios")
    public String listarUsuario(Model model){

        List<Usuario> usuarios = usuarioService.listar();
        model.addAttribute("list","yes");
        model.addAttribute("usuarios",usuarios);
        return "menu";
    }
    //LISTAR PRODUCTOR
    @GetMapping("/auth/listProductor")
    public String listarProductor(Model model){
        List<Productor> list = productorService.list();
        model.addAttribute("listP","yes");
        model.addAttribute("productores",list);
        return "menu";
    }
    //LISTAR COSTO HOJA COCA
    @GetMapping("/auth/listCostoHC")
    public String listarCostoHC(Model model){
        List<CostoHojaCoca> list = costoHcService.list();
        model.addAttribute("listC",list);
        return "menu";
    }
    //LISTAR SUCURSAL
    @GetMapping("/auth/listSucursal")
    public String listarSucursal(Model model){
        List<Sucursal> list = sucursalService.list();
        model.addAttribute("listS",list);
        return "menu";
    }
    //LISTAR AGENCIA
    @GetMapping("/auth/listAgencia")
    public String listarAgencia(Model model){
        List<Agencia> list = agenciaService.list();
        model.addAttribute("listA",list);
        return "menu";
    }
    //LISTAR UNIDAD
    @GetMapping("/auth/listUnidadOpe")
    public String listarUnidadOpe(Model model){
        List<UnidadOperativa> listar = unidadOpeService.listar();
        model.addAttribute("listU",listar);
        return "menu";
    }

    //ELIMINAR USUARIO
    @GetMapping("/auth/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Integer id){
        usuarioService.eliminar(id);
        return "redirect:/auth/listaUsuarios";
    }

    //EDITAR USUARIO
    @GetMapping("/auth/editUsuario/{id}")
    public String editarUsuario(@PathVariable Integer id,Model model){
        Usuario u = usuarioService.findById(id);

        List<UnidadOperativa> listar = unidadOpeService.listar();
        model.addAttribute("user",u);
        model.addAttribute("e", 1);

        model.addAttribute("unidadesOpe", listar);
        return "menu";
    }
    @PostMapping("/auth/update/{id}")
    public String actualizarUsuario(@PathVariable Integer id,Usuario usuario){
        usuarioService.update(id,usuario);
        return "redirect:/auth/listaUsuarios";
    }
    @GetMapping("/auth/compraUsuario/{id}")
    public String compraUsuario(@PathVariable Integer id,Model model){
        Usuario u = usuarioService.findById(id);
        List<CostoHojaCoca> costoHojaCocas = costoHcService.filterCostoHc(u.getCod_uniOpe());
        List<UnidadOperativa> listarU = unidadOpeService.listar();

        model.addAttribute("c","yes");

        model.addAttribute("unidadesOpe", listarU);
        model.addAttribute("tiposHc",costoHojaCocas);

        model.addAttribute("compra",nuevaCompra(id));
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


        return compra;
    }


}
