package clan.hanma.pagos_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.pagos_service.model.EstadoPago;
import clan.hanma.pagos_service.service.EstadoPagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estadoPago")
public class EstadoPagoController {

    @Autowired
    private EstadoPagoService estadoPagoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(estadoPagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(estadoPagoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EstadoPago est) {
        return new ResponseEntity<>(estadoPagoService.save(est), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        estadoPagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EstadoPago est) {
        return ResponseEntity.ok(estadoPagoService.update(id, est));
    }


}
