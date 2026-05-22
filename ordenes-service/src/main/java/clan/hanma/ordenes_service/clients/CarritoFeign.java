package clan.hanma.ordenes_service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import clan.hanma.ordenes_service.dto.ItemCarritoDTO;

@FeignClient(name = "carrito-service")
public interface CarritoFeign {
    @GetMapping("/carrito/items/{id}")
    public List<ItemCarritoDTO> obtenerItemsPorUsuario(@PathVariable Long id);
}
