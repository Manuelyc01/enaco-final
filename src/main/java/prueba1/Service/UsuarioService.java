package prueba1.Service;

import prueba1.models.Usuario;

import java.util.List;

public interface UsuarioService {
    public Usuario findByUsua(String usua);

    public Usuario registrar(Usuario usuario);

    List<Usuario> listar();

    void eliminar(Integer id_usuario);

    Usuario findById(Integer id);


    void update(Integer id, Usuario usuario);

    void compra(Integer id);

}
