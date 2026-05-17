package clan.hanma.logistica_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.logistica_service.model.DireccionEntrega;

@Repository
public interface DireccionEntregaRepository extends JpaRepository<DireccionEntrega, Long>{

}
