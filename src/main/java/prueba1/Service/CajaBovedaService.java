package prueba1.Service;

import prueba1.models.CajaBoveda;

import java.util.List;

public interface CajaBovedaService {
    List<CajaBoveda> list();

    void save(CajaBoveda cajaBoveda);
}
