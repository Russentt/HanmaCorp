package clan.hanma.logistica_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.logistica_service.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long>{

}
