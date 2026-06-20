package clan.hanma.ordenes_service.controller;

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

import clan.hanma.ordenes_service.exception.ErrorResponse;
import clan.hanma.ordenes_service.model.DetalleOrden;
import clan.hanma.ordenes_service.service.DetalleOrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/detalle")
@Tag(name = "Detalle de Orden", description = "Controlador de CRUD completo de detalle de orden")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService detalleService;

    @Operation(
        summary = "Encontrar todos los detalles de ordenes disponibles",
        description = "Metodo que encuentra todos los detalles de ordenes efectivamente registrados en el sistema. Los devuelve como una lista"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Se ha encontrado todos los detalles de orden con exito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = DetalleOrden.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado ninguna lista con detalle de orden",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(detalleService.findAll());
    }

    @Operation(
        summary = "Encontrar detalle de orden por ID",
        description = "Metodo que encuentra un detalle de orden dado su id"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Se ha encontrado el detalle de orden con exito",
        content = @Content(schema = @Schema(implementation = DetalleOrden.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado el detalle de orden",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del detalle de orden a buscar", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(detalleService.findById(id));
    }

    @Operation(
        summary = "Crear nuevos detalles de orden",
        description = "Metodo que crea un detalle de orden. Exige un cuerpo de solicitud."
    )
    @ApiResponse(
        responseCode = "204",
        description = "Detalle de orden guardado con exito",
        content = @Content(schema = @Schema(implementation = DetalleOrden.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud invalida",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody DetalleOrden detalle) {
        return new ResponseEntity<>(detalleService.save(detalle), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar detalle de orden",
        description = "Metodo que elimina un detalle de orden dado su id"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Detalle de orden eliminado con exito",
        content = @Content(schema = @Schema(implementation = Void.class))
    )
    @Parameter(name = "ID", description = "ID del detalle de orden a eliminar", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        detalleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar detalle de orden",
        description = "Metodo que actualiza los atributos de un detalle de orden"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Detalle de orden actualizado",
        content = @Content(schema = @Schema(implementation = DetalleOrden.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Detalle de orden no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud invalida",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del detalle de orden a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DetalleOrden detalle) {
        return ResponseEntity.ok(detalleService.update(id, detalle));
    }

}
