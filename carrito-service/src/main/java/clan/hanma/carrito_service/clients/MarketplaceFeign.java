package clan.hanma.carrito_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import clan.hanma.carrito_service.dto.ProductoDTO;

@FeignClient(name = "marketplace-service")
public interface MarketplaceFeign {
    @GetMapping("/productos/dto/{id}")
    ProductoDTO findByIdDTO(@PathVariable Long id);
}
