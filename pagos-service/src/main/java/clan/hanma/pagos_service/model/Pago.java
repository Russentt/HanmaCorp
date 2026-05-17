package clan.hanma.pagos_service.model;

import java.time.LocalDateTime;

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
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "orden_id")
    private Long ordenId;

    @NotNull
    private int monto;

    @NotBlank(message = "metodoPago no puede estar vacio")
    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoPago estadoPago;
}
