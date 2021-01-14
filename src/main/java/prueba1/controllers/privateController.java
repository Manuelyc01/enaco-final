package prueba1.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prueba1.Service.EstadoService;
import prueba1.Service.UsuarioService;
import prueba1.models.Estado;
import prueba1.models.Usuario;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = {"/private","" })
public class privateController {
    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private final EstadoService estadoService;

    public privateController(UsuarioService usuarioService, EstadoService estadoService) {
        this.usuarioService = usuarioService;
        this.estadoService = estadoService;
    }

    @GetMapping("/index")
    public String index(Authentication auth, HttpSession session){
        String username =auth.getName();
        if(session.getAttribute("usuario")==null){
            Usuario usuario = usuarioService.findByUsua(username);
            session.setAttribute("usuario",usuario);
        }

        return "menu";
    }
    @GetMapping("/auth/registrar")
    public String registroForm(Model model, Authentication auth ){
        String username=auth.getName();
        Usuario usuario = usuarioService.findByUsua(username);
        //Only admin "1" can register new users
        if(usuario.getId_rol().getId_rol()==1){
            model.addAttribute("user", new Usuario());
            return "registro";
        }else{
            return "redirect:/private/index";
        }
    }

    @PostMapping("/auth/registro")
    public String registro(Usuario usuario){
        String user= usuario.getUsua();
        if(usuarioService.findByUsua(user)!=null){
            //SI EL USUARIO EXISTE
            return "redirect:/auth/registrar";
        }else {
            usuarioService.registrar(usuario);
            return "redirect:/private/index";
        }

    }
}
