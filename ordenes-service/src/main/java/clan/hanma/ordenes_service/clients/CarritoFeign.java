package clan.hanma.ordenes_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "carrito-service")
public class CarritoFeign {
}
