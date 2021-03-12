package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cajaboveda")
public class CajaBoveda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cajaboveda")
    private Integer id_cajaBoveda;

    @OneToOne
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private Usuario id_usuario;

    @OneToOne
    @JoinColumn(name = "cod_uniope",referencedColumnName = "cod_uniope")
    private UnidadOperativa cod_uniOpe;

    @OneToOne
    @JoinColumn(name = "id_tipotransac",referencedColumnName = "id_tipotransac")
    private TipoTransaccion id_tipoTransac;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "monto")
    private Double monto;

}
