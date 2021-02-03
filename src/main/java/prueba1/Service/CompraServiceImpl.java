package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.Compra;
import prueba1.models.Usuario;
import prueba1.repository.CompraRepository;
import prueba1.repository.UsuarioRepository;

import javax.persistence.criteria.CriteriaBuilder;

@Service
public class CompraServiceImpl implements CompraService{

    @Autowired
    private final CompraRepository compraRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public CompraServiceImpl(CompraRepository compraRepository, UsuarioRepository usuarioRepository) {
        this.compraRepository = compraRepository;
        this.usuarioRepository = usuarioRepository;
    }


}
