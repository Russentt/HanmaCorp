package clan.hanma.inventario_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.inventario_service.model.MovimientoStock;

@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long>{
    
}
