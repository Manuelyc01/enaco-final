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
@Table(name = "decomiso")
public class Decomiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_decomiso")
    private Integer id_decomiso;

    @Basic(optional = false)
    @Column(name = "fecha",insertable = false, updatable = false)
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario id_usuario;
    @OneToOne
    @JoinColumn(name = "cod_uniope", referencedColumnName = "cod_uniope")
    private UnidadOperativa cod_uniOpe;

    @Column(name = "lugaroperativo")
    private String lugarOperativo;

    @Column(name = "lugardecomiso")
    private String lugarDecomiso;

    @Column(name = "decomisante")
    private String decomisante;

    @Column(name = "docreferencia")
    private String docReferencia;

    @Column(name = "comentario")
    private String comentario;

    @OneToOne
    @JoinColumn(name = "cod_tipohoja", referencedColumnName = "cod_tipohoja")
    private TipoHojaCoca cod_tipoHoja;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "cantidadneta")
    private Double cantidadNeta;
}

