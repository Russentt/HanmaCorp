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

import clan.hanma.marketplace_service.controller.ProductoController;
import clan.hanma.marketplace_service.dto.ProductoDTO;
import clan.hanma.marketplace_service.model.Categoria;
import clan.hanma.marketplace_service.model.Producto;
import clan.hanma.marketplace_service.service.ProductoService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

@WebMvcTest(ProductoController.class)
@DisplayName("Pruebas en el controlador de Productos")
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto productoMock;
    private Producto productoMock2;
    private ProductoDTO productoDTOMock;
    private Categoria categoriaMock;

    @BeforeEach
    void setup() {
        categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        categoriaMock.setNombre("Electronica");

        productoMock = new Producto();
        productoMock.setId(1L);
        productoMock.setNombre("Laptop");
        productoMock.setDescripcion("Laptop de alto rendimiento");
        productoMock.setPrecio(1200.00);
        productoMock.setStock(10L);
        productoMock.setCategoria(categoriaMock);

        productoMock2 = new Producto();
        productoMock2.setId(2L);
        productoMock2.setNombre("Mouse");
        productoMock2.setDescripcion("Mouse inalambrico");
        productoMock2.setPrecio(25.00);
        productoMock2.setStock(50L);
        productoMock2.setCategoria(categoriaMock);

        productoDTOMock = new ProductoDTO();
        productoDTOMock.setNombre(productoMock.getNombre());
        productoDTOMock.setDescripcion(productoMock.getDescripcion());
        productoDTOMock.setPrecio(productoMock.getPrecio());
        productoDTOMock.setStock(productoMock.getStock());
    }

    @Test
    @DisplayName("findAll() -> Debe retornar listado completo de productos")
    void testFindAll() throws Exception {
        when(productoService.findAll()).thenReturn(List.of(productoMock, productoMock2));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Laptop"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Mouse"));

        verify(productoService, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() -> Debe retornar producto encontrado por ID")
    void testFindById() throws Exception {
        when(productoService.findById(1L)).thenReturn(productoMock);

        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Laptop"))
                .andExpect(jsonPath("$.precio").value(1200.00));

        verify(productoService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("save() -> Debe registrar nuevo producto")
    void testSave() throws Exception {
        when(productoService.save(any(Producto.class))).thenReturn(productoMock);

        String productoJson = objectMapper.writeValueAsString(productoMock);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Laptop"));

        verify(productoService, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("delete() -> Debe eliminar producto por ID")
    void testDelete() throws Exception {
        doNothing().when(productoService).delete(anyLong());

        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isNoContent());

        verify(productoService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("update() -> Debe actualizar producto existente")
    void testUpdate() throws Exception {
        Producto productoActualizado = new Producto();
        productoActualizado.setId(1L);
        productoActualizado.setNombre("Laptop Gaming");
        productoActualizado.setDescripcion("Laptop para gaming");
        productoActualizado.setPrecio(1500.00);
        productoActualizado.setStock(5L);

        when(productoService.update(anyLong(), any(Producto.class))).thenReturn(productoActualizado);

        String productoJson = objectMapper.writeValueAsString(productoActualizado);

        mockMvc.perform(put("/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Laptop Gaming"))
                .andExpect(jsonPath("$.precio").value(1500.00));

        verify(productoService, times(1)).update(anyLong(), any(Producto.class));
    }

    @Test
    @DisplayName("findByCategoriaId() -> Debe retornar productos de una categoria")
    void testFindByCategoriaId() throws Exception {
        when(productoService.findByCategoriaId(1L)).thenReturn(List.of(productoMock, productoMock2));

        mockMvc.perform(get("/productos/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre").value("Laptop"))
                .andExpect(jsonPath("$[1].nombre").value("Mouse"));

        verify(productoService, times(1)).findByCategoriaId(1L);
    }

    @Test
    @DisplayName("findByTiendaId() -> Debe retornar productos de una tienda")
    void testFindByTiendaId() throws Exception {
        when(productoService.findByTiendaId(1L)).thenReturn(List.of(productoMock));

        mockMvc.perform(get("/productos/tienda/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre").value("Laptop"));

        verify(productoService, times(1)).findByTiendaId(1L);
    }

    @Test
    @DisplayName("findByPrice() -> Debe retornar productos en rango de precio")
    void testFindByPrice() throws Exception {
        when(productoService.findByPrice(20, 1300)).thenReturn(List.of(productoMock, productoMock2));

        mockMvc.perform(get("/productos/precio?min=20&max=1300"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(productoService, times(1)).findByPrice(20, 1300);
    }

    @Test
    @DisplayName("findByStock() -> Debe retornar productos con stock especifico")
    void testFindByStock() throws Exception {
        when(productoService.findByStock(50)).thenReturn(List.of(productoMock2));

        mockMvc.perform(get("/productos/stock/50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre").value("Mouse"));

        verify(productoService, times(1)).findByStock(50);
    }

    @Test
    @DisplayName("findStock() -> Debe retornar el stock de un producto")
    void testFindStock() throws Exception {
        when(productoService.findStock(1L)).thenReturn(10L);

        mockMvc.perform(get("/productos/producto-stock/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(10));

        verify(productoService, times(1)).findStock(1L);
    }

    @Test
    @DisplayName("findByIdDTO() -> Debe retornar ProductoDTO del producto")
    void testFindByIdDTO() throws Exception {
        when(productoService.findByIdDto(1L)).thenReturn(productoDTOMock);

        mockMvc.perform(get("/productos/dto/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Laptop"));

        verify(productoService, times(1)).findByIdDto(1L);
    }

    @Test
    @DisplayName("reservarStock() -> Debe disminuir el stock del producto")
    void testReservarStock() throws Exception {
        ProductoDTO productoReservado = new ProductoDTO();
        productoReservado.setNombre("Laptop");
        productoReservado.setStock(7L);

        when(productoService.reservarStock(1L, 3)).thenReturn(productoReservado);

        mockMvc.perform(put("/productos/reservar/1?cantidad=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stock").value(7));

        verify(productoService, times(1)).reservarStock(1L, 3);
    }

    @Test
    @DisplayName("liberarStock() -> Debe incrementar el stock del producto")
    void testLiberarStock() throws Exception {
        ProductoDTO productoLiberado = new ProductoDTO();
        productoLiberado.setNombre("Laptop");
        productoLiberado.setStock(15L);

        when(productoService.liberarStock(1L, 5)).thenReturn(productoLiberado);

        mockMvc.perform(put("/productos/liberar/1?cantidad=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stock").value(15));

        verify(productoService, times(1)).liberarStock(1L, 5);
    }

}
