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

import clan.hanma.marketplace_service.controller.TiendaController;
import clan.hanma.marketplace_service.model.Tienda;
import clan.hanma.marketplace_service.service.TiendaService;

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

@WebMvcTest(TiendaController.class)
@DisplayName("Pruebas en el controlador de Tiendas")
public class TiendaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TiendaService tiendaService;

	@Autowired
	private ObjectMapper objectMapper;

	private Tienda tiendaMock;
	private Tienda tiendaMock2;

	@BeforeEach
	void setup() {
		tiendaMock = new Tienda();
		tiendaMock.setId(1L);
		tiendaMock.setNombre("TecnoStore");
		tiendaMock.setDescripcion("Tienda de tecnologia");
		tiendaMock.setReputacion(4.5);
		tiendaMock.setActiva(true);

		tiendaMock2 = new Tienda();
		tiendaMock2.setId(2L);
		tiendaMock2.setNombre("CasaMarket");
		tiendaMock2.setDescripcion("Tienda para el hogar");
		tiendaMock2.setReputacion(4.1);
		tiendaMock2.setActiva(true);
	}

	@Test
	@DisplayName("findAll() -> Debe retornar listado completo de tiendas")
	void testFindAll() throws Exception {
		when(tiendaService.findAll()).thenReturn(List.of(tiendaMock, tiendaMock2));

		mockMvc.perform(get("/tiendas"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].nombre").value("TecnoStore"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].nombre").value("CasaMarket"));

		verify(tiendaService, times(1)).findAll();
	}

	@Test
	@DisplayName("findById() -> Debe retornar tienda encontrada por ID")
	void testFindById() throws Exception {
		when(tiendaService.findById(1L)).thenReturn(tiendaMock);

		mockMvc.perform(get("/tiendas/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("TecnoStore"))
				.andExpect(jsonPath("$.descripcion").value("Tienda de tecnologia"));

		verify(tiendaService, times(1)).findById(1L);
	}

	@Test
	@DisplayName("save() -> Debe registrar nueva tienda")
	void testSave() throws Exception {
		when(tiendaService.save(any(Tienda.class))).thenReturn(tiendaMock);

		String tiendaJson = objectMapper.writeValueAsString(tiendaMock);

		mockMvc.perform(post("/tiendas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(tiendaJson))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("TecnoStore"));

		verify(tiendaService, times(1)).save(any(Tienda.class));
	}

	@Test
	@DisplayName("delete() -> Debe eliminar tienda por ID")
	void testDelete() throws Exception {
		doNothing().when(tiendaService).delete(anyLong());

		mockMvc.perform(delete("/tiendas/1"))
				.andExpect(status().isNoContent());

		verify(tiendaService, times(1)).delete(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar tienda existente")
	void testUpdate() throws Exception {
		Tienda tiendaActualizada = new Tienda();
		tiendaActualizada.setId(1L);
		tiendaActualizada.setNombre("TecnoStore Pro");
		tiendaActualizada.setDescripcion("Tienda de tecnologia premium");
		tiendaActualizada.setReputacion(4.8);
		tiendaActualizada.setActiva(true);

		when(tiendaService.update(anyLong(), any(Tienda.class))).thenReturn(tiendaActualizada);

		String tiendaJson = objectMapper.writeValueAsString(tiendaActualizada);

		mockMvc.perform(put("/tiendas/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(tiendaJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("TecnoStore Pro"))
				.andExpect(jsonPath("$.reputacion").value(4.8));

		verify(tiendaService, times(1)).update(anyLong(), any(Tienda.class));
	}

}
