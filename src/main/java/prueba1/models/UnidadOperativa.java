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
    @Column(name = "cod_uniope")
    private String cod_uniOpe;

    @Column(name = "nom_uniope")
    private String nom_uniOpe;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ubigeo")
    private String ubigeo;

    @Column(name = "cod_agencia")
    private String cod_agencia;

    @Column(name = "unimedcompra")
    private String uniMedCompra;

}
