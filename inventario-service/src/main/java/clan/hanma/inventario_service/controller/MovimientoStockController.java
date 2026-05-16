package clan.hanma.inventario_service.controller;

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

import clan.hanma.inventario_service.model.MovimientoStock;
import clan.hanma.inventario_service.service.MovimientoStockService;

@RestController
@RequestMapping("/movimientos")
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService movimientoStockService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(movimientoStockService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoStockService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovimientoStock m) {
        return new ResponseEntity<>(movimientoStockService.save(m), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        movimientoStockService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MovimientoStock m) {
        return ResponseEntity.ok(movimientoStockService.update(id, m));
    }

}
