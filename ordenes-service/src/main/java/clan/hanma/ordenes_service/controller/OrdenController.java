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

import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ordenes")
@Tag(name = "Ordenes", description = "Controlador de CRUD completo de ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @Operation(
        summary = "Obtener todas las ordenes",
        description = "Obtiene una lista de todas las ordenes registradas en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Ordenes encontradas con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado ordenes"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(ordenService.findAll());
    }

    @Operation(
        summary = "Obtener orden por ID",
        description = "Obtiene una orden por su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Orden encontrada con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Orden no encontrada"
    )
    @Parameter(name = "id", description = "ID de la orden a buscar", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.findById(id));
    }

    @Operation(
        summary = "Crear nueva orden",
        description = "Crea una nueva orden con los datos proporcionados"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Orden creada con exito"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida"
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Orden o) {
        return new ResponseEntity<>(ordenService.save(o), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar orden por ID",
        description = "Elimina una orden por su ID"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Orden eliminada con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Orden no encontrada"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID de la orden a eliminar", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ordenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar orden por ID",
        description = "Actualiza una orden por su ID con los datos proporcionados"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Orden actualizada con exito"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Orden no encontrada"
    )
    @Parameter(name = "id", description = "ID de la orden a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Orden o) {
        return ResponseEntity.ok(ordenService.update(id, o));
    }
    
    @Operation(
        summary = "Obtener items de orden por ID de usuario",
        description = "Obtiene una lista de items asociados a las ordenes de un usuario por su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Items de orden encontrados con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado items de orden para el usuario dado"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID del usuario para obtener sus items de orden", required = true)
    @GetMapping("/items/{id}")
    public ResponseEntity<?> obtenerItemsPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.obtenerItemsPorUsuario(id));
    }

    @Operation(
        summary = "Ver orden pagada por ID",
        description = "Obtiene los detalles de una orden que ha sido pagada por su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Orden pagada encontrada con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Orden pagada no encontrada"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID de la orden a buscar", required = true)
    @GetMapping("/pago/aprobado/{id}")
    public ResponseEntity<?> verOrdenPagada(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.verOrdenPagada(id));
    }

}
