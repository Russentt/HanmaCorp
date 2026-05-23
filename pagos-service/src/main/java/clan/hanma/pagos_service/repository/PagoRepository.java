package clan.hanma.pagos_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clan.hanma.pagos_service.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{
    @Query(value = "select * from pago where orden_id = :id and estado_id = 2", nativeQuery = true)
    Pago findByOrden(Long id);
}
