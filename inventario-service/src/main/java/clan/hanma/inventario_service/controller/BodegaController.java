package clan.hanma.inventario_service.controller;

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

import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.service.BodegaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/bodegas")
@Tag(name = "Bodegas", description = "Controlador de CRUD completo de bodegas")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;

    @Operation(
        summary = "Obtener todas las bodegas",
        description = "Obtiene una lista de todas las bodegas registradas en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Bodegas encontradas con exito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bodega.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado bodegas"
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(bodegaService.findAll());
    }

    @Operation(
        summary = "Obtener bodega por ID",
        description = "Obtiene la bodega correspondiente al ID proporcionado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Bodega encontrada con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Bodega no encontrada"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID de la bodega a obtener", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bodegaService.findById(id));
    }

    @Operation(
        summary = "Crear nueva bodega",
        description = "Crea una nueva bodega con los datos proporcionados"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Bodega creada con exito"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados"
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Bodega b) {
        return new ResponseEntity<>(bodegaService.save(b), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar bodega por ID",
        description = "Elimina la bodega correspondiente al ID proporcionado"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Bodega eliminada con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado la bodega a eliminar"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID de la bodega a eliminar", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bodegaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar bodega por ID",
        description = "Actualiza la bodega correspondiente al ID proporcionado con los datos nuevos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Bodega actualizada con exito"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado la bodega a actualizar"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID de la bodega a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Bodega b) {
        return ResponseEntity.ok(bodegaService.update(id, b));
    }

}
