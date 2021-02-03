package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.CostoHojaCoca;
import prueba1.models.UnidadOperativa;
import prueba1.repository.CostoHcRepository;

import java.util.List;

@Service
public class CostoHcServiceImpl implements CostoHcService{

    @Autowired
    private final CostoHcRepository repository;

    public CostoHcServiceImpl(CostoHcRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CostoHojaCoca> list(){
        return repository.findAll();
    }

    @Override
    public List<CostoHojaCoca> filterCostoHc(UnidadOperativa cod_uniOpe){
        return repository.filterTipoHc(cod_uniOpe);
    }
}
