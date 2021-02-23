package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba1.models.CajaBoveda;
import prueba1.models.UnidadOperativa;
import prueba1.repository.CajaBovedaRepository;

import java.util.Date;
import java.util.List;

@Service
public class CajaBovedaServiceImpl implements CajaBovedaService{
    @Autowired
    private final CajaBovedaRepository repository;
    @Autowired
    private final UnidadOpeService unidadOpeService;

    public CajaBovedaServiceImpl(CajaBovedaRepository repository, UnidadOpeService unidadOpeService) {
        this.repository = repository;
        this.unidadOpeService = unidadOpeService;
    }

    @Override
    public List<CajaBoveda> list(){
        return repository.list();
    }

    @Override
    public void save(CajaBoveda cajaBoveda){
        UnidadOperativa uo = unidadOpeService.findByCod(cajaBoveda.getCod_uniOpe().getCod_uniOpe());//UNIDAD
        Integer id_tipo = cajaBoveda.getId_tipoTransac().getId_tipoTransac();//Tipo transac
        Double monto = cajaBoveda.getMonto();
        unidadOpeService.saveCajaBoveda(uo.getCod_uniOpe(),monto,id_tipo);
        //DIA
        cajaBoveda.setFecha(new Date());
        repository.save(cajaBoveda);
    }
}
