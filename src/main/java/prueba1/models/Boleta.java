package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boleta {
    private String num_liquidacion;
    private String direccion;
    private String direccionRepre;
    private String lugarventa;
    private String nombre;
    private String cedula;
    private String fecha;
    private String dni;
    private String cod_tipoHoja;
    private Double cantidad;//pesoneto
    private String articulo;//cod_tipoHoja
    private Double valoruni;//preciosinIgv
    private Double valorcompra;//valorcompra
    private String son;
    private Double subtotal;//valorCompra
    private Double igv;
    private Double totalCompra;

}
