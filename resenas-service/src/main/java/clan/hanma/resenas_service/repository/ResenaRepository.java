package clan.hanma.resenas_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.resenas_service.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long>{

}
