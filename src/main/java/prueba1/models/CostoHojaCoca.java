package prueba1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "costohojacoca")
public class CostoHojaCoca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_costohc")
    private Integer id_costoHc;

    @OneToOne
    @JoinColumn(name = "cod_uniope", referencedColumnName = "cod_uniope")
    private UnidadOperativa cod_uniOpe;

    @OneToOne
    @JoinColumn(name = "cod_tipohoja",referencedColumnName = "cod_tipohoja")
    private TipoHojaCoca cod_tipoHoja;

    @Column(name = "fecha_vigencia", columnDefinition = "DATE")
    private LocalDate fecha_vigencia;

    @Column(name = "preciosinigv")
    private Double precioSinIgv;

    @Column(name = "igv")
    private Double igv;

    @Column(name = "precioconigv")
    private Double precioConIgv;







}
