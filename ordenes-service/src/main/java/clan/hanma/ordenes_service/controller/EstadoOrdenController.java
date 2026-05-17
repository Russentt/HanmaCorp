package clan.hanma.ordenes_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.ordenes_service.service.EstadoOrdenService;

@RestController
@RequestMapping("/estados")
public class EstadoOrdenController {

    @Autowired
    private EstadoOrdenService estadoOrdenService;
}
