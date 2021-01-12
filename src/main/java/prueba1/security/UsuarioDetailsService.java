package prueba1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import prueba1.models.Usuario;
import prueba1.repository.UsuarioRepository;

public class UsuarioDetailsService implements UserDetailsService {
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario user= usuarioRepository.findByUsuario(usuario);
        User.UserBuilder builder=null;

        if(user != null){
            builder = User.withUsername(usuario);
            builder.disabled(false);
            builder.password(user.getPassw());
            builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
        }else {
            throw new UsernameNotFoundException("Usuario no encontrado");

        }
        return builder.build();
    }
}
