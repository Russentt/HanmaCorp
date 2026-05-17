package clan.hanma.inventario_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import clan.hanma.inventario_service.dto.ProductoDTO;

@FeignClient(name = "marketplace-service")
public interface MarketplaceFeign {

    @GetMapping("/productos/dto/{id}")
    ProductoDTO findByIdDTO(Long id);
}
