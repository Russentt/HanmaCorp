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

import clan.hanma.logistica_service.model.Comuna;
import clan.hanma.logistica_service.service.ComunaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(comunaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(comunaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(Comuna c) {
        return new ResponseEntity<>(comunaService.save(c), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        comunaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Comuna c) {
        return ResponseEntity.ok(comunaService.save(c));
    }
    

}
