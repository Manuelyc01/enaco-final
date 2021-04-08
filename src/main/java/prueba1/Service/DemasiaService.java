package prueba1.Service;

import prueba1.models.Demasia;

import java.text.ParseException;
import java.util.List;

public interface DemasiaService {
    void save(Demasia demasia);

    List<Demasia> numMovimiento();

    List<Demasia> listByUni(String cod);

    List<Demasia> registrosFechaDemasia(String inicio, String fin, String cod) throws ParseException;

    List<Demasia> listByProductDemasia(String cod_tipoHoja, String cod_uniOpe);

    List<Demasia> registrosFechaDemasiaHc(String inicio, String fin, String cod, String codHc) throws ParseException;
}
