package clan.hanma.marketplace_service.model;

import java.math.BigDecimal;

public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long precio;
    private Long stock;
    private Tienda tienda;
    private Categoria categoria;
}
