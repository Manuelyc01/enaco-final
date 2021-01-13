package prueba1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba1.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

    public Usuario findByUsua(String usua);
}
