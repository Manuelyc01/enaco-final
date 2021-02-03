package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prueba1.models.CostoHojaCoca;
import prueba1.models.UnidadOperativa;

import java.util.List;

@Repository
public interface CostoHcRepository extends JpaRepository<CostoHojaCoca,Integer> {
    /*@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
    User findUserByStatusAndName(Integer status, String name);*/
    @Query("SELECT u FROM CostoHojaCoca u where u.estado=1 and u.cod_uniOpe= ?1")
    public List<CostoHojaCoca> filterTipoHc(UnidadOperativa cod_uniOpe);
}
