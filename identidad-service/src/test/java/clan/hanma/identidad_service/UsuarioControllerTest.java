package clan.hanma.identidad_service;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import clan.hanma.identidad_service.controller.UsuarioController;
import clan.hanma.identidad_service.dto.UsuarioDTO;
import clan.hanma.identidad_service.model.Rol;
import clan.hanma.identidad_service.model.Usuario;
import clan.hanma.identidad_service.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UsuarioController.class)
@DisplayName("Pruebas en la capa controladora de Usuarios")
public class UsuarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuarioMock;
    private Usuario usuarioMock2;
    private Rol rolMock;
    private UsuarioDTO usuarioDTOMock;

    @BeforeEach
    void setup() {

        rolMock = new Rol();
        rolMock.setId(1L);
        rolMock.setNombre("Cliente");
        rolMock.setDescripcion("Un cliente promedio de Hanma Corporation");

        usuarioMock = new Usuario();
        usuarioMock.setNombre("Alejandro");
        usuarioMock.setApellido("Gutierrez");
        usuarioMock.setFechaRegistro(LocalDate.now());
        usuarioMock.setEmail("alejandro@gmail.com");
        usuarioMock.setId(301L);
        usuarioMock.setPassword("1234");
        usuarioMock.setRol(rolMock);
        usuarioMock.setTelefono("+56934563412");

        usuarioDTOMock = new UsuarioDTO();
        usuarioDTOMock.setEmail(usuarioMock.getEmail());
        usuarioDTOMock.setNombreCompleto(usuarioMock.getNombre());
        usuarioDTOMock.setNombreRol(usuarioMock.getRol().getNombre());
        usuarioDTOMock.setTelefono(usuarioMock.getTelefono());
    }

    @Test
    @DisplayName("GET /usuarios -> Debe retornar 200 y devolver una lista completa")
    void testFindAll() throws Exception {
        Mockito.when(usuarioService.findAll()).thenReturn(List.of(usuarioDTOMock));
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    @DisplayName("GET /usuarios/{id} -> Debe retornar 200 y devolver al usuario encontrado en formato DTO.")
    void testFindById() throws Exception {
        Mockito.when(usuarioService.findById(usuarioMock.getId())).thenReturn(usuarioDTOMock);
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/301")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName(
        "POST /usuarios -> Debe retornar codigo HTTP 201, creado."
    )
    void testSave() throws Exception {
        Mockito.when(usuarioService.save(Mockito.any(Usuario.class))).thenReturn(usuarioMock);
        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(usuarioMock)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Alejandro"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Gutierrez"));
    }

    @Test
    @DisplayName(
        "DELETE /usuarios/{id} -> Debe retornar codigo HTTP 204, No Content. Y borrar al usuario"
    )
    void testDelete() throws Exception {
        Long idUsuario = usuarioMock.getId();
        doNothing().when(usuarioService).delete(idUsuario);
        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/{id}", idUsuario)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertEquals(usuarioMock.getId(), idUsuario);
    }

    @Test
    @DisplayName(
        "PUT /usuarios{id} -> Debe retornar codigo HTTP 200, OK. Y actualizar al usuario"
    )
    void testUpdate() throws Exception {
        Long idUser = usuarioMock.getId();
    
        Usuario usuarioFinal = new Usuario(
        idUser, "Alejandro", "Gutierrez", "alejo@gmail.com", 
        "password123", "+56923456578", LocalDate.now(), rolMock);

        when(usuarioService.update(eq(idUser), any(Usuario.class))).thenReturn(usuarioFinal);

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/{id}", idUser).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(usuarioFinal))
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("alejo@gmail.com"));

    }

    @Test
    @DisplayName("GET /usuarios/email/{email} -> Debe retornar 200 y devolver el usuario por email")
    void testFindByEmail() throws Exception {
        String email = usuarioMock.getEmail();
        Mockito.when(usuarioService.findByEmail(email)).thenReturn(usuarioMock);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/email/{email}", email)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
    }

    @Test
    @DisplayName("GET /usuarios/rol/{id} -> Debe retornar 200 y devolver lista de usuarios con ese rol")
    void testFindByRol() throws Exception {
        Long rolId = rolMock.getId();
        Mockito.when(usuarioService.findByRol(rolId)).thenReturn(List.of(usuarioMock));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/rol/{id}", rolId)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(usuarioMock.getEmail()));
    }

    @Test
    @DisplayName("GET /usuarios/existe-email/{email} -> Debe retornar 200 si el email existe")
    void testEmailExists() throws Exception {
        String email = usuarioMock.getEmail();
        Mockito.when(usuarioService.emailExists(email)).thenReturn(true);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/existe-email/{email}", email)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("GET /usuarios/existe-email/{email} -> Debe retornar 404 si el email no existe")
    void testEmailNotExists() throws Exception {
        String email = "noexiste@gmail.com";
        Mockito.when(usuarioService.emailExists(email)).thenReturn(false);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/existe-email/{email}", email)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
