package clan.hanma.ordenes_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.ordenes_service.service.DetalleOrdenService;

@RestController
@RequestMapping("/detalle")
public class DetalleOrdenController {
    private DetalleOrdenService detalleService;

}
