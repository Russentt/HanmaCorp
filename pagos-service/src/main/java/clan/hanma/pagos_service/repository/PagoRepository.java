package clan.hanma.pagos_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.pagos_service.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{

}
