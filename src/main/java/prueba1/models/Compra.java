package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Integer id_compra;
    @Column(name = "num_compra")
    private String num_compra;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "dni")
    private String dni;

    @Column(name = "cod_uniope")
    private String cod_uniOpe;

    @Column(name = "cod_tipohoja")
    private String cod_tipoHoja;


}
