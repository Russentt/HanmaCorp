package clan.hanma.ordenes_service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.repository.EstadoOrdenRepository;
import clan.hanma.ordenes_service.service.EstadoOrdenService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de EstadoOrden")
public class EstadoOrdenServiceTest {

	@Mock
	private EstadoOrdenRepository estadoOrdenRepository;

	@InjectMocks
	private EstadoOrdenService estadoOrdenService;

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
	@DisplayName("findAll() -> Debe retornar lista completa de estados")
	void testFindAll() {
		when(estadoOrdenRepository.findAll()).thenReturn(List.of(estadoMock, estadoMock2));

		List<EstadoOrden> resultado = estadoOrdenService.findAll();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("PENDIENTE", resultado.get(0).getNombre());
		assertEquals("CONFIRMADA", resultado.get(1).getNombre());
		verify(estadoOrdenRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findAll() -> Debe retornar lista vacia cuando no hay estados")
	void testFindAllEmpty() {
		when(estadoOrdenRepository.findAll()).thenReturn(List.of());

		List<EstadoOrden> resultado = estadoOrdenService.findAll();

		assertNotNull(resultado);
		assertEquals(0, resultado.size());
		verify(estadoOrdenRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findById() -> Debe retornar estado encontrado por ID")
	void testFindById() {
		when(estadoOrdenRepository.findById(1L)).thenReturn(Optional.of(estadoMock));

		EstadoOrden resultado = estadoOrdenService.findById(1L);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("PENDIENTE", resultado.getNombre());
		verify(estadoOrdenRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("findById() -> Debe retornar null cuando no existe")
	void testFindByIdNotFound() {
		when(estadoOrdenRepository.findById(999L)).thenReturn(Optional.empty());

		EstadoOrden resultado = estadoOrdenService.findById(999L);

		assertNull(resultado);
		verify(estadoOrdenRepository, times(1)).findById(999L);
	}

	@Test
	@DisplayName("save() -> Debe guardar un nuevo estado")
	void testSave() {
		when(estadoOrdenRepository.save(estadoMock)).thenReturn(estadoMock);

		EstadoOrden resultado = estadoOrdenService.save(estadoMock);

		assertNotNull(resultado);
		assertEquals("PENDIENTE", resultado.getNombre());
		verify(estadoOrdenRepository, times(1)).save(estadoMock);
	}

	@Test
	@DisplayName("delete() -> Debe eliminar estado por ID")
	void testDelete() {
		doNothing().when(estadoOrdenRepository).deleteById(1L);

		estadoOrdenService.delete(1L);

		verify(estadoOrdenRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar estado existente")
	void testUpdate() {
		EstadoOrden estadoActualizado = new EstadoOrden();
		estadoActualizado.setId(1L);
		estadoActualizado.setNombre("ENVIADA");

		when(estadoOrdenRepository.findById(1L)).thenReturn(Optional.of(estadoMock));
		when(estadoOrdenRepository.save(any(EstadoOrden.class))).thenAnswer(invocation -> invocation.getArgument(0));

		EstadoOrden resultado = estadoOrdenService.update(1L, estadoActualizado);

		assertNotNull(resultado);
		assertEquals("ENVIADA", resultado.getNombre());
		verify(estadoOrdenRepository, times(1)).findById(1L);
		verify(estadoOrdenRepository, times(1)).save(any(EstadoOrden.class));
	}

}
