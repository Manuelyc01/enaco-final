package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reporte {
    private UnidadOperativa codUni;
    private TipoHojaCoca codHc;
    private String fcInicio;
    private String fcFin;
}
