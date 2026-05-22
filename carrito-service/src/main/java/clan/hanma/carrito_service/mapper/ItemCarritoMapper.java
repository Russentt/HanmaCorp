package clan.hanma.carrito_service.mapper;

import org.springframework.stereotype.Component;

import clan.hanma.carrito_service.dto.ItemCarritoDTO;
import clan.hanma.carrito_service.model.ItemCarrito;

@Component
public class ItemCarritoMapper {
    public ItemCarritoDTO toDTO(ItemCarrito i) {
        ItemCarritoDTO dto = new ItemCarritoDTO();
        dto.setCantidad(i.getCantidad());
        dto.setProductoId(i.getProductoId());
        return dto;
    }
}
