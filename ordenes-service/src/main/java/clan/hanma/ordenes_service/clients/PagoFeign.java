package clan.hanma.ordenes_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import clan.hanma.ordenes_service.dto.PagoDTO;

@FeignClient(name = "pagos-service")
public interface PagoFeign {
    @GetMapping("/pagos/orden/{id}")
    public PagoDTO validarOrdenPagada(@PathVariable Long id);
}
