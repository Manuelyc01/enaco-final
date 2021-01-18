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
}
