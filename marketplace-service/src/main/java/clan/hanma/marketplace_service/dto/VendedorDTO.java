package clan.hanma.marketplace_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"nombreCompleto", "email", "telefono", "nombreTienda"})
public class VendedorDTO {
    private String nombreCompleto;
    private String nombreTienda;
    private String email;
    private String telefono;
}
