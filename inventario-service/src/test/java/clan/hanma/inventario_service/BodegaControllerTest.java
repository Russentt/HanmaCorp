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

import clan.hanma.inventario_service.controller.BodegaController;
import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.service.BodegaService;

@WebMvcTest(BodegaController.class)
@DisplayName("BodegaController Test Suite")
public class BodegaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BodegaService bodegaService;

	private ObjectMapper objectMapper;
	private Bodega bodega;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();

		bodega = new Bodega();
		bodega.setId(1L);
		bodega.setNombre("Bodega Norte");
		bodega.setDireccion("Av. Industrial 123");
	}

	@Test
	@DisplayName("testFindAll debe retornar todas las bodegas")
	void testFindAll() throws Exception {
		List<Bodega> bodegas = new ArrayList<>();
		bodegas.add(bodega);

		when(bodegaService.findAll()).thenReturn(bodegas);

		mockMvc.perform(get("/bodegas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].nombre").value("Bodega Norte"));

		verify(bodegaService, times(1)).findAll();
	}

	@Test
	@DisplayName("testFindById debe retornar bodega por ID")
	void testFindById() throws Exception {
		when(bodegaService.findById(1L)).thenReturn(bodega);

		mockMvc.perform(get("/bodegas/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.direccion").value("Av. Industrial 123"));

		verify(bodegaService, times(1)).findById(1L);
	}

	@Test
	@DisplayName("testSave debe crear nueva bodega")
	void testSave() throws Exception {
		when(bodegaService.save(any(Bodega.class))).thenReturn(bodega);

		mockMvc.perform(post("/bodegas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bodega)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("Bodega Norte"));

		verify(bodegaService, times(1)).save(any(Bodega.class));
	}

	@Test
	@DisplayName("testDelete debe eliminar bodega por ID")
	void testDelete() throws Exception {
		mockMvc.perform(delete("/bodegas/1"))
				.andExpect(status().isNoContent());

		verify(bodegaService, times(1)).delete(1L);
	}

	@Test
	@DisplayName("testUpdate debe actualizar bodega por ID")
	void testUpdate() throws Exception {
		Bodega bodegaUpdated = new Bodega();
		bodegaUpdated.setId(1L);
		bodegaUpdated.setNombre("Bodega Sur");
		bodegaUpdated.setDireccion("Camino Sur 456");

		when(bodegaService.update(anyLong(), any(Bodega.class))).thenReturn(bodegaUpdated);

		mockMvc.perform(put("/bodegas/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bodegaUpdated)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("Bodega Sur"));

		verify(bodegaService, times(1)).update(anyLong(), any(Bodega.class));
	}

}
