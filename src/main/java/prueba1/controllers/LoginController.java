package prueba1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import prueba1.Service.UsuarioService;
import prueba1.models.Usuario;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/auth/login")
    public String login(Model model, HttpSession session){

        if(session.getAttribute("usuario")!=null){
            return "redirect:/private/index";
        }else{
            model.addAttribute("usuario",new Usuario());
            return "login";
        }
    }

}
