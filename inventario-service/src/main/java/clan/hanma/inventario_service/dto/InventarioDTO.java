package clan.hanma.inventario_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private int stockDisponble;
    private int stockReservado;
    private int stockMinimo;
    private String nombreBodega;
    private String direccionBodega;
}
