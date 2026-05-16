package clan.hanma.ordenes_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.ordenes_service.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>{

}
