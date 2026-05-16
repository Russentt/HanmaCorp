package clan.hanma.identidad_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clan.hanma.identidad_service.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByEmail(String email);

    @Query(value = "select * from usuarios where rol_id = :id", nativeQuery = true)
    List<Usuario> findByRol(Long id);
}
