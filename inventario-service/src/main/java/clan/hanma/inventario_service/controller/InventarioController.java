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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.inventario_service.model.Inventario;
import clan.hanma.inventario_service.service.InventarioService;

@RestController
@RequestMapping("/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Inventario i) {
        return new ResponseEntity<>(inventarioService.save(i), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Inventario i) {
        return ResponseEntity.ok(inventarioService.update(id, i));
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<?> findByIdDTO(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.findByIdDTO(id));
    }

    @GetMapping("/stock/{stockDisponible}")
    public ResponseEntity<?> findByStockDisponbile(@PathVariable int stockDisponible) {
        return ResponseEntity.ok(inventarioService.findByStockDisponible(stockDisponible));
    }

    @GetMapping("/producto-stock/{id}")
    public ResponseEntity<?>findStock(@PathVariable Long id) {
        return ResponseEntity.ok("Del producto " + inventarioService.findByIdDTO(id).getNombre() + " quedan " + inventarioService.findStock(id) + " unidades");
    }

    @PutMapping("/reservar/{id}")
    public ResponseEntity<?> reservarStock(@PathVariable Long id, @RequestParam int cantidad) {
        return ResponseEntity.ok(inventarioService.reservarStock(id, cantidad));
    }

    @PutMapping("/liberar/{id}")
    public ResponseEntity<?> liberarStock(@PathVariable Long id, @RequestParam int cantidad) {
        return ResponseEntity.ok(inventarioService.liberarStock(id, cantidad));
    }

}
