package clan.hanma.marketplace_service.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String nombreRol;
}
