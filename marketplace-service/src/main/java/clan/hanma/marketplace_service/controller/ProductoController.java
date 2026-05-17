package clan.hanma.marketplace_service.controller;

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

import clan.hanma.marketplace_service.model.Producto;
import clan.hanma.marketplace_service.service.ProductoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Producto p) {
        return new ResponseEntity<>(productoService.save(p), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Producto p) {
        return ResponseEntity.ok(productoService.update(id, p));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> findByCategoriaId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findByCategoriaId(id));
    }

    @GetMapping("/tienda/{id}")
    public ResponseEntity<?> findByTiendaId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findByTiendaId(id));
    }

    @GetMapping("/precio")
    public ResponseEntity<?> findByPrice(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(productoService.findByPrice(min, max));
    }

    @GetMapping("/stock/{stock}")
    public ResponseEntity<?> findByStock(@PathVariable int stock) {
        return ResponseEntity.ok(productoService.findByStock(stock));
    }

    @GetMapping("/buscar-producto/{id}")
    public ResponseEntity<?> findStock(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findStock(id));
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<?> findByIdDTO(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findByIdDto(id));
    }

}
