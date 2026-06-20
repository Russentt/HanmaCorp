package clan.hanma.marketplace_service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import clan.hanma.marketplace_service.controller.CategoriaController;
import clan.hanma.marketplace_service.model.Categoria;
import clan.hanma.marketplace_service.service.CategoriaService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
@DisplayName("Pruebas en el controlador de Categorias")
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Categoria categoriaMock;
    private Categoria categoriaMock2;

    @BeforeEach
    void setup() {
        categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        categoriaMock.setNombre("Electronica");
        categoriaMock.setDescripcion("Productos electronicos");

        categoriaMock2 = new Categoria();
        categoriaMock2.setId(2L);
        categoriaMock2.setNombre("Hogar");
        categoriaMock2.setDescripcion("Articulos para el hogar");
    }

    @Test
    @DisplayName("findAll() -> Debe retornar listado completo de categorias")
    void testFindAll() throws Exception {
        when(categoriaService.findAll()).thenReturn(List.of(categoriaMock, categoriaMock2));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Electronica"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Hogar"));

        verify(categoriaService, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() -> Debe retornar categoria encontrada por ID")
    void testFindById() throws Exception {
        when(categoriaService.findById(1L)).thenReturn(categoriaMock);

        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Electronica"))
                .andExpect(jsonPath("$.descripcion").value("Productos electronicos"));

        verify(categoriaService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("save() -> Debe registrar nueva categoria")
    void testSave() throws Exception {
        when(categoriaService.save(any(Categoria.class))).thenReturn(categoriaMock);

        String categoriaJson = objectMapper.writeValueAsString(categoriaMock);

        mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoriaJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Electronica"));

        verify(categoriaService, times(1)).save(any(Categoria.class));
    }

    @Test
    @DisplayName("delete() -> Debe eliminar categoria por ID")
    void testDelete() throws Exception {
        doNothing().when(categoriaService).delete(anyLong());

        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("update() -> Debe actualizar categoria existente")
    void testUpdate() throws Exception {
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setId(1L);
        categoriaActualizada.setNombre("Electronica y Tecnologia");
        categoriaActualizada.setDescripcion("Articulos electronicos y tecnologicos");

        when(categoriaService.update(anyLong(), any(Categoria.class))).thenReturn(categoriaActualizada);

        String categoriaJson = objectMapper.writeValueAsString(categoriaActualizada);

        mockMvc.perform(put("/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoriaJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Electronica y Tecnologia"))
                .andExpect(jsonPath("$.descripcion").value("Articulos electronicos y tecnologicos"));

        verify(categoriaService, times(1)).update(anyLong(), any(Categoria.class));
    }

}
