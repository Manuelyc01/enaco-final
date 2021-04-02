package prueba1.Service;

import prueba1.models.Demasia;

import java.text.ParseException;
import java.util.List;

public interface DemasiaService {
    void save(Demasia demasia);

    List<Demasia> numMovimiento();

    List<Demasia> listByUni(String cod);

    List<Demasia> registrosFechaCompra(String inicio, String fin, String cod) throws ParseException;

    List<Demasia> listByProductCompra(String cod_tipoHoja, String cod_uniOpe);

    List<Demasia> registrosFechaCompraHc(String inicio, String fin, String cod, String codHc) throws ParseException;
}
