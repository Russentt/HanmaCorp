package clan.hanma.marketplace_service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import clan.hanma.marketplace_service.controller.TiendaController;
import clan.hanma.marketplace_service.model.Tienda;
import clan.hanma.marketplace_service.service.TiendaService;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(TiendaController.class)
@DisplayName("Tienda Controller Test")
public class TiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Tienda tiendaMock;

    void setup() {
        tiendaMock = new Tienda();
        tiendaMock.setId(1L);
        tiendaMock.setNombre("Tienda 1");
        tiendaMock.setDescripcion("Descripción de la Tienda 1");
    }

    @Test
    @DisplayName("findAll() -> Debe retornar una lista completa de tiendas")
    void testFindAll() throws Exception{
        // Implementar prueba para el método findAll() del TiendaController
        when(tiendaService.findAll()).thenReturn(List.of(tiendaMock));
        mockMvc.perform(MockMvcRequestBuilders.get("/tiendas").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(tiendaMock.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(tiendaMock.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descripcion").value(tiendaMock.getDescripcion()))
                .andDo(print());
    }

    @Test
    @DisplayName("findById() -> Debe retornar una tienda por su ID")
    void testFindById() throws Exception {
        // Implementar prueba para el método findById() del TiendaController
        when(tiendaService.findById(1L)).thenReturn(tiendaMock);
        mockMvc.perform(MockMvcRequestBuilders.get("/tiendas/1").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(tiendaMock.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(tiendaMock.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(tiendaMock.getDescripcion()))
                .andDo(print());
    }

    @Test
    @DisplayName("save() -> Debe guardar una nueva tienda")
    void testSave() throws Exception {
        // Implementar prueba para el método save() del TiendaController
        when(tiendaService.save(tiendaMock)).thenReturn(tiendaMock);
        String tiendaJson = objectMapper.writeValueAsString(tiendaMock);
        mockMvc.perform(MockMvcRequestBuilders.post("/tiendas").contentType(MediaType.APPLICATION_JSON).content(tiendaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(tiendaMock.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(tiendaMock.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(tiendaMock.getDescripcion()))
                .andDo(print());
    }
    @Test
    @DisplayName("delete() -> Debe eliminar una tienda por su ID")
    void testDelete() throws Exception {
        // Implementar prueba para el método delete() del TiendaController
        mockMvc.perform(MockMvcRequestBuilders.delete("/tiendas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
