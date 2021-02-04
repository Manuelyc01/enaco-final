package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.Compra;
import prueba1.models.Usuario;
import prueba1.repository.CompraRepository;
import prueba1.repository.UsuarioRepository;

import java.util.Date;

@Service
public class CompraServiceImpl implements CompraService{

    @Autowired
    private final CompraRepository compraRepository;
    @Autowired
    private final UsuarioService usuarioService;

    public CompraServiceImpl(CompraRepository compraRepository, UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.compraRepository = compraRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public void save(Integer id, Compra compra){
        //OBTENER NUM COMPRAS DE USUARIO
        Usuario u = usuarioService.findById(id);
        Integer num;
        if (u.getNum_compras()!=null){
            num=u.getNum_compras()+1;
            u.setNum_compras(num);
            usuarioService.registrar(u);
        }else {
            num=1;
            u.setNum_compras(num);
            usuarioService.registrar(u);
        }
        int n=num.toString().length();//TAMAÑO DEL NUMERO
        switch (n)//CANTIDAD DE DIGITOS (6)
        {
            case 1:
                compra.setNum_liquidacion(u.getSerie_compra()+"00000"+num.toString());
                break;
            case 2:
                compra.setNum_liquidacion(u.getSerie_compra()+"0000"+num.toString());
                break;
            case 3:
                compra.setNum_liquidacion(u.getSerie_compra()+"000"+num.toString());
                break;
            case 4:
                compra.setNum_liquidacion(u.getSerie_compra()+"00"+num.toString());
                break;
            case 5:
                compra.setNum_liquidacion(u.getSerie_compra()+"0"+num.toString());
                break;
            case 6:
                compra.setNum_liquidacion(u.getSerie_compra()+num.toString());

        }
        //ENVIO FECHA HOY
        Date date=new Date();
        compra.setFecha(date);
        compraRepository.save(compra);
    }

}
