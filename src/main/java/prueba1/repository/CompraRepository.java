package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.Compra;
import prueba1.models.CostoHojaCoca;
import prueba1.models.Usuario;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer> {

    @Query("SELECT u FROM Compra u where u.id_usuario.id_usuario=?1 order by u.id_compra desc ")
    public List<Compra> listCompraByIdUsuario(Integer id);

    @Query("SELECT u from Compra u order by u.id_compra desc ")
    public List<Compra> list();


}
