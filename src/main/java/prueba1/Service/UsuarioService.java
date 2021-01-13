package prueba1.Service;

import prueba1.models.Usuario;

public interface UsuarioService {
    public Usuario findByUsua(String usua);

    public Usuario registrar(Usuario usuario);
}
