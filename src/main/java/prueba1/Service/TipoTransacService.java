package prueba1.Service;

import prueba1.models.TipoTransaccion;

import java.util.List;

public interface TipoTransacService {
    List<TipoTransaccion> list();

    TipoTransaccion getById(Integer id);
}
