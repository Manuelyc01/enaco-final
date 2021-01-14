package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import prueba1.models.Estado;
import prueba1.models.Usuario;
import prueba1.repository.UsuarioRepository;

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

}
