package clan.hanma.marketplace_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clan.hanma.marketplace_service.model.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long>{
}
