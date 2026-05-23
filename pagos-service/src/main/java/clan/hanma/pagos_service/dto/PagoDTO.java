package clan.hanma.pagos_service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"monto", "metodoPago", "fechaPago", "estadoPago"})
public class PagoDTO {
    private int monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private String estadoPago;
}
