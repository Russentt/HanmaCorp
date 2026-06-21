package clan.hanma.inventario_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import clan.hanma.inventario_service.controller.MovimientoStockController;
import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.model.Inventario;
import clan.hanma.inventario_service.model.MovimientoStock;
import clan.hanma.inventario_service.service.MovimientoStockService;

@WebMvcTest(MovimientoStockController.class)
@DisplayName("MovimientoStockController Test Suite")
class MovimientoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MovimientoStockService movimientoStockService;

	private ObjectMapper objectMapper;
	private Bodega bodega;
	private Inventario inventario;
	private MovimientoStock movimientoStock;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		bodega = new Bodega();
		bodega.setId(1L);
		bodega.setNombre("Bodega Principal");
		bodega.setDireccion("Almacen");

		inventario = new Inventario();
		inventario.setId(1L);
		inventario.setStockDisponible(100);
		inventario.setStockReservado(10);
		inventario.setStockMinimo(5);
		inventario.setProductoId(50L);
		inventario.setBodega(bodega);

		movimientoStock = new MovimientoStock();
		movimientoStock.setId(1L);
		movimientoStock.setTipoMovimiento("ENTRADA");
		movimientoStock.setCantidad(25);
		movimientoStock.setFechaMovimiento(LocalDateTime.now());
		movimientoStock.setInventario(inventario);
	}

	@Test
	@DisplayName("testFindAll debe retornar todos los movimientos")
	void testFindAll() throws Exception {
		List<MovimientoStock> movimientos = new ArrayList<>();
		movimientos.add(movimientoStock);

		when(movimientoStockService.findAll()).thenReturn(movimientos);

		mockMvc.perform(get("/movimientos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].tipoMovimiento").value("ENTRADA"))
				.andExpect(jsonPath("$[0].cantidad").value(25));

		verify(movimientoStockService, times(1)).findAll();
	}

	@Test
	@DisplayName("testFindById debe retornar movimiento por ID")
	void testFindById() throws Exception {
		when(movimientoStockService.findById(1L)).thenReturn(movimientoStock);

		mockMvc.perform(get("/movimientos/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.tipoMovimiento").value("ENTRADA"))
				.andExpect(jsonPath("$.cantidad").value(25));

		verify(movimientoStockService, times(1)).findById(1L);
	}

	@Test
	@DisplayName("testSave debe crear nuevo movimiento")
	void testSave() throws Exception {
		when(movimientoStockService.save(any(MovimientoStock.class))).thenReturn(movimientoStock);

		mockMvc.perform(post("/movimientos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(movimientoStock)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.tipoMovimiento").value("ENTRADA"));

		verify(movimientoStockService, times(1)).save(any(MovimientoStock.class));
	}

	@Test
	@DisplayName("testDelete debe eliminar movimiento por ID")
	void testDelete() throws Exception {
		mockMvc.perform(delete("/movimientos/1"))
				.andExpect(status().isNoContent());

		verify(movimientoStockService, times(1)).delete(1L);
	}

	@Test
	@DisplayName("testUpdate debe actualizar movimiento por ID")
	void testUpdate() throws Exception {
		MovimientoStock movimientoUpdated = new MovimientoStock();
		movimientoUpdated.setId(1L);
		movimientoUpdated.setTipoMovimiento("SALIDA");
		movimientoUpdated.setCantidad(15);
		movimientoUpdated.setFechaMovimiento(LocalDateTime.now());
		movimientoUpdated.setInventario(inventario);

		when(movimientoStockService.update(anyLong(), any(MovimientoStock.class))).thenReturn(movimientoUpdated);

		mockMvc.perform(put("/movimientos/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(movimientoUpdated)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.tipoMovimiento").value("SALIDA"))
				.andExpect(jsonPath("$.cantidad").value(15));

		verify(movimientoStockService, times(1)).update(anyLong(), any(MovimientoStock.class));
	}
}
