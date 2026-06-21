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

import clan.hanma.ordenes_service.model.DetalleOrden;
import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.repository.DetalleRepository;
import clan.hanma.ordenes_service.service.DetalleOrdenService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de DetalleOrden")
public class DetalleOrdenServiceTest {

	@Mock
	private DetalleRepository detalleRepository;

	@InjectMocks
	private DetalleOrdenService detalleOrdenService;

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
	@DisplayName("findAll() -> Debe retornar lista completa de detalles")
	void testFindAll() {
		when(detalleRepository.findAll()).thenReturn(List.of(detalleMock, detalleMock2));

		List<DetalleOrden> resultado = detalleOrdenService.findAll();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals(10L, resultado.get(0).getProductoId());
		assertEquals(20L, resultado.get(1).getProductoId());
		verify(detalleRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findAll() -> Debe retornar lista vacia cuando no hay datos")
	void testFindAllEmpty() {
		when(detalleRepository.findAll()).thenReturn(List.of());

		List<DetalleOrden> resultado = detalleOrdenService.findAll();

		assertNotNull(resultado);
		assertEquals(0, resultado.size());
		verify(detalleRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findById() -> Debe retornar detalle por ID")
	void testFindById() {
		when(detalleRepository.findById(1L)).thenReturn(Optional.of(detalleMock));

		DetalleOrden resultado = detalleOrdenService.findById(1L);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals(2, resultado.getCantidad());
		verify(detalleRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("findById() -> Debe retornar null cuando no existe")
	void testFindByIdNotFound() {
		when(detalleRepository.findById(999L)).thenReturn(Optional.empty());

		DetalleOrden resultado = detalleOrdenService.findById(999L);

		assertNull(resultado);
		verify(detalleRepository, times(1)).findById(999L);
	}

	@Test
	@DisplayName("save() -> Debe guardar un nuevo detalle")
	void testSave() {
		when(detalleRepository.save(detalleMock)).thenReturn(detalleMock);

		DetalleOrden resultado = detalleOrdenService.save(detalleMock);

		assertNotNull(resultado);
		assertEquals(10L, resultado.getProductoId());
		assertEquals(15000, resultado.getPrecioUnitario());
		verify(detalleRepository, times(1)).save(detalleMock);
	}

	@Test
	@DisplayName("delete() -> Debe eliminar detalle por ID")
	void testDelete() {
		doNothing().when(detalleRepository).deleteById(1L);

		detalleOrdenService.delete(1L);

		verify(detalleRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar detalle existente")
	void testUpdate() {
		DetalleOrden detalleActualizado = new DetalleOrden();
		detalleActualizado.setId(1L);
		detalleActualizado.setProductoId(99L);
		detalleActualizado.setCantidad(5);
		detalleActualizado.setPrecioUnitario(22000);
		detalleActualizado.setOrden(ordenMock2);

		when(detalleRepository.findById(1L)).thenReturn(Optional.of(detalleMock));
		when(detalleRepository.save(any(DetalleOrden.class))).thenAnswer(invocation -> invocation.getArgument(0));

		DetalleOrden resultado = detalleOrdenService.update(1L, detalleActualizado);

		assertNotNull(resultado);
		assertEquals(99L, resultado.getProductoId());
		assertEquals(5, resultado.getCantidad());
		assertEquals(2L, resultado.getOrden().getId());
		verify(detalleRepository, times(1)).findById(1L);
		verify(detalleRepository, times(1)).save(any(DetalleOrden.class));
	}

}
