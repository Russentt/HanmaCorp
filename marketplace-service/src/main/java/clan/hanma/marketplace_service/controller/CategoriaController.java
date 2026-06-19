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
import clan.hanma.marketplace_service.model.Categoria;
import clan.hanma.marketplace_service.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Controlador de CRUD completo de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(
        summary = "Ver categorias",
        description = "Metodo que devuelve un listado con todas las categorias registradas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Categorias encontradas con exito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Categoria.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado categorias",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @Operation(
        summary = "Encontrar categoria",
        description = "Metodo que busca una categoria por ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Categoria encontrada",
        content = @Content(schema = @Schema(implementation = Categoria.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Categoria no encontrada",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @Operation(
        summary = "Registrar categoria",
        description = "Metodo que registra una categoria en la base de datos"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Categoria registrada con exito",
        content = @Content(schema = @Schema(implementation = Categoria.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "No se ha podido registrar la categoria dada",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Categoria c) {
        return new ResponseEntity<>(categoriaService.save(c), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Borrar categoria",
        description = "Metodo que elimina una categoria segun su ID"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Categoria eliminada con exito",
        content = @Content(schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Categoria no encontrada. No se ha podido eliminar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Categoria actualizada",
        description = "Metodo que actualiza los atributos de una categoria existente"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Categoria actualizada con exito",
        content = @Content(schema = @Schema(implementation = Categoria.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Categoria no encontrada. No se ha podido actualizar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Categoria c) {
        return ResponseEntity.ok(categoriaService.update(id, c));
    }

}
