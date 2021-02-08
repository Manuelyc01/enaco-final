package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.TipoTransaccion;
import prueba1.repository.TipoTransacRepository;

import java.util.List;

@Service
public class TipoTransacServiceImpl implements TipoTransacService{
    @Autowired
    private final TipoTransacRepository repository;

    public TipoTransacServiceImpl(TipoTransacRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TipoTransaccion> list(){
        return repository.findAll();
    }
}
