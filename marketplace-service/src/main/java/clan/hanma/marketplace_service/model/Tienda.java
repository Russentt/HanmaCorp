package clan.hanma.marketplace_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tiendas")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "Descripcion no puede estar vacio")
    private String descripcion;

    @Min(value = 0)
    @Max(value = 5)
    @NotNull(message = "Reputacion no puede estar vacio")
    private Double reputacion;

    @NotNull(message = "Campo de actividad no puede estar vacio")
    private boolean activa;
}
