package clan.hanma.marketplace_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.marketplace_service.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long>{

}
