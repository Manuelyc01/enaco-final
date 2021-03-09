package prueba1.Service;

import prueba1.models.Movimiento;

import java.util.List;

public interface MovimientoService {
    List<Movimiento> list();

    Movimiento findById(Integer id);
}
