package clan.hanma.inventario_service.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private double precio;
    private Long stock;
}
