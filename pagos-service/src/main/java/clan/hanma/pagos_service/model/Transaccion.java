package clan.hanma.pagos_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "codigo_transaccion")
    private String codigoTransaccion;

    @Column(nullable = false, name = "respuesta_pasarela")
    private String respuestaPasarela;

    @ManyToOne
    @JoinColumn(name = "pago_id")
    private Pago pago;

}
