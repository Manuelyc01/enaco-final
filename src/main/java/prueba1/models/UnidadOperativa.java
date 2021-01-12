package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "unidadoperativa")
public class UnidadOperativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_UniOpe")
    private Integer id_UniOpe;

    @Column(name = "cod_uniOpe")
    private String cod_uniOpe;

    @Column(name = "nom_UniOpe")
    private String nom_uniOpe;

    @Column(name = "direccion")
    private String direcion;

    @Column(name = "ubigeoUniOpe")
    private String ubigeoUniOpe;

    @Column(name = "localidad")
    private String localidad;

    @OneToOne(mappedBy = "id_UniOpe")
    private Usuario usuario;
}
