package prueba1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import prueba1.Service.UsuarioService;
import prueba1.models.Usuario;

@Controller
public class LoginController {

    @Autowired
    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/auth/login")
    public String login(Model model){
        model.addAttribute("usuario",new Usuario());
        return "login";
    }

    @GetMapping("/auth/registrar")
    public String registroForm(Model model){
        model.addAttribute("user", new Usuario());
        return "registro";
    }
    @GetMapping("/auth/auth/registrar")
    public String registrForm(Model model){
        model.addAttribute("user", new Usuario());
        return "registro";
    }

    @PostMapping("/auth/registro")
    public String registro(Usuario usuario){
        usuarioService.registrar(usuario);
        return "redirect:/auth/login";
    }
}
