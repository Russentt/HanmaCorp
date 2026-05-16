package clan.hanma.inventario_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.inventario_service.model.Bodega;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long>{
    
}
