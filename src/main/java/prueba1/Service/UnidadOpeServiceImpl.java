package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.UnidadOperativa;
import prueba1.repository.UnidadOperativaRepository;

import java.util.List;

@Service
public class UnidadOpeServiceImpl   implements UnidadOpeService {

    @Autowired
    private final UnidadOperativaRepository repository;

    public UnidadOpeServiceImpl(UnidadOperativaRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<UnidadOperativa> listar(){
        return repository.findAll();
    }
    @Override
    public UnidadOperativa findByCod(String cod){
        return repository.getOne(cod);
    }
    @Override
    public void save(UnidadOperativa unidadOperativa){
        repository.save(unidadOperativa);
    }
    @Override
    public void saveCajaBoveda(String cod,double monto,Integer tipo){
        UnidadOperativa u = repository.getOne(cod);
        Double m=Math.round(monto * 100.0) / 100.0;
        if(tipo ==1){
            if(u.getCajaBoveda()==null){
                u.setCajaBoveda(+m);
            } else if (u.getCajaBoveda() >= 0){
                u.setCajaBoveda(u.getCajaBoveda()+m);
            }
        }else if (tipo==2){
            if(u.getCajaBoveda()>0){
                u.setCajaBoveda(Math.round((u.getCajaBoveda()-m) * 100.0) / 100.0);
            }
        }
        repository.save(u);
    }
    //FILTRADO
    @Override
    public List<UnidadOperativa> listarAgeSu(String cod_agencia, String cod_sucursal){
        return repository.listarAgeSu(cod_agencia,cod_sucursal);
    }
    @Override
    public List<UnidadOperativa> listarAgencia(String cod_agencia){
        return repository.listarAgencia(cod_agencia);
    }
    @Override
    public List<UnidadOperativa> listarSucursal(String cod_sucursal){
        return repository.listarSucursal(cod_sucursal);
    }
}
