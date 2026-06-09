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

import clan.hanma.pagos_service.model.Transaccion;
import clan.hanma.pagos_service.service.TransaccionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(transaccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transaccionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Transaccion t) {
        return new ResponseEntity<>(transaccionService.save(t), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        transaccionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Transaccion t) {
        return ResponseEntity.ok(transaccionService.update(id, t));
    }


}
