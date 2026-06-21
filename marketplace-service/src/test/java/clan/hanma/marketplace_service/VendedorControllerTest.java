package clan.hanma.marketplace_service;

import java.time.LocalDateTime;
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

import clan.hanma.marketplace_service.controller.VendedorController;
import clan.hanma.marketplace_service.dto.UsuarioDTO;
import clan.hanma.marketplace_service.model.Tienda;
import clan.hanma.marketplace_service.model.Vendedor;
import clan.hanma.marketplace_service.service.VendedorService;

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

@WebMvcTest(VendedorController.class)
@DisplayName("Pruebas en el controlador de Vendedores")

public class VendedorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VendedorService vendedorService;

	@Autowired
	private ObjectMapper objectMapper;

	private Vendedor vendedorMock;
	private Vendedor vendedorMock2;
	private Tienda tiendaMock;
	private Tienda tiendaMock2;
	private UsuarioDTO usuarioDTOMock;

	@BeforeEach
	void setup() {
		objectMapper.registerModule(new JavaTimeModule());

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

		vendedorMock = new Vendedor();
		vendedorMock.setId(1L);
		vendedorMock.setUsuarioId(10L);
		vendedorMock.setFechaRegistro(LocalDateTime.of(2026, 1, 10, 9, 0));
		vendedorMock.setTienda(tiendaMock);

		vendedorMock2 = new Vendedor();
		vendedorMock2.setId(2L);
		vendedorMock2.setUsuarioId(20L);
		vendedorMock2.setFechaRegistro(LocalDateTime.of(2026, 1, 11, 10, 30));
		vendedorMock2.setTienda(tiendaMock2);

		usuarioDTOMock = new UsuarioDTO();
		usuarioDTOMock.setNombreCompleto("Juan Perez");
		usuarioDTOMock.setEmail("juan@mail.com");
		usuarioDTOMock.setTelefono("999999999");
		usuarioDTOMock.setNombreRol("VENDEDOR");
	}

	@Test
	@DisplayName("findAll() -> Debe retornar listado completo de vendedores")
	void testFindAll() throws Exception {
		when(vendedorService.findAll()).thenReturn(List.of(vendedorMock, vendedorMock2));

		mockMvc.perform(get("/vendedores"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].usuarioId").value(10))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].usuarioId").value(20));

		verify(vendedorService, times(1)).findAll();
	}

	@Test
	@DisplayName("findById() -> Debe retornar vendedor encontrado por ID")
	void testFindById() throws Exception {
		when(vendedorService.findById(1L)).thenReturn(vendedorMock);

		mockMvc.perform(get("/vendedores/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.usuarioId").value(10));

		verify(vendedorService, times(1)).findById(1L);
	}

	@Test
	@DisplayName("existeUsuario() -> Debe retornar datos de usuario para un vendedor")
	void testExisteUsuario() throws Exception {
		when(vendedorService.findById(1L)).thenReturn(vendedorMock);
		when(vendedorService.existeUsuario(10L)).thenReturn(usuarioDTOMock);

		mockMvc.perform(get("/vendedores/usuario/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"))
				.andExpect(jsonPath("$.email").value("juan@mail.com"));

		verify(vendedorService, times(1)).findById(1L);
		verify(vendedorService, times(1)).existeUsuario(10L);
	}

	@Test
	@DisplayName("save() -> Debe registrar nuevo vendedor")
	void testSave() throws Exception {
		when(vendedorService.save(any(Vendedor.class))).thenReturn(vendedorMock);

		String vendedorJson = objectMapper.writeValueAsString(vendedorMock);

		mockMvc.perform(post("/vendedores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vendedorJson))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.usuarioId").value(10));

		verify(vendedorService, times(1)).save(any(Vendedor.class));
	}

	@Test
	@DisplayName("delete() -> Debe eliminar vendedor por ID")
	void testDelete() throws Exception {
		doNothing().when(vendedorService).delete(anyLong());

		mockMvc.perform(delete("/vendedores/1"))
				.andExpect(status().isNoContent());

		verify(vendedorService, times(1)).delete(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar vendedor existente")
	void testUpdate() throws Exception {
		Vendedor vendedorActualizado = new Vendedor();
		vendedorActualizado.setId(1L);
		vendedorActualizado.setUsuarioId(10L);
		vendedorActualizado.setFechaRegistro(vendedorMock.getFechaRegistro());
		vendedorActualizado.setTienda(tiendaMock2);

		when(vendedorService.update(anyLong(), any(Vendedor.class))).thenReturn(vendedorActualizado);

		String vendedorJson = objectMapper.writeValueAsString(vendedorActualizado);

		mockMvc.perform(put("/vendedores/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vendedorJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.usuarioId").value(10))
				.andExpect(jsonPath("$.tienda.id").value(2));

		verify(vendedorService, times(1)).update(anyLong(), any(Vendedor.class));
	}

}
