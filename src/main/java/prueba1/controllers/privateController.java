package prueba1.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prueba1.Service.EstadoService;
import prueba1.Service.UnidadOpeService;
import prueba1.Service.UsuarioService;
import prueba1.models.UnidadOperativa;
import prueba1.models.Usuario;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = {"/private","" })
public class privateController {

    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final UnidadOpeService unidadOpeService;

    @Autowired
    private final EstadoService estadoService;
    public privateController(UsuarioService usuarioService, UnidadOpeService unidadOpeService, EstadoService estadoService) {
        this.usuarioService = usuarioService;
        this.unidadOpeService = unidadOpeService;
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
            model.addAttribute("r", 1);
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
            model.addAttribute("user",usuario);
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
        model.addAttribute("listP","yes");
        return "menu";
    }
    //LISTAR COSTO HOJA COCA
    @GetMapping("/auth/listCostoHC")
    public String listarCostoHC(Model model){
        model.addAttribute("listC","yes");
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


}
