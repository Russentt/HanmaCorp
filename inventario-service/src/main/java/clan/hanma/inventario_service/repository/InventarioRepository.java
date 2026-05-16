package clan.hanma.inventario_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.inventario_service.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    
}
