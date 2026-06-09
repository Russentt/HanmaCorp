package clan.hanma.identidad_service.controller;

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

import clan.hanma.identidad_service.dto.UsuarioDTO;
import clan.hanma.identidad_service.exception.BadRequestException;
import clan.hanma.identidad_service.exception.ResourceNotFoundException;
import clan.hanma.identidad_service.model.Usuario;
import clan.hanma.identidad_service.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Controlador para el CRUD completo de Usuarios.")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @Operation(
        summary = "Buscar por ID.",
        description = "Metodo que busca a un usuario segun su id (Long)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado",
        content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @Operation(
        summary = "Crear usuario",
        description = "Metodo que crea a un usuario y solicita un @RequestBody"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Usuario creado con exito",
        content = @Content(schema = @Schema(implementation = Usuario.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Error al crear usuario",
        content = @Content(schema = @Schema(implementation = BadRequestException.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Usuario u) {
        return new ResponseEntity<>(usuarioService.save(u), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar por ID",
        description = "Metodo que elimina a un usuario segun su ID"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Usuario eliminado con exito",
        content = @Content(schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar usuario",
        description = "Metodo que actualiza a un usuario si su ID se ha indicado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuario actualizado",
        content = @Content(schema = @Schema(implementation = Usuario.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Usuario u) {
        return ResponseEntity.ok(usuarioService.update(id, u));
    }

    @Operation(
        summary = "Encontrar usuario por email",
        description = "Metodo que encuentra a un usuario segun su email"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado",
        content = @Content(schema = @Schema(implementation = Usuario.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @Operation(
        summary = "Encontrar usuarios por rol",
        description = "Metodo que devuelve una lista de usuarios que compartan rol"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Rol y usuarios encontrados",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Rol no encontrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @GetMapping("/rol/{id}")
    public ResponseEntity<?> findByRol(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findByRol(id));
    }

    @Operation(
        summary = "Verificar si e-mail esta registrado",
        description = "Metodo que comprueba si figura como registrado un e-mail dado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Se encuentra el e-mail registrado",
        content = @Content(schema = @Schema(implementation = Boolean.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se encuentra registrado el e-mail",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @GetMapping("/existe-email/{email}")
    public ResponseEntity<?> emailExists(@PathVariable String email) {
        if (usuarioService.emailExists(email) != false) return ResponseEntity.ok("Se encuentra registrado el email: " + email);
        return ResponseEntity.notFound().build();
    }

}
