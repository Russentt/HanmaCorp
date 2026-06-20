package clan.hanma.inventario_service.controller;

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

import clan.hanma.inventario_service.exception.ErrorResponse;
import clan.hanma.inventario_service.model.MovimientoStock;
import clan.hanma.inventario_service.service.MovimientoStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/movimientos")
@Tag(name = "Movimientos de Stock", description = "Controlador de CRUD completo de movimientos de stock")
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService movimientoStockService;

    @Operation(
        summary = "Obtener todos los movimientos de stock",
        description = "Devuelve una lista de todos los movimientos de stock registrados en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Movimientos de stock encontrados con exito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovimientoStock.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado movimientos de stock"
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(movimientoStockService.findAll());
    }

    @ApiResponse(
            responseCode = "200",
            description = "Movimiento de stock encontrado con exito",
            content = @Content(schema = @Schema(implementation = MovimientoStock.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Movimiento de stock no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del movimiento de stock a obtener", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoStockService.findById(id));
    }

    @Operation(
        summary = "Crear nuevo movimiento de stock",
        description = "Crea un nuevo movimiento de stock con los datos proporcionados"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Movimiento de stock creado con exito",
        content = @Content(schema = @Schema(implementation = MovimientoStock.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud invalida",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovimientoStock m) {
        return new ResponseEntity<>(movimientoStockService.save(m), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar movimiento de stock por ID",
        description = "Elimina el movimiento de stock correspondiente al ID proporcionado"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Movimiento de stock eliminado con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Movimiento de stock no encontrado para eliminar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del movimiento de stock a eliminar", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        movimientoStockService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar movimiento de stock por ID",
        description = "Actualiza el movimiento de stock correspondiente al ID proporcionado con los datos nuevos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Movimiento de stock actualizado con exito",
        content = @Content(schema = @Schema(implementation = MovimientoStock.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud invalida",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Movimiento de stock no encontrado para actualizar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
         description = "Error interno del servidor",
         content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del movimiento de stock a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MovimientoStock m) {
        return ResponseEntity.ok(movimientoStockService.update(id, m));
    }

}
