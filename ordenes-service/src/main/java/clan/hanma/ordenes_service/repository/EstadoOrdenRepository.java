package clan.hanma.ordenes_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.ordenes_service.model.EstadoOrden;

@Repository
public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long>{

}
