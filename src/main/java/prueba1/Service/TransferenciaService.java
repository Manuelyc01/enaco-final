package prueba1.Service;

import prueba1.models.Transferencia;

import java.text.ParseException;
import java.util.List;

public interface TransferenciaService {
    void save(Transferencia transferencia);


    List<Transferencia> listByUni(String cod);

    List<Transferencia> registrosFechaTransferencia(String inicio, String fin, String cod) throws ParseException;

    List<Transferencia> listByProductTransferencia(String cod_tipoHoja, String cod_uniOpe);

    List<Transferencia> registrosFechaTransferenciaHc(String inicio, String fin, String cod, String codHc) throws ParseException;
}
