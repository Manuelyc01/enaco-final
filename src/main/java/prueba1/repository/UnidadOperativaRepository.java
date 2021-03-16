package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Inventario;
import prueba1.models.UnidadOperativa;

import java.util.List;

@Repository
public interface UnidadOperativaRepository extends JpaRepository<UnidadOperativa,String> {

    //AGENCIA && SUCURSAL
    @Query("SELECT u FROM UnidadOperativa u where u.cod_agencia.cod_agencia=?1 and u.cod_sucursal.cod_sucursal=?2")
    public List<UnidadOperativa> listarAgeSu(String cod_agencia,String cod_sucursal);

    //AGENCIA
    @Query("SELECT u FROM UnidadOperativa u where u.cod_agencia.cod_agencia=?1")
    public List<UnidadOperativa> listarAgencia(String cod_agencia);

    //SUCURSAL
    @Query("SELECT u FROM UnidadOperativa u where u.cod_sucursal.cod_sucursal=?1")
    public List<UnidadOperativa> listarSucursal(String cod_sucursal);


}
