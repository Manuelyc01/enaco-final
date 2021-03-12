package prueba1.Service;

import prueba1.models.UnidadOperativa;

import java.util.List;

public interface UnidadOpeService {
    List<UnidadOperativa> listar();

    UnidadOperativa findByCod(String cod);

    void save(UnidadOperativa unidadOperativa);

    void saveCajaBoveda(String cod, double monto, Integer tipo);
}
