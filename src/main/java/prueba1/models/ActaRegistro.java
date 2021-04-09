package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActaRegistro {

    private String unidadOpe;
    private String usuario;
    private String fechaInicio;
    private String fechaFin;


    private String codHc;
    private Double saldoMesAnterior;

    private Double ingresoCompra;
    private Double ingresoDecomiso;
    private Double ingresoDemasia;
    private Double ingresoTransferencia;

    private Double subtotalIngreso;

    private Double salidaTransferencia;
    private Double salidaMerma;

    private Double subtotalSalida;

    private Double total;
}
