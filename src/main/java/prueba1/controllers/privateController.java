package prueba1.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prueba1.Service.UsuarioService;
import prueba1.models.Usuario;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/private")
public class privateController {
    @Autowired
    private final UsuarioService usuarioService;

    public privateController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/index")
    public String index(Authentication auth, HttpSession session){
        String username =auth.getName();
        if(session.getAttribute("usuario")==null){
            Usuario usuario = usuarioService.findByUsua(username);
            session.setAttribute("usuario",usuario);
        }

        return "index";
    }
}
