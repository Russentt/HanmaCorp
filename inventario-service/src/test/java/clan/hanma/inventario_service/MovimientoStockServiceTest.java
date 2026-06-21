package clan.hanma.inventario_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.model.Inventario;
import clan.hanma.inventario_service.model.MovimientoStock;
import clan.hanma.inventario_service.repository.MovimientoStockRepository;
import clan.hanma.inventario_service.service.MovimientoStockService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MovimientoStockService Test Suite")
class MovimientoStockServiceTest {

	@Mock
	private MovimientoStockRepository movimientoRepository;

	@InjectMocks
	private MovimientoStockService movimientoStockService;

	private Bodega bodega;
	private Inventario inventario;
	private MovimientoStock movimientoStock;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

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
	@DisplayName("findAll debe retornar lista de movimientos con exito")
	void testFindAllSuccess() {
		List<MovimientoStock> movimientos = new ArrayList<>();
		movimientos.add(movimientoStock);

		when(movimientoRepository.findAll()).thenReturn(movimientos);

		List<MovimientoStock> result = movimientoStockService.findAll();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getId());
		verify(movimientoRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findAll debe retornar lista vacia cuando no existen movimientos")
	void testFindAllEmpty() {
		List<MovimientoStock> movimientos = new ArrayList<>();

		when(movimientoRepository.findAll()).thenReturn(movimientos);

		List<MovimientoStock> result = movimientoStockService.findAll();

		assertNotNull(result);
		assertEquals(0, result.size());
		verify(movimientoRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findById debe retornar movimiento cuando existe")
	void testFindByIdSuccess() {
		when(movimientoRepository.findById(1L)).thenReturn(Optional.of(movimientoStock));

		MovimientoStock result = movimientoStockService.findById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("ENTRADA", result.getTipoMovimiento());
		assertEquals(25, result.getCantidad());
		verify(movimientoRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("findById debe retornar null cuando movimiento no existe")
	void testFindByIdNotFound() {
		when(movimientoRepository.findById(999L)).thenReturn(Optional.empty());

		MovimientoStock result = movimientoStockService.findById(999L);

		assertNull(result);
		verify(movimientoRepository, times(1)).findById(999L);
	}

	@Test
	@DisplayName("save debe crear movimiento exitosamente")
	void testSaveSuccess() {
		when(movimientoRepository.save(any(MovimientoStock.class))).thenReturn(movimientoStock);

		MovimientoStock result = movimientoStockService.save(movimientoStock);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("ENTRADA", result.getTipoMovimiento());
		verify(movimientoRepository, times(1)).save(any(MovimientoStock.class));
	}

	@Test
	@DisplayName("delete debe eliminar movimiento exitosamente")
	void testDeleteSuccess() {
		movimientoStockService.delete(1L);

		verify(movimientoRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("update debe actualizar movimiento exitosamente")
	void testUpdateSuccess() {
		MovimientoStock movimientoToUpdate = new MovimientoStock();
		movimientoToUpdate.setTipoMovimiento("SALIDA");
		movimientoToUpdate.setCantidad(15);
		movimientoToUpdate.setFechaMovimiento(LocalDateTime.now());
		movimientoToUpdate.setInventario(inventario);

		MovimientoStock movimientoUpdated = new MovimientoStock();
		movimientoUpdated.setId(1L);
		movimientoUpdated.setTipoMovimiento("SALIDA");
		movimientoUpdated.setCantidad(15);
		movimientoUpdated.setFechaMovimiento(LocalDateTime.now());
		movimientoUpdated.setInventario(inventario);

		when(movimientoRepository.findById(1L)).thenReturn(Optional.of(movimientoStock));
		when(movimientoRepository.save(any(MovimientoStock.class))).thenReturn(movimientoUpdated);

		MovimientoStock result = movimientoStockService.update(1L, movimientoToUpdate);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("SALIDA", result.getTipoMovimiento());
		assertEquals(15, result.getCantidad());
		verify(movimientoRepository, times(1)).findById(1L);
		verify(movimientoRepository, times(1)).save(any(MovimientoStock.class));
	}
}
