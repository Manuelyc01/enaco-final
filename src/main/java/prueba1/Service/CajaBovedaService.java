package prueba1.Service;

import prueba1.models.CajaBoveda;

import java.text.ParseException;
import java.util.List;

public interface CajaBovedaService {
    List<CajaBoveda> list();

    void save(CajaBoveda cajaBoveda);

    List<CajaBoveda> listByUni(String cod);

    List<CajaBoveda> listByUniT(String cod, Integer t);

    List<CajaBoveda> registrosFechaCajaBoveda(String inicio, String fin, String cod) throws ParseException;

    List<CajaBoveda> registrosFechaCajaBovedaT(String inicio, String fin, String cod, Integer t) throws ParseException;
}
