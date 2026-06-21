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

import clan.hanma.ordenes_service.model.HistorialEstadoOrden;
import clan.hanma.ordenes_service.service.HistorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/historial")
@Tag(name = "Historial de Estados de Orden", description = "Controlador de CRUD completo de historial de estados de orden")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @Operation(
        summary = "Obtener todos los historiales de estados de orden",
        description = "Devuelve una lista de todos los historiales de estados de orden registrados en el sistema")
    @ApiResponse(
        responseCode = "200",
        description = "Historiales de estados de orden encontrados con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado historiales de estados de orden"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(historialService.findAll());
    }

    @Operation(
        summary = "Obtener historial de estado de orden por ID",
        description = "Devuelve el historial de estado de orden correspondiente al ID proporcionado")
    @ApiResponse(
        responseCode = "200",
        description = "Historial de estado de orden encontrado con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Historial de estado de orden no encontrado"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID del historial de estado de orden a buscar", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.findById(id));
    }

    @Operation(
        summary = "Crear nuevo historial de estado de orden",
        description = "Crea un nuevo historial de estado de orden con los datos proporcionados")
    @ApiResponse(
        responseCode = "201",
        description = "Historial de estado de orden creado con exito")
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados")
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody HistorialEstadoOrden hist) {
        return new ResponseEntity<>(historialService.save(hist), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Eliminar historial de estado de orden por ID",
        description = "Elimina el historial de estado de orden correspondiente al ID proporcionado")
    @ApiResponse(
        responseCode = "204",
        description = "Historial de estado de orden eliminado con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Historial de estado de orden no encontrado"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID del historial de estado de orden a eliminar", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        historialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar historial de estado de orden por ID",
        description = "Actualiza el historial de estado de orden correspondiente al ID proporcionado con los datos nuevos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Historial de estado de orden actualizado con exito"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Historial de estado de orden no encontrado"
    )
    @Parameter(name = "id", description = "ID del historial de estado de orden a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody HistorialEstadoOrden hist) {
        return ResponseEntity.ok(historialService.save(hist));
    }

}
