package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.Movimiento;
import prueba1.repository.MovimientoRepository;

import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService{

    @Autowired
    private final MovimientoRepository repository;

    public MovimientoServiceImpl(MovimientoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Movimiento> list(){
        return repository.findAll();
    }

    @Override
    public Movimiento findById(Integer id){
        return repository.getOne(id);
    }

}
