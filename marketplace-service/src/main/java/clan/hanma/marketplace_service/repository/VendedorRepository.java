package clan.hanma.marketplace_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clan.hanma.marketplace_service.model.Vendedor;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long>{

    @Query(value = "select * from vendedores where usuario_id = :id", nativeQuery=true)
    Vendedor findByUsuarioId(Long id);
}
