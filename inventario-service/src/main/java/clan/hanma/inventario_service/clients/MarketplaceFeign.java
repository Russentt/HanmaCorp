package clan.hanma.inventario_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import clan.hanma.inventario_service.dto.ProductoDTO;

@FeignClient(name = "marketplace-service")
public interface MarketplaceFeign {

    @GetMapping("/productos/dto/{id}")
    ProductoDTO findByIdDTO(@PathVariable Long id);

    @GetMapping("/productos/producto-stock/{id}")
    int findStock(@PathVariable Long id);

    @PutMapping("/productos/reservar/{id}")
    ProductoDTO reservarStock(@PathVariable Long id, @RequestParam int cantidad);

    @PutMapping("/productos/liberar/{id}")
    ProductoDTO liberarStock(@PathVariable Long id, @RequestParam int cantidad);
}
