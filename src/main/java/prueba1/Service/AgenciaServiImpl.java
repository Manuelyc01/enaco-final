package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.Agencia;
import prueba1.repository.AgenciaRepository;

import java.util.List;

@Service
public class AgenciaServiImpl implements AgenciaService {

    @Autowired
    private final AgenciaRepository repository;

    public AgenciaServiImpl(AgenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Agencia> list(){
        return repository.findAll();
    }
}
