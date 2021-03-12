package prueba1.Service;

import prueba1.models.Merma;

import java.util.List;

public interface MermaService {
    List<Merma> numMovimiento();

    void save(Merma merma);
}
