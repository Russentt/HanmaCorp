package clan.hanma.marketplace_service.controller;

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
import org.springframework.http.HttpStatus;

import clan.hanma.marketplace_service.dto.UsuarioDTO;
import clan.hanma.marketplace_service.dto.VendedorDTO;
import clan.hanma.marketplace_service.exception.ErrorResponse;
import clan.hanma.marketplace_service.model.Vendedor;
import clan.hanma.marketplace_service.service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vendedores")
@Tag(name = "Vendedores", description = "Controlador para el CRUD completo de vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @Operation(
        summary = "Ver a todos los vendedores registrados",
        description = "Metodo que devuelve una lista con todos los vendedores registrados"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Se han encontrado vendedores",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vendedor.class)))
    )
    @ApiResponse(
        responseCode = "404",
        description = "No se han encontrado vendedores",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(vendedorService.findAll());
    }

    @Operation(
        summary = "Buscar vendedor por ID",
        description = "Metodo que encuentra a un vendedor al ingresar su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Vendedor encontrado",
        content = @Content(schema = @Schema(implementation = VendedorDTO.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Vendedor no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vendedorService.findById(id));
    }

    @Operation(
        summary = "Ver si existe vendedor",
        description = "Metodo que corrobora si existe un vendedor segun su ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Vendedor existe",
        content = @Content(schema = @Schema(implementation = Boolean.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Vendedor no existe",
        content = @Content(schema = @Schema(implementation = Boolean.class))
    )
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> existeUsuario(@PathVariable Long id) {
        Vendedor v = vendedorService.findById(id);
        Long idd = v.getUsuarioId();
        return ResponseEntity.ok(vendedorService.existeUsuario(idd));
    }

    @Operation(
        summary = "Registrar vendedor",
        description = "Metodo que registra a un vendedor en la base de datos. Exige atributos por medio de un RequestBody"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Vendedor creado",
        content = @Content(schema = @Schema(implementation = Vendedor.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "No se ha podido crear el vendedor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Vendedor v) {
        return new ResponseEntity<>(vendedorService.save(v), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Borrar vendedor",
        description = "Metodo que elimina a un vendedor al entregarse su ID"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Vendedor eliminado",
        content = @Content(schema = @Schema(implementation = Void.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Vendedor no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        vendedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Actualizar vendedor",
        description = "Metodo que actualiza los atributos de un vendedor"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Vendedor actualizado",
        content = @Content(schema = @Schema(implementation = Vendedor.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Vendedor no encontrado. No se puede actualizar",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Vendedor v) {
        return ResponseEntity.ok(vendedorService.update(id, v));
    }

}
