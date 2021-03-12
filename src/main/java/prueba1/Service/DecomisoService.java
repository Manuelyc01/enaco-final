package prueba1.Service;

import prueba1.models.Decomiso;

import java.util.List;

public interface DecomisoService {
    List<Decomiso> numMoviento();

    void save(Decomiso decomiso);
}
