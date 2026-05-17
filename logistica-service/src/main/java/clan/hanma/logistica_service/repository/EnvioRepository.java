package clan.hanma.logistica_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.logistica_service.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long>{

}
