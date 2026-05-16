package clan.hanma.marketplace_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "Descripcion no puede estar vacio")
    private String descripcion;

    @NotNull(message = "Precio no puede estar vacio")
    @Positive(message = "Precio solo admite valores positivos.")
    private double precio;

    @NotNull(message = "Stock no puede estar vacio")
    @Min(value = 0, message = "Stock no puede ser inferior a 0.")
    private Long stock;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
