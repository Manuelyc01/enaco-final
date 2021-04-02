package prueba1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import prueba1.models.CajaBoveda;
import prueba1.models.Demasia;
import prueba1.models.UnidadOperativa;
import prueba1.repository.CajaBovedaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        List<CajaBoveda> stockFinal = repository.getStockFinal(cajaBoveda.getCod_uniOpe().getCod_uniOpe(), PageRequest.of(0, 1));
        switch (id_tipo){
            case 1://INGRESO
                cajaBoveda.setSaldoInicial(stockFinal.get(0).getSaldoFinal());
                cajaBoveda.setSaldoFinal(monto+stockFinal.get(0).getSaldoFinal());
                break;
            case 2://REEMBOLSO
                cajaBoveda.setSaldoInicial(stockFinal.get(0).getSaldoFinal());
                cajaBoveda.setSaldoFinal(monto-stockFinal.get(0).getSaldoFinal());
                break;
            case 3://COMPRA
                cajaBoveda.setSaldoInicial(stockFinal.get(0).getSaldoFinal());
                cajaBoveda.setSaldoFinal(monto-stockFinal.get(0).getSaldoFinal());
        }

        repository.save(cajaBoveda);
    }

    @Override
    public List<CajaBoveda> listByUni(String cod){
        return repository.lisByUni(cod);
    }
    @Override
    public List<CajaBoveda> registrosFechaCajaBoveda(String inicio, String fin, String cod) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date ini= format.parse(inicio.replace("T"," "));
        Date fn= format.parse(fin.replace("T"," "));
        return repository.filterFechaCajaBovedaHc(ini,fn,cod);
    }

}
