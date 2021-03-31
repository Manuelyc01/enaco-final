package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActaRegistro {
    private String codHc;
    private Double saldoMesAnterior;
    private Double ingresoCompra;
    private Double ingresoDecomiso;
    private Double ingresoDemasia;
}
