package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transferencia")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia")
    private Integer id_transferencia;

    @OneToOne
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private Usuario id_usuario;

    @OneToOne
    @JoinColumn(name = "origen",referencedColumnName = "cod_uniope")
    private UnidadOperativa origen;

    @OneToOne
    @JoinColumn(name = "destino",referencedColumnName = "cod_uniope")
    private UnidadOperativa destino;

    @Column(name = "transportista")
    private String transportista;

    @Column(name = "placavehiculo")
    private String placaVehiculo;

    @Column(name = "comentario")
    private String comentario;

    @OneToOne
    @JoinColumn(name = "cod_tipohoja", referencedColumnName = "cod_tipohoja")
    private TipoHojaCoca cod_tipoHoja;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "fecha")
    private Date fecha;

}
