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

import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.service.EstadoOrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
@Tag(name = "Estados de Orden", description = "Controlador de CRUD completo de estados de orden")
public class EstadoOrdenController {

    @Autowired
    private EstadoOrdenService estadoOrdenService;

    @Operation(
        summary = "Ver estados de orden",
        description = "Metodo que devuelve un listado con todos los estados de orden registrados"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Estados de orden encontrados con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado estados de orden"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(estadoOrdenService.findAll());
    }

    @Operation(
        summary = "Encontrar estado de orden",
        description = "Metodo que busca un estado de orden por ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Estado de orden encontrado"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado el estado de orden"
    )
    @Parameter(name = "id", description = "ID del estado de orden a buscar", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(estadoOrdenService.findById(id));
    }
    @Operation(
        summary = "Crear estado de orden",
        description = "Metodo que crea un nuevo estado de orden"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Estado de orden creado con exito"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud incorrecta"
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EstadoOrden estOrden) {
        return new ResponseEntity<>(estadoOrdenService.save(estOrden), HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Eliminar estado de orden",
        description = "Metodo que elimina un estado de orden por ID"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Estado de orden eliminado con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado el estado de orden a eliminar"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID del estado de orden a eliminar", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        estadoOrdenService.delete(id);
        return ResponseEntity.noContent().build();   
    }

    @Operation(
        summary = "Actualizar estado de orden",
        description = "Metodo que actualiza un estado de orden por ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Estado de orden actualizado con exito"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud incorrecta"
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado el estado de orden a actualizar"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor"
    )
    @Parameter(name = "id", description = "ID del estado de orden a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EstadoOrden estOrden) {
        return ResponseEntity.ok(estadoOrdenService.update(id, estOrden));
    }

}
