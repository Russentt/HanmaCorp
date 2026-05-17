package clan.hanma.resenas_service.controller;

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

import clan.hanma.resenas_service.model.ReaccionResena;
import clan.hanma.resenas_service.service.ReaccionResenaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reacciones")
public class ReaccionResenaController {

    @Autowired
    private ReaccionResenaService reaccionResenaService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(reaccionResenaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reaccionResenaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ReaccionResena rr) {
        return new ResponseEntity<>(reaccionResenaService.save(rr), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        reaccionResenaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReaccionResena rr) {
        return ResponseEntity.ok(reaccionResenaService.save(rr));
    }

}
