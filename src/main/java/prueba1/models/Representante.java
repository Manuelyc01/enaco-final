package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "representante")
public class Representante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_representante;

    private String nombre;
    private String dni;
    private String ubigeo;
    private String direccion;
    private String cod_productor;
    private Integer estado;
    private String cod_uniope;


}
