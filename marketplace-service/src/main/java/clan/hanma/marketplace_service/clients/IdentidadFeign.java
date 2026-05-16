package clan.hanma.marketplace_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import clan.hanma.marketplace_service.dto.UsuarioDTO;

@FeignClient(name = "identidad-service", url= "http://localhost:8090")
public interface IdentidadFeign {

    @GetMapping("{/id}")
    UsuarioDTO findById(@PathVariable Long id);
}
