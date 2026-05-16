package clan.hanma.inventario_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventarios")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "stock_disponible")
    private int stockDisponible;

    @NotNull
    @Column(name = "stock_reservado")
    private int stockReservado;

    @NotNull
    @Column(name = "stock_minimo")
    private int stockMinimo;

    @NotNull
    @Column(name = "producto_id")
    private Long productoId;

    @ManyToOne
    @JoinColumn(name = "bodega_id")
    private Bodega bodega;
}
