package clan.hanma.marketplace_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import clan.hanma.marketplace_service.dto.UsuarioDTO;

@FeignClient(name = "identidad-service")
public interface IdentidadFeign {

    @GetMapping("/usuarios/{id}")
    UsuarioDTO findById(@PathVariable Long id);
}
