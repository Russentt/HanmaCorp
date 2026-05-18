package clan.hanma.identidad_service.exception;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> errorDeValidacion(MethodArgumentNotValidException ex) {
    List<String> errores = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
    ErrorResponse response = new ErrorResponse
    (String.valueOf(HttpStatus.BAD_REQUEST.value()),
    "Validacion fallida",
    errores.toString(),
    LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
