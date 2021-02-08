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
    @Autowired
    private final UnidadOpeService unidadOpeService;

    public CompraServiceImpl(CompraRepository compraRepository, UsuarioRepository usuarioRepository, UsuarioService usuarioService, UnidadOpeService unidadOpeService) {
        this.compraRepository = compraRepository;
        this.usuarioService = usuarioService;
        this.unidadOpeService = unidadOpeService;
    }

    @Override
    public void save(Integer id, Compra compra){
        //OBTENER NUM COMPRAS DE USUARIO
        Usuario u = usuarioService.findById(id);
        Integer num=u.getNum_compras();
        if (num==null){
            num=+1;
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
        usuarioService.compra(id);
        unidadOpeService.saveCajaBoveda(compra.getCod_uniOpe().getCod_uniOpe(),compra.getTotalCompra(),2);
        compraRepository.save(compra);
    }

}
