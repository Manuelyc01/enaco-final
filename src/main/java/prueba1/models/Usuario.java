package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id_usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @OneToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Rol id_rol;
    @OneToOne
    @JoinColumn(name = "cod_uniope", referencedColumnName = "cod_uniope")
    private UnidadOperativa cod_uniOpe;

    @Column(name = "passw")
    private String  passw;


    @OneToOne
    @JoinColumn(name = "id_estado",referencedColumnName = "id_estado")
    private Estado id_estado;

    @Column(name = "usua")
    private String usua;

    @Column(name = "serie_compra")
    private String serie_compra;

    @Column(name = "num_compras")
    private Integer num_compras;
}
