package clan.hanma.inventario_service.mapper;

import org.springframework.stereotype.Component;

import clan.hanma.inventario_service.dto.InventarioDTO;
import clan.hanma.inventario_service.model.Inventario;

@Component
public class InventarioMapper {

    public InventarioDTO toDTO(Inventario i) {
        InventarioDTO dto = new InventarioDTO();
        dto.setStockDisponble(i.getStockDisponible());
        dto.setStockReservado(i.getStockReservado());
        dto.setStockMinimo(i.getStockMinimo());
        dto.setNombreBodega(i.getBodega().getNombre());
        dto.setDireccionBodega(i.getBodega().getDireccion());
        return dto;
    }
}
