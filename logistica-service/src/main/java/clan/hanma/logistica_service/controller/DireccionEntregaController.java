package clan.hanma.logistica_service.controller;

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

import clan.hanma.logistica_service.model.DireccionEntrega;
import clan.hanma.logistica_service.service.DireccionEntregaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/direcciones")
public class DireccionEntregaController {

    @Autowired
    private DireccionEntregaService direccionService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(direccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(direccionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody DireccionEntrega dir) {
        return new ResponseEntity<>(direccionService.save(dir), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        direccionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DireccionEntrega dir) {
        return ResponseEntity.ok(direccionService.update(id, dir));
    }

}
