package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Integer id_inventario;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario id_usuario;

    @OneToOne
    @JoinColumn(name = "cod_almacen", referencedColumnName = "cod_uniope")
    private UnidadOperativa cod_almacen;

    @OneToOne
    @JoinColumn(name = "id_movimiento",referencedColumnName = "id_movimiento")
    private Movimiento id_movimiento;

    @Column(name = "documento")
    private String documento;

    @OneToOne
    @JoinColumn(name = "cod_tipohoja",referencedColumnName = "cod_tipohoja")
    private TipoHojaCoca cod_tipoHoja;

    @Column(name = "pesoneto")
    private Double pesoNeto;

    @Column(name = "stockinicial")
    private Double stockInicial;

    @Column(name = "stockfinal")
    private Double stockFinal;



}
