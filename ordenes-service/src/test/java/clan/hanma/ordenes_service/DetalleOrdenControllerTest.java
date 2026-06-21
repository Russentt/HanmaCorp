package clan.hanma.ordenes_service;

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

import clan.hanma.ordenes_service.controller.DetalleOrdenController;
import clan.hanma.ordenes_service.model.DetalleOrden;
import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.service.DetalleOrdenService;

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

@WebMvcTest(DetalleOrdenController.class)
@DisplayName("Pruebas en el controlador de DetalleOrden")
public class DetalleOrdenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DetalleOrdenService detalleOrdenService;

	@Autowired
	private ObjectMapper objectMapper;

	private DetalleOrden detalleMock;
	private DetalleOrden detalleMock2;
	private Orden ordenMock;
	private Orden ordenMock2;

	@BeforeEach
	void setup() {
		ordenMock = new Orden();
		ordenMock.setId(1L);
		ordenMock.setUsuarioId(100L);
		ordenMock.setTotal(35000);

		ordenMock2 = new Orden();
		ordenMock2.setId(2L);
		ordenMock2.setUsuarioId(200L);
		ordenMock2.setTotal(48000);

		detalleMock = new DetalleOrden();
		detalleMock.setId(1L);
		detalleMock.setProductoId(10L);
		detalleMock.setCantidad(2);
		detalleMock.setPrecioUnitario(15000);
		detalleMock.setOrden(ordenMock);

		detalleMock2 = new DetalleOrden();
		detalleMock2.setId(2L);
		detalleMock2.setProductoId(20L);
		detalleMock2.setCantidad(1);
		detalleMock2.setPrecioUnitario(48000);
		detalleMock2.setOrden(ordenMock2);
	}

	@Test
	@DisplayName("findAll() -> Debe retornar listado completo de detalles")
	void testFindAll() throws Exception {
		when(detalleOrdenService.findAll()).thenReturn(List.of(detalleMock, detalleMock2));

		mockMvc.perform(get("/detalle"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].productoId").value(10))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].productoId").value(20));

		verify(detalleOrdenService, times(1)).findAll();
	}

	@Test
	@DisplayName("findById() -> Debe retornar detalle encontrado por ID")
	void testFindById() throws Exception {
		when(detalleOrdenService.findById(1L)).thenReturn(detalleMock);

		mockMvc.perform(get("/detalle/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.productoId").value(10))
				.andExpect(jsonPath("$.cantidad").value(2));

		verify(detalleOrdenService, times(1)).findById(1L);
	}

	@Test
	@DisplayName("save() -> Debe registrar un nuevo detalle")
	void testSave() throws Exception {
		when(detalleOrdenService.save(any(DetalleOrden.class))).thenReturn(detalleMock);

		String detalleJson = objectMapper.writeValueAsString(detalleMock);

		mockMvc.perform(post("/detalle")
				.contentType(MediaType.APPLICATION_JSON)
				.content(detalleJson))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.productoId").value(10));

		verify(detalleOrdenService, times(1)).save(any(DetalleOrden.class));
	}

	@Test
	@DisplayName("delete() -> Debe eliminar detalle por ID")
	void testDelete() throws Exception {
		doNothing().when(detalleOrdenService).delete(anyLong());

		mockMvc.perform(delete("/detalle/1"))
				.andExpect(status().isNoContent());

		verify(detalleOrdenService, times(1)).delete(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar detalle existente")
	void testUpdate() throws Exception {
		DetalleOrden detalleActualizado = new DetalleOrden();
		detalleActualizado.setId(1L);
		detalleActualizado.setProductoId(99L);
		detalleActualizado.setCantidad(5);
		detalleActualizado.setPrecioUnitario(22000);
		detalleActualizado.setOrden(ordenMock2);

		when(detalleOrdenService.update(anyLong(), any(DetalleOrden.class))).thenReturn(detalleActualizado);

		String detalleJson = objectMapper.writeValueAsString(detalleActualizado);

		mockMvc.perform(put("/detalle/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(detalleJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.productoId").value(99))
				.andExpect(jsonPath("$.cantidad").value(5));

		verify(detalleOrdenService, times(1)).update(anyLong(), any(DetalleOrden.class));
	}

}
