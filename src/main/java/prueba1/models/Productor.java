package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productor")
public class Productor {

    @Id
    @Column(name = "cedula")
    private String cedula;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombpredio")
    private String nombPredio;

    @Column(name = "ubicacion")
    private String ubicacion;

    @OneToOne
    @JoinColumn(name = "cod_uniope", referencedColumnName = "cod_uniope")
    private UnidadOperativa cod_uniOpe;
}
