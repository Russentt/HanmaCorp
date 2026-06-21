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

import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.repository.BodegaRepository;
import clan.hanma.inventario_service.service.BodegaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BodegaService Test Suite")
public class BodegaServiceTest {

	@Mock
	private BodegaRepository bodegaRepository;

	@InjectMocks
	private BodegaService bodegaService;

	private Bodega bodega;

	@BeforeEach
	void setUp() {
		bodega = new Bodega();
		bodega.setId(1L);
		bodega.setNombre("Bodega Norte");
		bodega.setDireccion("Av. Industrial 123");
	}

	@Test
	@DisplayName("findAll debe retornar lista de bodegas con exito")
	void testFindAllSuccess() {
		List<Bodega> bodegas = new ArrayList<>();
		bodegas.add(bodega);

		when(bodegaRepository.findAll()).thenReturn(bodegas);

		List<Bodega> result = bodegaService.findAll();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Bodega Norte", result.get(0).getNombre());
		verify(bodegaRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findAll debe retornar lista vacia cuando no existen bodegas")
	void testFindAllEmpty() {
		when(bodegaRepository.findAll()).thenReturn(new ArrayList<>());

		List<Bodega> result = bodegaService.findAll();

		assertNotNull(result);
		assertEquals(0, result.size());
		verify(bodegaRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findById debe retornar bodega cuando existe")
	void testFindByIdSuccess() {
		when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));

		Bodega result = bodegaService.findById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Bodega Norte", result.getNombre());
		verify(bodegaRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("findById debe retornar null cuando bodega no existe")
	void testFindByIdNotFound() {
		when(bodegaRepository.findById(999L)).thenReturn(Optional.empty());

		Bodega result = bodegaService.findById(999L);

		assertNull(result);
		verify(bodegaRepository, times(1)).findById(999L);
	}

	@Test
	@DisplayName("save debe crear bodega exitosamente")
	void testSaveSuccess() {
		when(bodegaRepository.save(any(Bodega.class))).thenReturn(bodega);

		Bodega result = bodegaService.save(bodega);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Av. Industrial 123", result.getDireccion());
		verify(bodegaRepository, times(1)).save(any(Bodega.class));
	}

	@Test
	@DisplayName("delete debe eliminar bodega exitosamente")
	void testDeleteSuccess() {
		bodegaService.delete(1L);

		verify(bodegaRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("update debe actualizar bodega exitosamente")
	void testUpdateSuccess() {
		Bodega bodegaToUpdate = new Bodega();
		bodegaToUpdate.setNombre("Bodega Sur");
		bodegaToUpdate.setDireccion("Camino Sur 456");

		when(bodegaRepository.findById(1L)).thenReturn(Optional.of(bodega));
		when(bodegaRepository.save(any(Bodega.class))).thenReturn(bodega);

		Bodega result = bodegaService.update(1L, bodegaToUpdate);

		assertNotNull(result);
		assertEquals("Bodega Sur", result.getNombre());
		assertEquals("Camino Sur 456", result.getDireccion());
		verify(bodegaRepository, times(1)).findById(1L);
		verify(bodegaRepository, times(1)).save(any(Bodega.class));
	}

}
