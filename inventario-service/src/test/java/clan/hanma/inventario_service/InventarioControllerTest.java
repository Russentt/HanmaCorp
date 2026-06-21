package clan.hanma.inventario_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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

import java.util.ArrayList;
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

import clan.hanma.inventario_service.controller.InventarioController;
import clan.hanma.inventario_service.dto.ProductoDTO;
import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.model.Inventario;
import clan.hanma.inventario_service.service.InventarioService;

@WebMvcTest(InventarioController.class)
@DisplayName("InventarioController Test Suite")
public class InventarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private InventarioService inventarioService;

	private ObjectMapper objectMapper;
	private Bodega bodega;
	private Inventario inventario;
	private ProductoDTO productoDTO;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();

		bodega = new Bodega();
		bodega.setId(1L);
		bodega.setNombre("Bodega Principal");
		bodega.setDireccion("Santiago Centro");

		inventario = new Inventario();
		inventario.setId(1L);
		inventario.setStockDisponible(100);
		inventario.setStockReservado(10);
		inventario.setStockMinimo(5);
		inventario.setProductoId(50L);
		inventario.setBodega(bodega);

		productoDTO = new ProductoDTO();
		productoDTO.setNombre("Teclado Mecanico");
		productoDTO.setDescripcion("Switch blue");
		productoDTO.setPrecio(49990);
		productoDTO.setStock(100L);
	}

	@Test
	@DisplayName("testFindAll debe retornar todos los inventarios")
	void testFindAll() throws Exception {
		List<Inventario> inventarios = new ArrayList<>();
		inventarios.add(inventario);

		when(inventarioService.findAll()).thenReturn(inventarios);

		mockMvc.perform(get("/inventarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].stockDisponible").value(100));

		verify(inventarioService, times(1)).findAll();
	}

	@Test
	@DisplayName("testFindById debe retornar inventario por ID")
	void testFindById() throws Exception {
		when(inventarioService.findById(1L)).thenReturn(inventario);

		mockMvc.perform(get("/inventarios/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.stockReservado").value(10));

		verify(inventarioService, times(1)).findById(1L);
	}

	@Test
	@DisplayName("testSave debe crear nuevo inventario")
	void testSave() throws Exception {
		when(inventarioService.save(any(Inventario.class))).thenReturn(inventario);

		mockMvc.perform(post("/inventarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(inventario)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.stockMinimo").value(5));

		verify(inventarioService, times(1)).save(any(Inventario.class));
	}

	@Test
	@DisplayName("testDelete debe eliminar inventario por ID")
	void testDelete() throws Exception {
		mockMvc.perform(delete("/inventarios/1"))
				.andExpect(status().isNoContent());

		verify(inventarioService, times(1)).delete(1L);
	}

	@Test
	@DisplayName("testUpdate debe actualizar inventario por ID")
	void testUpdate() throws Exception {
		Inventario inventarioUpdated = new Inventario();
		inventarioUpdated.setId(1L);
		inventarioUpdated.setStockDisponible(80);
		inventarioUpdated.setStockReservado(15);
		inventarioUpdated.setStockMinimo(8);
		inventarioUpdated.setProductoId(51L);
		inventarioUpdated.setBodega(bodega);

		when(inventarioService.update(anyLong(), any(Inventario.class))).thenReturn(inventarioUpdated);

		mockMvc.perform(put("/inventarios/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(inventarioUpdated)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.stockDisponible").value(80));

		verify(inventarioService, times(1)).update(anyLong(), any(Inventario.class));
	}

	@Test
	@DisplayName("testFindByIdDTO debe retornar producto DTO")
	void testFindByIdDTO() throws Exception {
		when(inventarioService.findByIdDTO(50L)).thenReturn(productoDTO);

		mockMvc.perform(get("/inventarios/dto/50"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Teclado Mecanico"));

		verify(inventarioService, times(1)).findByIdDTO(50L);
	}

	@Test
	@DisplayName("testFindByStockDisponible debe retornar inventarios filtrados")
	void testFindByStockDisponible() throws Exception {
		List<Inventario> inventarios = new ArrayList<>();
		inventarios.add(inventario);

		when(inventarioService.findByStockDisponible(100)).thenReturn(inventarios);

		mockMvc.perform(get("/inventarios/stock/100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].stockDisponible").value(100));

		verify(inventarioService, times(1)).findByStockDisponible(100);
	}

	@Test
	@DisplayName("testFindStock debe retornar mensaje con stock")
	void testFindStock() throws Exception {
		when(inventarioService.findByIdDTO(50L)).thenReturn(productoDTO);
		when(inventarioService.findStock(50L)).thenReturn(100);

		mockMvc.perform(get("/inventarios/producto-stock/50"))
				.andExpect(status().isOk())
				.andExpect(content().string("Del producto Teclado Mecanico quedan 100 unidades"));

		verify(inventarioService, times(1)).findByIdDTO(50L);
		verify(inventarioService, times(1)).findStock(50L);
	}

	@Test
	@DisplayName("testReservarStock debe reservar stock de producto")
	void testReservarStock() throws Exception {
		when(inventarioService.reservarStock(anyLong(), anyInt())).thenReturn(productoDTO);

		mockMvc.perform(put("/inventarios/reservar/50")
				.param("cantidad", "5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Teclado Mecanico"));

		verify(inventarioService, times(1)).reservarStock(50L, 5);
	}

	@Test
	@DisplayName("testLiberarStock debe liberar stock de producto")
	void testLiberarStock() throws Exception {
		when(inventarioService.liberarStock(anyLong(), anyInt())).thenReturn(productoDTO);

		mockMvc.perform(put("/inventarios/liberar/50")
				.param("cantidad", "3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Teclado Mecanico"));

		verify(inventarioService, times(1)).liberarStock(50L, 3);
	}

}
