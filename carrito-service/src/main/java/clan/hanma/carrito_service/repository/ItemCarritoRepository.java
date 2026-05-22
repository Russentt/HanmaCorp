package clan.hanma.carrito_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.carrito_service.model.ItemCarrito;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long>{
    List<ItemCarrito> findByCarritoUsuarioId(Long usuarioId);
}
