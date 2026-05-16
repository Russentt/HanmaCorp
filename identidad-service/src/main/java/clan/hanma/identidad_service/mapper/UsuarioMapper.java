package clan.hanma.identidad_service.mapper;

import org.springframework.stereotype.Component;

import clan.hanma.identidad_service.dto.UsuarioDTO;
import clan.hanma.identidad_service.model.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombreCompleto(u.getNombre() + " " + u.getApellido());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        dto.setNombreRol(u.getRol().getNombre());
        return dto;
    }
}
