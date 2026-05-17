package clan.hanma.marketplace_service.mapper;

import org.springframework.stereotype.Component;

import clan.hanma.marketplace_service.dto.ProductoDTO;
import clan.hanma.marketplace_service.model.Producto;

@Component
public class ProductoMapper {
    public ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        return dto;
    }
}
