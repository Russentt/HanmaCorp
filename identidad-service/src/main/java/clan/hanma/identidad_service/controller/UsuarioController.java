package clan.hanma.identidad_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.identidad_service.model.Usuario;
import clan.hanma.identidad_service.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Usuario u) {
        return ResponseEntity.ok(usuarioService.save(u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Usuario u) {
        return ResponseEntity.ok(usuarioService.update(id, u));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @GetMapping("/rol/{id}")
    public ResponseEntity<?> findByRol(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findByRol(id));
    }

    @GetMapping("/existe-email/{email}")
    public ResponseEntity<?> emailExists(@PathVariable String email) {
        if (usuarioService.emailExists(email) != false) return ResponseEntity.ok("Se encuentra registrado el email: " + email);
        return ResponseEntity.notFound().build();
    }

}
