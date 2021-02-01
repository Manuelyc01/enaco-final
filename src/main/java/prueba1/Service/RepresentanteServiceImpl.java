package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.Representante;
import prueba1.repository.RepresentanteRepository;

@Service
public class RepresentanteServiceImpl implements RepresentanteService {
    @Autowired
    private final RepresentanteRepository repository;

    public RepresentanteServiceImpl(RepresentanteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Representante findByDni(String dni){
        return repository.findByDni(dni);
    }
}
