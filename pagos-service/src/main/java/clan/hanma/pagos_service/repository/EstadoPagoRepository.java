package clan.hanma.pagos_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.pagos_service.model.EstadoPago;

@Repository
public interface EstadoPagoRepository extends JpaRepository<EstadoPago, Long>{

}
