package clan.hanma.inventario_service.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movimientos_stock")
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "TIPO_MOVIMIENTO no puede estar vacio")
    @Column(name = "tipo_movimiento", updatable = false)
    private String tipoMovimiento;

    @NotNull(message = "Cantidad no puede ser nulo")
    private int cantidad;

    @CreationTimestamp
    @Column(name = "fecha_movimiento", updatable = false, nullable = false)
    private LocalDateTime fechaMovimiento;

    @ManyToOne
    @JoinColumn(name = "inventario_id")
    @NotNull
    private Inventario inventario;
}
