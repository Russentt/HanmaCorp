package clan.hanma.ordenes_service.controller;

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

import clan.hanma.ordenes_service.model.DetalleOrden;
import clan.hanma.ordenes_service.service.DetalleOrdenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/detalle")
public class DetalleOrdenController {
    private DetalleOrdenService detalleService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(detalleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(detalleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody DetalleOrden detalle) {
        return new ResponseEntity<>(detalleService.save(detalle), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        detalleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DetalleOrden detalle) {
        return ResponseEntity.ok(detalleService.update(id, detalle));
    }

}
