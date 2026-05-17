package clan.hanma.ordenes_service.controller;

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

import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.service.EstadoOrdenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoOrdenController {

    @Autowired
    private EstadoOrdenService estadoOrdenService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(estadoOrdenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(estadoOrdenService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EstadoOrden estOrden) {
        return new ResponseEntity<>(estadoOrdenService.save(estOrden), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        estadoOrdenService.delete(id);
        return ResponseEntity.noContent().build();   
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EstadoOrden estOrden) {
        return ResponseEntity.ok(estadoOrdenService.update(id, estOrden));
    }

}
