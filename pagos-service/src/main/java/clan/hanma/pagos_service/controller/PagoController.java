package clan.hanma.pagos_service.controller;

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

import clan.hanma.pagos_service.model.Pago;
import clan.hanma.pagos_service.service.PagoService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
       return ResponseEntity.ok(pagoService.findAll()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Pago p) {
        return new ResponseEntity<>(pagoService.save(p), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Pago p) {
        return ResponseEntity.ok(pagoService.update(id, p));
    }

}
