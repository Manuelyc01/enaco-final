package prueba1.Service;

import prueba1.models.Decomiso;

import java.text.ParseException;
import java.util.List;

public interface DecomisoService {
    List<Decomiso> numMoviento();

    void save(Decomiso decomiso);

    List<Decomiso> listByUni(String cod);

    List<Decomiso> registrosFechaDecomiso(String inicio, String fin, String cod) throws ParseException;

    List<Decomiso> listByProductDecomiso(String cod_tipoHoja, String cod_uniOpe);

    List<Decomiso> registrosFechaDecomisoHc(String inicio, String fin, String cod, String codHc) throws ParseException;
}
