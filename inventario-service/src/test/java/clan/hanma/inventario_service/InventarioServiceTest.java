package clan.hanma.inventario_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import clan.hanma.inventario_service.clients.MarketplaceFeign;
import clan.hanma.inventario_service.dto.ProductoDTO;
import clan.hanma.inventario_service.mapper.InventarioMapper;
import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.model.Inventario;
import clan.hanma.inventario_service.repository.InventarioRepository;
import clan.hanma.inventario_service.service.InventarioService;

@ExtendWith(MockitoExtension.class)
@DisplayName("InventarioService Test Suite")
public class InventarioServiceTest {

	@Mock
	private InventarioRepository inventarioRepository;

	@Mock
	private InventarioMapper mapper;

	@Mock
	private MarketplaceFeign feign;

	@InjectMocks
	private InventarioService inventarioService;

	private Bodega bodega;
	private Inventario inventario;
	private ProductoDTO productoDTO;

	@BeforeEach
	void setUp() {
		bodega = new Bodega();
		bodega.setId(1L);
		bodega.setNombre("Bodega Principal");
		bodega.setDireccion("Santiago Centro");

		inventario = new Inventario();
		inventario.setId(1L);
		inventario.setStockDisponible(100);
		inventario.setStockReservado(10);
		inventario.setStockMinimo(5);
		inventario.setProductoId(50L);
		inventario.setBodega(bodega);

		productoDTO = new ProductoDTO();
		productoDTO.setNombre("Teclado Mecanico");
		productoDTO.setDescripcion("Switch blue");
		productoDTO.setPrecio(49990);
		productoDTO.setStock(100L);
	}

	@Test
	@DisplayName("findAll debe retornar lista de inventarios con exito")
	void testFindAllSuccess() {
		List<Inventario> inventarios = new ArrayList<>();
		inventarios.add(inventario);

		when(inventarioRepository.findAll()).thenReturn(inventarios);

		List<Inventario> result = inventarioService.findAll();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getId());
		verify(inventarioRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findAll debe retornar lista vacia cuando no existen inventarios")
	void testFindAllEmpty() {
		when(inventarioRepository.findAll()).thenReturn(new ArrayList<>());

		List<Inventario> result = inventarioService.findAll();

		assertNotNull(result);
		assertEquals(0, result.size());
		verify(inventarioRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findById debe retornar inventario cuando existe")
	void testFindByIdSuccess() {
		when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));

		Inventario result = inventarioService.findById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(100, result.getStockDisponible());
		verify(inventarioRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("findById debe retornar null cuando inventario no existe")
	void testFindByIdNotFound() {
		when(inventarioRepository.findById(999L)).thenReturn(Optional.empty());

		Inventario result = inventarioService.findById(999L);

		assertNull(result);
		verify(inventarioRepository, times(1)).findById(999L);
	}

	@Test
	@DisplayName("save debe crear inventario exitosamente")
	void testSaveSuccess() {
		when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

		Inventario result = inventarioService.save(inventario);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(50L, result.getProductoId());
		verify(inventarioRepository, times(1)).save(any(Inventario.class));
	}

	@Test
	@DisplayName("delete debe eliminar inventario exitosamente")
	void testDeleteSuccess() {
		inventarioService.delete(1L);

		verify(inventarioRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("update debe actualizar inventario exitosamente")
	void testUpdateSuccess() {
		Inventario inventarioToUpdate = new Inventario();
		inventarioToUpdate.setStockDisponible(80);
		inventarioToUpdate.setStockReservado(15);
		inventarioToUpdate.setStockMinimo(8);
		inventarioToUpdate.setProductoId(51L);
		inventarioToUpdate.setBodega(bodega);

		when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));
		when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

		Inventario result = inventarioService.update(1L, inventarioToUpdate);

		assertNotNull(result);
		assertEquals(80, result.getStockDisponible());
		assertEquals(15, result.getStockReservado());
		assertEquals(8, result.getStockMinimo());
		assertEquals(51L, result.getProductoId());
		verify(inventarioRepository, times(1)).findById(1L);
		verify(inventarioRepository, times(1)).save(any(Inventario.class));
	}

	@Test
	@DisplayName("findByIdDTO debe retornar producto DTO desde feign")
	void testFindByIdDTOSuccess() {
		when(feign.findByIdDTO(50L)).thenReturn(productoDTO);

		ProductoDTO result = inventarioService.findByIdDTO(50L);

		assertNotNull(result);
		assertEquals("Teclado Mecanico", result.getNombre());
		verify(feign, times(1)).findByIdDTO(50L);
	}

	@Test
	@DisplayName("findByStockDisponible debe retornar inventarios filtrados")
	void testFindByStockDisponibleSuccess() {
		List<Inventario> inventarios = new ArrayList<>();
		inventarios.add(inventario);

		when(inventarioRepository.findByStockDisponible(100)).thenReturn(inventarios);

		List<Inventario> result = inventarioService.findByStockDisponible(100);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(100, result.get(0).getStockDisponible());
		verify(inventarioRepository, times(1)).findByStockDisponible(100);
	}

	@Test
	@DisplayName("findStock debe retornar stock desde feign")
	void testFindStockSuccess() {
		when(feign.findStock(50L)).thenReturn(100);

		int result = inventarioService.findStock(50L);

		assertEquals(100, result);
		verify(feign, times(1)).findStock(50L);
	}

	@Test
	@DisplayName("reservarStock debe retornar producto DTO actualizado")
	void testReservarStockSuccess() {
		when(feign.reservarStock(50L, 5)).thenReturn(productoDTO);

		ProductoDTO result = inventarioService.reservarStock(50L, 5);

		assertNotNull(result);
		assertEquals("Teclado Mecanico", result.getNombre());
		verify(feign, times(1)).reservarStock(50L, 5);
	}

	@Test
	@DisplayName("liberarStock debe retornar producto DTO actualizado")
	void testLiberarStockSuccess() {
		when(feign.liberarStock(50L, 3)).thenReturn(productoDTO);

		ProductoDTO result = inventarioService.liberarStock(50L, 3);

		assertNotNull(result);
		assertEquals("Teclado Mecanico", result.getNombre());
		verify(feign, times(1)).liberarStock(50L, 3);
	}

}
