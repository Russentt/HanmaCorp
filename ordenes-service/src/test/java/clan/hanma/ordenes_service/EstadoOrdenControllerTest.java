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

import clan.hanma.ordenes_service.controller.EstadoOrdenController;
import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.service.EstadoOrdenService;

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

@WebMvcTest(EstadoOrdenController.class)
@DisplayName("Pruebas en el controlador de EstadoOrden")
public class EstadoOrdenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EstadoOrdenService estadoOrdenService;

	@Autowired
	private ObjectMapper objectMapper;

	private EstadoOrden estadoMock;
	private EstadoOrden estadoMock2;

	@BeforeEach
	void setup() {
		estadoMock = new EstadoOrden();
		estadoMock.setId(1L);
		estadoMock.setNombre("PENDIENTE");

		estadoMock2 = new EstadoOrden();
		estadoMock2.setId(2L);
		estadoMock2.setNombre("CONFIRMADA");
	}

	@Test
	@DisplayName("findAll() -> Debe retornar listado completo de estados")
	void testFindAll() throws Exception {
		when(estadoOrdenService.findAll()).thenReturn(List.of(estadoMock, estadoMock2));

		mockMvc.perform(get("/estados"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].nombre").value("PENDIENTE"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].nombre").value("CONFIRMADA"));

		verify(estadoOrdenService, times(1)).findAll();
	}

	@Test
    @DisplayName("findById() -> Debe responder 200 para solicitud por ID")
    void testFindById() throws Exception {
        mockMvc.perform(get("/estados/1"))
                .andExpect(status().isOk());
		verify(estadoOrdenService, times(1)).findById(any());
	}

	@Test
	@DisplayName("save() -> Debe registrar un nuevo estado")
	void testSave() throws Exception {
		when(estadoOrdenService.save(any(EstadoOrden.class))).thenReturn(estadoMock);

		String estadoJson = objectMapper.writeValueAsString(estadoMock);

		mockMvc.perform(post("/estados")
				.contentType(MediaType.APPLICATION_JSON)
				.content(estadoJson))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("PENDIENTE"));

		verify(estadoOrdenService, times(1)).save(any(EstadoOrden.class));
	}

	@Test
	@DisplayName("delete() -> Debe eliminar estado por ID")
	void testDelete() throws Exception {
		doNothing().when(estadoOrdenService).delete(anyLong());

		mockMvc.perform(delete("/estados/1"))
				.andExpect(status().isNoContent());

		verify(estadoOrdenService, times(1)).delete(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar estado existente")
	void testUpdate() throws Exception {
		EstadoOrden estadoActualizado = new EstadoOrden();
		estadoActualizado.setId(1L);
		estadoActualizado.setNombre("ENVIADA");

		when(estadoOrdenService.update(anyLong(), any(EstadoOrden.class))).thenReturn(estadoActualizado);

		String estadoJson = objectMapper.writeValueAsString(estadoActualizado);

		mockMvc.perform(put("/estados/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(estadoJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("ENVIADA"));

		verify(estadoOrdenService, times(1)).update(anyLong(), any(EstadoOrden.class));
	}

}
