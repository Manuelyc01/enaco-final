package prueba1.Service;

import prueba1.models.CajaBoveda;

import java.text.ParseException;
import java.util.List;

public interface CajaBovedaService {
    List<CajaBoveda> list();

    void save(CajaBoveda cajaBoveda);

    List<CajaBoveda> listByUni(String cod);

    List<CajaBoveda> registrosFechaCajaBoveda(String inicio, String fin, String cod) throws ParseException;

   }
