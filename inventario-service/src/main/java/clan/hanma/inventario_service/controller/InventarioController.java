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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.inventario_service.dto.InventarioDTO;
import clan.hanma.inventario_service.exception.ErrorResponse;
import clan.hanma.inventario_service.model.Inventario;
import clan.hanma.inventario_service.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/inventarios")
@Tag(name = "Inventarios", description = "Controlador de CRUD completo de inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(
        summary = "Obtener todos los inventarios",
        description = "Obtiene una lista de todos los inventarios registrados en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Inventarios encontrados con exito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Inventario.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado inventarios",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @Operation(
        summary = "Obtener inventario por ID",
        description = "Obtiene el inventario correspondiente al ID proporcionado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Inventario encontrado con exito",
        content = @Content(schema = @Schema(implementation = Inventario.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Inventario no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del inventario a obtener", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.findById(id));
    }

    @Operation(
        summary = "Crear nuevo inventario",
        description = "Crea un nuevo inventario con los datos proporcionados en el cuerpo de la solicitud"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Inventario creado con exito",
        content = @Content(schema = @Schema(implementation = Inventario.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Inventario i) {
        return new ResponseEntity<>(inventarioService.save(i), HttpStatus.CREATED);
    }
    
    @Operation(
        summary = "Eliminar inventario por ID",
        description = "Elimina el inventario correspondiente al ID proporcionado")
    @ApiResponse(
        responseCode = "204",
        description = "Inventario eliminado con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Inventario no encontrado para eliminar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Error interno del servidor", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del inventario a eliminar", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar inventario por ID",
        description = "Actualiza el inventario correspondiente al ID proporcionado con los datos del cuerpo de la solicitud"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Inventario actualizado con exito",
        content = @Content(schema = @Schema(implementation = Inventario.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Inventario no encontrado para actualizar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del inventario a actualizar", required = true)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Inventario i) {
        return ResponseEntity.ok(inventarioService.update(id, i));
    }

    @Operation(
        summary = "Obtener inventario por ID en formato DTO",
        description = "Obtiene el inventario correspondiente al ID proporcionado en formato DTO"
    )
    @ApiResponse(
        responseCode = "200",
        description = "InventarioDTO encontrado con exito",
        content = @Content(schema = @Schema(implementation = InventarioDTO.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "InventarioDTO no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del inventario a obtener en formato DTO", required = true)
    @GetMapping("/dto/{id}")
    public ResponseEntity<?> findByIdDTO(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.findByIdDTO(id));
    }

    @Operation(
        summary = "Obtener inventarios por stock disponible",
        description = "Obtiene una lista de inventarios que tienen el stock disponible igual al valor proporcionado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Inventarios encontrados con exito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Inventario.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado inventarios con el stock disponible proporcionado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "stockDisponible", description = "Valor del stock disponible para filtrar los inventarios", required = true)
    @GetMapping("/stock/{stockDisponible}")
    public ResponseEntity<?> findByStockDisponbile(@PathVariable int stockDisponible) {
        return ResponseEntity.ok(inventarioService.findByStockDisponible(stockDisponible));
    }

    @Operation(
        summary = "Obtener stock disponible de un producto por ID de inventario",
        description = "Obtiene el stock disponible del producto asociado al inventario correspondiente al ID proporcionado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Stock disponible encontrado con exito"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Inventario no encontrado para obtener el stock disponible",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del inventario para obtener el stock disponible del producto asociado", required = true)
    @GetMapping("/producto-stock/{id}")
    public ResponseEntity<?>findStock(@PathVariable Long id) {
        return ResponseEntity.ok("Del producto " + inventarioService.findByIdDTO(id).getNombre() + " quedan " + inventarioService.findStock(id) + " unidades");
    }

    @Operation(
        summary = "Reservar stock de un inventario por ID",
        description = "Reserva una cantidad específica del stock disponible del inventario correspondiente al ID proporcionado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Stock reservado con exito",
        content = @Content(schema = @Schema(implementation = Inventario.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Inventario no encontrado para reservar stock",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del inventario para reservar el stock disponible", required = true)
    @PutMapping("/reservar/{id}")
    public ResponseEntity<?> reservarStock(@PathVariable Long id, @RequestParam int cantidad) {
        return ResponseEntity.ok(inventarioService.reservarStock(id, cantidad));
    }

    @Operation(
        summary = "Liberar stock de un inventario por ID",
        description = "Liberar una cantidad específica del stock reservado del inventario correspondiente al ID proporcionado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Stock liberado con exito",
        content = @Content(schema = @Schema(implementation = Inventario.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida, verifique los datos proporcionados",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Inventario no encontrado para liberar stock",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @Parameter(name = "id", description = "ID del inventario para liberar el stock reservado", required = true)
    @PutMapping("/liberar/{id}")
    public ResponseEntity<?> liberarStock(@PathVariable Long id, @RequestParam int cantidad) {
        return ResponseEntity.ok(inventarioService.liberarStock(id, cantidad));
    }

}
