package clan.hanma.marketplace_service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import clan.hanma.marketplace_service.clients.IdentidadFeign;
import clan.hanma.marketplace_service.dto.UsuarioDTO;
import clan.hanma.marketplace_service.model.Tienda;
import clan.hanma.marketplace_service.model.Vendedor;
import clan.hanma.marketplace_service.repository.VendedorRepository;
import clan.hanma.marketplace_service.service.VendedorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de Vendedores")
public class VendedorServiceTest {

	@Mock
	private VendedorRepository vendedorRepository;

	@Mock
	private IdentidadFeign feign;

	@InjectMocks
	private VendedorService vendedorService;

	private Vendedor vendedorMock;
	private Vendedor vendedorMock2;
	private Tienda tiendaMock;
	private Tienda tiendaMock2;
	private UsuarioDTO usuarioDTOMock;

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
	@DisplayName("findAll() -> Debe retornar una lista completa de vendedores")
	void testFindAll() {
		when(vendedorRepository.findAll()).thenReturn(List.of(vendedorMock, vendedorMock2));

		List<Vendedor> resultado = vendedorService.findAll();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals(10L, resultado.get(0).getUsuarioId());
		assertEquals(20L, resultado.get(1).getUsuarioId());
		verify(vendedorRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findAll() -> Debe retornar una lista vacia cuando no hay vendedores")
	void testFindAllEmpty() {
		when(vendedorRepository.findAll()).thenReturn(List.of());

		List<Vendedor> resultado = vendedorService.findAll();

		assertNotNull(resultado);
		assertEquals(0, resultado.size());
		verify(vendedorRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findById() -> Debe retornar el vendedor encontrado por ID")
	void testFindById() {
		when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedorMock));

		Vendedor resultado = vendedorService.findById(1L);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals(10L, resultado.getUsuarioId());
		verify(vendedorRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("findById() -> Debe retornar null cuando el vendedor no existe")
	void testFindByIdNotFound() {
		when(vendedorRepository.findById(999L)).thenReturn(Optional.empty());

		Vendedor resultado = vendedorService.findById(999L);

		assertNull(resultado);
		verify(vendedorRepository, times(1)).findById(999L);
	}

	@Test
	@DisplayName("findByUsuarioId() -> Debe retornar vendedor por usuarioId")
	void testFindByUsuarioId() {
		when(vendedorRepository.findByUsuarioId(10L)).thenReturn(vendedorMock);

		Vendedor resultado = vendedorService.findByUsuarioId(10L);

		assertNotNull(resultado);
		assertEquals(10L, resultado.getUsuarioId());
		assertEquals("TecnoStore", resultado.getTienda().getNombre());
		verify(vendedorRepository, times(1)).findByUsuarioId(10L);
	}

	@Test
	@DisplayName("save() -> Debe guardar un nuevo vendedor exitosamente")
	void testSave() {
		when(vendedorRepository.save(vendedorMock)).thenReturn(vendedorMock);

		Vendedor resultado = vendedorService.save(vendedorMock);

		assertNotNull(resultado);
		assertEquals(10L, resultado.getUsuarioId());
		verify(vendedorRepository, times(1)).save(vendedorMock);
	}

	@Test
	@DisplayName("delete() -> Debe eliminar un vendedor por ID")
	void testDelete() {
		doNothing().when(vendedorRepository).deleteById(1L);

		vendedorService.delete(1L);

		verify(vendedorRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar un vendedor existente")
	void testUpdate() {
		Vendedor vendedorActualizado = new Vendedor();
		vendedorActualizado.setId(1L);
		vendedorActualizado.setUsuarioId(10L);
		vendedorActualizado.setFechaRegistro(vendedorMock.getFechaRegistro());
		vendedorActualizado.setTienda(tiendaMock2);

		when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedorMock));
		when(vendedorRepository.save(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Vendedor resultado = vendedorService.update(1L, vendedorActualizado);

		assertNotNull(resultado);
		assertEquals("CasaMarket", resultado.getTienda().getNombre());
		verify(vendedorRepository, times(1)).findById(1L);
		verify(vendedorRepository, times(1)).save(any(Vendedor.class));
	}

	@Test
	@DisplayName("existeUsuario() -> Debe retornar datos de usuario desde identidad")
	void testExisteUsuario() {
		when(feign.findById(10L)).thenReturn(usuarioDTOMock);

		UsuarioDTO resultado = vendedorService.existeUsuario(10L);

		assertNotNull(resultado);
		assertEquals("Juan Perez", resultado.getNombreCompleto());
		assertEquals("juan@mail.com", resultado.getEmail());
		verify(feign, times(1)).findById(10L);
	}

}
