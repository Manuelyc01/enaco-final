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
                u.setCajaBoveda(u.getCajaBoveda()-m);
            }
        }
        repository.save(u);
    }
}
