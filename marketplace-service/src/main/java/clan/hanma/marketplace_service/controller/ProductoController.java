package clan.hanma.marketplace_service.controller;

import org.hibernate.annotations.Array;
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

import clan.hanma.marketplace_service.dto.ProductoDTO;
import clan.hanma.marketplace_service.exception.ErrorResponse;
import clan.hanma.marketplace_service.model.Producto;
import clan.hanma.marketplace_service.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Controlador para CRUD completo de Productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(
        summary = "Encontrar todos los productos registrados",
        description = "Metodo que devuelve un listado de productos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Listado de productos encontrado con exito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No existen productos registrados",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }
    
    @Operation(
        summary = "Encontrar producto",
        description = "Metodo que encuentra un producto segun su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Producto encontrado",
        content = @Content(schema = @Schema(implementation = Producto.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Producto no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @Operation(
        summary = "Registrar producto",
        description = "Metodo que registra un producto en la base de datos"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Producto registrado correctamente",
        content = @Content(schema = @Schema(implementation = Producto.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "No se ha podido registrar el producto indicado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Producto p) {
        return new ResponseEntity<>(productoService.save(p), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Borrar producto",
        description = "Metodo que elimina un producto segun su ID"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Producto eliminado con exito",
        content = @Content(schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado el producto indicado. No se puede borrar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar producto",
        description = "Metodo que actualiza los atributos de un producto"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Producto actualizado con exito",
        content = @Content(schema = @Schema(implementation = Producto.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado el producto indicado. No se puede actualizar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Producto p) {
        return ResponseEntity.ok(productoService.update(id, p));
    }

    @Operation(
        summary = "Encontrar productos segun ID categoria",
        description = "Metodo que devuelve un listado de productos segun el ID de categoria entregado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Productos encontrados",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado ningun producto que posea dicho ID de categoria",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> findByCategoriaId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findByCategoriaId(id));
    }

    @Operation(
        summary = "Encontrar productos segun ID de tienda",
        description = "Metodo que devuelve un listado de productos asociados a un ID de tienda"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Productos encontrados",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado productos asociados al ID de tienda dado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/tienda/{id}")
    public ResponseEntity<?> findByTiendaId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findByTiendaId(id));
    }

    @Operation(
        summary = "Encontrar productos por precio",
        description = "Metodo que devuelve un listado de productos de hallarse estos en el rango de precios indicado"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Productos encontrados",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado productos en el rango de precios indicado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/precio")
    public ResponseEntity<?> findByPrice(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(productoService.findByPrice(min, max));
    }

    @Operation(
        summary = "Encontrar productos por stock",
        description = "Metodo que devuelve un listado de productos que posean un stock comun"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Productos encontrados",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado productos con dicho stock",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/stock/{stock}")
    public ResponseEntity<?> findByStock(@PathVariable int stock) {
        return ResponseEntity.ok(productoService.findByStock(stock));
    }

    @Operation(
        summary = "Encontrar el stock de un producto",
        description = "Metodo que retorna el stock asociado a un producto"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Stock de producto entregado",
        content = @Content(schema = @Schema(implementation = Producto.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se ha encontrado dicho producto",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/producto-stock/{id}")
    public ResponseEntity<?> findStock(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findStock(id));
    }

    @Operation(
        summary = "Encontrar productoDTO",
        description = "Metodo que devuelve un producto en su formato DTO"
    )
    @ApiResponse(
        responseCode = "200",
        description = "ProductoDTO encontrado",
        content = @Content(schema = @Schema(implementation = ProductoDTO.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "ProductoDTO no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/dto/{id}")
    public ResponseEntity<?> findByIdDTO(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findByIdDto(id));
    }

    @Operation(
        summary = "Reservar stock de un producto",
        description = "Metodo que disminuye el stock disponible para un producto"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Producto reservado con exito",
        content = @Content(schema = @Schema(implementation = ProductoDTO.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "No se ha podido reservar el producto",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Producto no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/reservar/{id}")
    public ResponseEntity<?> reservarStock(@PathVariable Long id, @RequestParam int cantidad) {
        return ResponseEntity.ok(productoService.reservarStock(id, cantidad));
    }

    @Operation(
        summary = "Liberar stock de un producto",
        description = "Metodo que aumenta el stock disponible de un producto"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Stock de producto liberado correctamente",
        content = @Content(schema = @Schema(implementation = ProductoDTO.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "No se ha podido liberar el stock del producto dado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Producto no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/liberar/{id}")
    public ResponseEntity<?> liberarStock(@PathVariable Long id, @RequestParam int cantidad) {
        return ResponseEntity.ok(productoService.liberarStock(id, cantidad));
    }


}
