package clan.hanma.resenas_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.resenas_service.service.ReaccionResenaService;

@RestController
@RequestMapping("/reacciones")
public class ReaccionResenaController {

    @Autowired
    private ReaccionResenaService reaccionResenaService;
}
