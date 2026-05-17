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

import clan.hanma.ordenes_service.model.HistorialEstadoOrden;
import clan.hanma.ordenes_service.service.HistorialService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(historialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody HistorialEstadoOrden hist) {
        return new ResponseEntity<>(historialService.save(hist), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        historialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody HistorialEstadoOrden hist) {
        return ResponseEntity.ok(historialService.save(hist));
    }

}
