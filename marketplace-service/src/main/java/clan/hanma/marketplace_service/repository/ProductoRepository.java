package clan.hanma.marketplace_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clan.hanma.marketplace_service.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    @Query(value = "select * from productos where categoria_id = :id", nativeQuery = true)
    Producto findByCategoriaId(Long id);

    @Query(value = "select * from productos where tienda_id = :id", nativeQuery = true)
    Producto findByTiendaId(Long id);

    @Query(value = "select * from productos where stock = :stock", nativeQuery = true)
    Producto findByStock(int stock);

    @Query(value = "select * from productos where precio between :min and :max", nativeQuery = true)
    Producto findByPrice(int min, int max);

}
