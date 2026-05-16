package clan.hanma.ordenes_service.model;

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
    private Long productoId;

    @NotNull
    private int cantidad;

    @NotNull
    private int precioUnitario;
    
    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Orden orden;
}
