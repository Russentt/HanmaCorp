package clan.hanma.inventario_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clan.hanma.inventario_service.model.Inventario;
import java.util.List;


@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    @Query(value = "select * from inventarios where stock_disponible = :stockDisponible", nativeQuery = true)
    List<Inventario> findByStockDisponible(int stockDisponible);
}
