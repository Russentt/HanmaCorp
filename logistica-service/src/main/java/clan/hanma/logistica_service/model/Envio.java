package clan.hanma.logistica_service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "orden_id")
    private Long ordenId;

    @ManyToOne
    @JoinColumn(name = "direccion_entrega_id")
    private DireccionEntrega direccionEntrega;

    @Column(nullable = false, name = "fecha_envio")
    private LocalDate fechaEnvio;

    @Column(nullable = false, name = "fecha_entrega_estimada")
    private LocalDateTime fechaEntregaEstimada;

    @Column(nullable = false, name = "fecha_entrega_real")
    private LocalDateTime fechaEntregaReal;
    

}
