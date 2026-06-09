package clan.hanma.identidad_service.controller;
import org.springframework.http.HttpStatus;
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

import clan.hanma.identidad_service.exception.BadRequestException;
import clan.hanma.identidad_service.exception.ResourceNotFoundException;
import clan.hanma.identidad_service.model.Rol;
import clan.hanma.identidad_service.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Controlador para el CRUD completo de Roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @Operation(
        summary = "Encontrar rol por ID",
        description = "Metodo que devuelve un rol segun el ID ingresado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Rol encontrado con exito",
        content = @Content(schema = @Schema(implementation = Rol.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Rol no encontrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.findById(id));
    }

    @Operation(
        summary = "Guardar rol",
        description = "Metodo que guarda un rol. Exige un @RequestBody"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Rol creado con exito",
        content = @Content(schema = @Schema(implementation = Rol.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Error al crear rol",
        content = @Content(schema = @Schema(implementation = BadRequestException.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Rol rol) {
        return new ResponseEntity<>(rolService.save(rol), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar rol por ID",
        description = "Metodo que elimina un rol segun su id"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Rol eliminado con exito",
        content = @Content(schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Rol no encontrado",
        content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar rol por ID",
        description = "Metodo que actualiza un rol"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Rol actualizado con exito",
        content = @Content(schema = @Schema(implementation = Rol.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Rol r) {
        return new ResponseEntity<>(rolService.update(id, r), HttpStatus.CREATED);
    }

}
