package clan.hanma.identidad_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.identidad_service.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
