package clan.hanma.marketplace_service.mapper;

import org.springframework.stereotype.Component;

import clan.hanma.marketplace_service.dto.UsuarioDTO;
import clan.hanma.marketplace_service.dto.VendedorDTO;
import clan.hanma.marketplace_service.model.Vendedor;

@Component
public class VendedorMapper {

    public VendedorDTO toDTO(Vendedor v, UsuarioDTO u) {
        VendedorDTO dto = new VendedorDTO();
        dto.setNombreCompleto(u.getNombreCompleto());
        dto.setNombreTienda(v.getTienda().getNombre());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        return dto;
    }

}
