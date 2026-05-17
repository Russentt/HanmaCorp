package clan.hanma.resenas_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.resenas_service.model.ReaccionResena;

@Repository
public interface ReaccionResenaRepository extends JpaRepository<ReaccionResena, Long>{

}
