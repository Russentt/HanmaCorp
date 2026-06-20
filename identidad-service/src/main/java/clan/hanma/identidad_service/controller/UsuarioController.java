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
@Tag(name = "Usuarios", description = "Controlador para el CRUD completo de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Recupera una lista completa de todos los usuarios registrados en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuarios recuperados exitosamente",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @Operation(
        summary = "Buscar usuario por ID",
        description = "Recupera un usuario especifico por su identificador unico"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado exitosamente",
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
        summary = "Crear nuevo usuario",
        description = "Crea un nuevo usuario en el sistema. Requiere un cuerpo de solicitud con la informacion del usuario"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Usuario creado exitosamente",
        content = @Content(schema = @Schema(implementation = Usuario.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Datos de usuario invalidos proporcionados",
        content = @Content(schema = @Schema(implementation = BadRequestException.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Usuario u) {
        return new ResponseEntity<>(usuarioService.save(u), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar usuario por ID",
        description = "Elimina un usuario del sistema basado en su identificador"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Usuario eliminado exitosamente",
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
        description = "Actualiza la informacion de un usuario existente basado en su identificador. Requiere un cuerpo de solicitud con los datos actualizados"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuario actualizado exitosamente",
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
        summary = "Buscar usuario por email",
        description = "Recupera un usuario por su direccion de correo electronico"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado exitosamente",
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
        summary = "Buscar usuarios por rol",
        description = "Recupera una lista de todos los usuarios que tienen un rol especifico"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuarios con el rol especificado encontrados exitosamente",
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
        summary = "Verificar si email esta registrado",
        description = "Verifica si una direccion de correo electronico especifica esta registrada en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Email esta registrado",
        content = @Content(schema = @Schema(implementation = Boolean.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Email no esta registrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @GetMapping("/existe-email/{email}")
    public ResponseEntity<?> emailExists(@PathVariable String email) {
        if (usuarioService.emailExists(email) != false) return ResponseEntity.ok("Email registrado: " + email);
        return ResponseEntity.notFound().build();
    }

}
