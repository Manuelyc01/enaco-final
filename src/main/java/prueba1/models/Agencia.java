package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agencia")
public class Agencia {

    @Id
    @Column(name = "cod_agencia")
    private String cod_agencia;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ubigeo")
    private String ubigeo;

    @Column(name = "unimedcompra")
    private String uniMedCompra;

    @OneToOne
    @JoinColumn(name = "cod_sucursal",referencedColumnName = "cod_sucursal")
    private Sucursal cod_sucursal;

}
