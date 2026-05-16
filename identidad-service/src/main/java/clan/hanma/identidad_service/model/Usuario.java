package clan.hanma.identidad_service.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre no puede estar vacio.")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "Apellido no puede estar vacio.")
    @Column(nullable = false, length = 50)
    private String apellido;

    @Email(message = "Formato de email invalido.")
    @NotBlank(message = "Email no puede estar vacio.")
    @Column(nullable = false, length = 70, unique = true)
    private String email;

    @NotBlank(message = "Password no puede estar vacio.")
    @Column(nullable = false, length = 30, name = "passwd")
    private String password;

    @NotNull(message = "Telefono no puede estar vacio.")
    @Column(length = 20)
    private String telefono;

    @NotNull(message = "Fecha de registro no puede estar vacio.")
    @Column(nullable = false, name = "fecha_registro")
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name ="rol_id")
    @NotNull(message = "Rol no puede estar vacio")
    private Rol rol;
}
