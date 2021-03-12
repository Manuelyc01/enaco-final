package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.TipoHojaCoca;
import prueba1.repository.TipoHcRepository;

import java.util.List;

@Service
public class TipoHcServiceImpl implements TipoHcService {

    @Autowired
    private final TipoHcRepository repository;

    public TipoHcServiceImpl(TipoHcRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TipoHojaCoca> list(){
        return repository.findAll();
    }

}
