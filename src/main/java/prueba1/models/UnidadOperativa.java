package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "unidadoperativa")
public class UnidadOperativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Uniope")
    private Integer id_UniOpe;

    @Column(name = "cod_uniope")
    private String cod_uniOpe;

    @Column(name = "nom_uniope")
    private String nom_uniOpe;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ubigeouniope")
    private String ubigeoUniOpe;

    @Column(name = "localidad")
    private String localidad;

}
