package clan.hanma.resenas_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "producto_id")
    private Long productoId;

    @Column(name = "orden_id")
    private Long ordenId;

    @NotBlank(message = "Titulo no puede estar vacio")
    private String titulo;

    @NotBlank(message = "Comentario no puede estar vacio")
    private String comentario;

    @NotNull(message = "Puntuacion no puede estar vacio")
    private int puntuacion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @NotNull
    private boolean visible;
}
