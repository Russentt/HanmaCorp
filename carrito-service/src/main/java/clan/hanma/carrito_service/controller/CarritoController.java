package clan.hanma.carrito_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.carrito_service.model.Carrito;
import clan.hanma.carrito_service.service.CarritoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(carritoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Carrito c) {
        return new ResponseEntity<>(carritoService.save(c), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        carritoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<?> encontrarProductoDTO(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.encontrarProductoDTO(id));
    }

}
