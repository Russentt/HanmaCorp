package clan.hanma.ordenes_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "producto_id")
    private Long productoId;

    @NotNull
    private int cantidad;

    @NotNull
    @Column(name = "precio_unitario")
    private int precioUnitario;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Orden orden;
}
