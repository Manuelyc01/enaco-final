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

    @Override
    public void newCompra(Integer id){
        Compra compra= new Compra();
        Usuario u=usuarioRepository.getOne(id);
        String n=u.getNum_compras().toString();
        int num = n.length();

        switch (num){
            case 1:
                compra.setNum_compra(u.getSerie_compra()+"00000"+u.getNum_compras().toString());
                break;
            case 2:
                compra.setNum_compra(u.getSerie_compra()+"0000"+u.getNum_compras().toString());
                break;
            case 3:
                compra.setNum_compra(u.getSerie_compra()+"000"+u.getNum_compras().toString());
                break;
            case 4:
                compra.setNum_compra(u.getSerie_compra()+"00"+u.getNum_compras().toString());
                break;
            case 5:
                compra.setNum_compra(u.getSerie_compra()+"0"+u.getNum_compras().toString());
                break;
            case 6:
                compra.setNum_compra(u.getSerie_compra()+u.getNum_compras().toString());
        }
        compraRepository.save(compra);
    };
}
