package clan.hanma.identidad_service;

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

import clan.hanma.identidad_service.controller.RolController;
import clan.hanma.identidad_service.model.Rol;
import clan.hanma.identidad_service.service.RolService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(RolController.class)
@DisplayName("Pruebas en la capa controladora de Roles")
public class RolControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rolMock;
    private Rol rolMock2;

    @BeforeEach
    void setup() {
        rolMock = new Rol();
        rolMock.setId(1L);
        rolMock.setNombre("Cliente");
        rolMock.setDescripcion("Un cliente promedio de Hanma Corporation");

        rolMock2 = new Rol();
        rolMock2.setId(2L);
        rolMock2.setNombre("Administrador");
        rolMock2.setDescripcion("Administrador del sistema");
    }

    @Test
    @DisplayName("GET /roles -> Debe retornar 200 y devolver una lista completa de roles")
    void testFindAll() throws Exception {
        Mockito.when(rolService.findAll()).thenReturn(List.of(rolMock, rolMock2));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/roles")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Cliente"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre").value("Administrador"));
    }

    @Test
    @DisplayName("GET /roles/{id} -> Debe retornar 200 y devolver el rol encontrado")
    void testFindById() throws Exception {
        Mockito.when(rolService.findById(rolMock.getId())).thenReturn(rolMock);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/roles/{id}", rolMock.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Cliente"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value("Un cliente promedio de Hanma Corporation"));
    }

    @Test
    @DisplayName("POST /roles -> Debe retornar 201 y crear un nuevo rol")
    void testSave() throws Exception {
        Mockito.when(rolService.save(Mockito.any(Rol.class))).thenReturn(rolMock);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(rolMock)))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Cliente"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value("Un cliente promedio de Hanma Corporation"));
    }

    @Test
    @DisplayName("DELETE /roles/{id} -> Debe retornar 204 y eliminar el rol")
    void testDelete() throws Exception {
        Long rolId = rolMock.getId();
        doNothing().when(rolService).delete(rolId);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/roles/{id}", rolId)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT /roles/{id} -> Debe retornar 201 y actualizar el rol")
    void testUpdate() throws Exception {
        Long rolId = rolMock.getId();
        Rol rolActualizado = new Rol();
        rolActualizado.setId(rolId);
        rolActualizado.setNombre("Cliente Premium");
        rolActualizado.setDescripcion("Cliente con acceso premium");

        when(rolService.update(eq(rolId), any(Rol.class))).thenReturn(rolActualizado);

        mockMvc.perform(MockMvcRequestBuilders.put("/roles/{id}", rolId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(rolActualizado)))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Cliente Premium"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value("Cliente con acceso premium"));
    }
}
