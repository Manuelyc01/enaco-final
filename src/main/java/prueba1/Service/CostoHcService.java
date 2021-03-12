package prueba1.Service;

import prueba1.models.CostoHojaCoca;
import prueba1.models.UnidadOperativa;

import java.util.List;

public interface CostoHcService {
    List<CostoHojaCoca> list();



    List<CostoHojaCoca> filterCostoHc(String cod_uniOpe);
}
