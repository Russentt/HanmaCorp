package clan.hanma.pagos_service.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String status;
    private String error;
    private String mensaje;
    private LocalDateTime fechaError;
}
