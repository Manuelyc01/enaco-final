package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipohojacoca")
public class TipoHojaCoca {

    @Id
    @Column(name = "cod_tipohoja")
    private String cod_tipoHoja;

    @Column(name = "nombre")
    private String nombre;
}
