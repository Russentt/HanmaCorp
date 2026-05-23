package clan.hanma.pagos_service.mapper;

import org.springframework.stereotype.Component;

import clan.hanma.pagos_service.dto.PagoDTO;
import clan.hanma.pagos_service.model.Pago;

@Component
public class PagoMapper {
    public PagoDTO toDTO(Pago p) {
        PagoDTO dto = new PagoDTO();
        dto.setFechaPago(p.getFechaPago());
        dto.setMetodoPago(p.getMetodoPago());
        dto.setMonto(p.getMonto());
        dto.setEstadoPago(p.getEstadoPago().getNombre());
        return dto;
    }
}
