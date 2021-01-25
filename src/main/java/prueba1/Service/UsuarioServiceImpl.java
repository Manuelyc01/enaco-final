package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import prueba1.models.Estado;
import prueba1.models.Rol;
import prueba1.models.Usuario;
import prueba1.repository.UsuarioRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario findByUsua(String usua) {
        return usuarioRepository.findByUsua(usua);
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        usuario.setPassw(bCryptPasswordEncoder.encode(usuario.getPassw()));
        return usuarioRepository.save(usuario);
    }
    @Override
    public List<Usuario> listar(){
        List<Usuario> list = usuarioRepository.findAll();
        return list;
    }
    @Override
    public void eliminar(Integer id_usuario){
        usuarioRepository.deleteById(id_usuario);
    }
    @Override
    public Usuario findById(Integer id){
        return  usuarioRepository.getOne(id);
    }
    @Override
    public void update(Integer id, Usuario usuario){
        Usuario u1 = usuarioRepository.getOne(id);
        String s= "";
        u1.setNombre(usuario.getNombre());
        u1.setTelefono(usuario.getTelefono());
        u1.setCorreo(usuario.getCorreo());
        u1.setId_rol(usuario.getId_rol());
        u1.setCod_uniOpe(usuario.getCod_uniOpe());
        u1.setUsua(usuario.getUsua());
        if (usuario.getPassw()!= s){
            u1.setPassw(bCryptPasswordEncoder.encode(usuario.getPassw()));
        }
        u1.setId_estado(usuario.getId_estado());
        u1.setSerie_compra(usuario.getSerie_compra());

        usuarioRepository.save(u1);

    }
}
