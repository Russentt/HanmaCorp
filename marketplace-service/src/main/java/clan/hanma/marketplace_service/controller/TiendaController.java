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
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.marketplace_service.exception.ErrorResponse;
import clan.hanma.marketplace_service.model.Tienda;
import clan.hanma.marketplace_service.service.TiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tiendas")
@Tag(name = "Tiendas", description = "Controlador para el CRUD completo de tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @Operation(
        summary = "Ver todas las tiendas registradas",
        description = "Metodo que devuelve un listado de tiendas registradas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Se han encontrado tiendas",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Tienda.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado ninguna tienda",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(tiendaService.findAll());
    }

    @Operation(
        summary = "Encontrar tienda por ID",
        description = "Metodo que encuentra una tienda segun su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Tienda encontrada",
        content = @Content(schema = @Schema(implementation = Tienda.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Tienda no encontrada",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tiendaService.findById(id));
    }

    @Operation(
        summary = "Registrar tienda",
        description = "Metodo que registra a una tienda en la base de datos"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Tienda registrada correctamente",
        content = @Content(schema = @Schema(implementation = Tienda.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "No se ha podido registrar la tienda",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Tienda t) {
        return new ResponseEntity<>(tiendaService.save(t), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Borrar tienda",
        description = "Metodo que borra una tienda segun su id"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Tienda borrada con exito",
        content = @Content(schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Tienda no encontrada. No se puede borrar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tiendaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar tienda",
        description = "Metodo que actualiza atributos de una tienda segun su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Se han actualizado los atributos de la tienda ingresada",
        content = @Content(schema = @Schema(implementation = Tienda.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado la tienda indicada. No se puede actualizar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Tienda t) {
        return ResponseEntity.ok(tiendaService.update(id, t));
    }

}
