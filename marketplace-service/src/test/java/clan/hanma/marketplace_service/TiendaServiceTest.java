package clan.hanma.marketplace_service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import clan.hanma.marketplace_service.model.Tienda;
import clan.hanma.marketplace_service.repository.TiendaRepository;
import clan.hanma.marketplace_service.service.TiendaService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de Tiendas")
public class TiendaServiceTest {

	@Mock
	private TiendaRepository tiendaRepository;

	@InjectMocks
	private TiendaService tiendaService;

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
	@DisplayName("findAll() -> Debe retornar una lista completa de tiendas")
	void testFindAll() {
		when(tiendaRepository.findAll()).thenReturn(List.of(tiendaMock, tiendaMock2));

		List<Tienda> resultado = tiendaService.findAll();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("TecnoStore", resultado.get(0).getNombre());
		assertEquals("CasaMarket", resultado.get(1).getNombre());
		verify(tiendaRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findAll() -> Debe retornar una lista vacia cuando no hay tiendas")
	void testFindAllEmpty() {
		when(tiendaRepository.findAll()).thenReturn(List.of());

		List<Tienda> resultado = tiendaService.findAll();

		assertNotNull(resultado);
		assertEquals(0, resultado.size());
		verify(tiendaRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("findById() -> Debe retornar la tienda encontrada por ID")
	void testFindById() {
		when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaMock));

		Tienda resultado = tiendaService.findById(1L);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("TecnoStore", resultado.getNombre());
		assertEquals("Tienda de tecnologia", resultado.getDescripcion());
		assertEquals(4.5, resultado.getReputacion());
		verify(tiendaRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("findById() -> Debe retornar null cuando la tienda no existe")
	void testFindByIdNotFound() {
		when(tiendaRepository.findById(999L)).thenReturn(Optional.empty());

		Tienda resultado = tiendaService.findById(999L);

		assertNull(resultado);
		verify(tiendaRepository, times(1)).findById(999L);
	}

	@Test
	@DisplayName("save() -> Debe guardar una nueva tienda exitosamente")
	void testSave() {
		when(tiendaRepository.save(tiendaMock)).thenReturn(tiendaMock);

		Tienda resultado = tiendaService.save(tiendaMock);

		assertNotNull(resultado);
		assertEquals("TecnoStore", resultado.getNombre());
		assertEquals("Tienda de tecnologia", resultado.getDescripcion());
		verify(tiendaRepository, times(1)).save(tiendaMock);
	}

	@Test
	@DisplayName("delete() -> Debe eliminar una tienda por ID")
	void testDelete() {
		doNothing().when(tiendaRepository).deleteById(1L);

		tiendaService.delete(1L);

		verify(tiendaRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("update() -> Debe actualizar una tienda existente")
	void testUpdate() {
		Tienda tiendaActualizada = new Tienda();
		tiendaActualizada.setId(1L);
		tiendaActualizada.setNombre("TecnoStore Plus");
		tiendaActualizada.setDescripcion("Tienda de tecnologia premium");
		tiendaActualizada.setReputacion(4.8);
		tiendaActualizada.setActiva(true);

		when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaMock));
		when(tiendaRepository.save(any(Tienda.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Tienda resultado = tiendaService.update(1L, tiendaActualizada);

		assertNotNull(resultado);
		assertEquals("TecnoStore Plus", resultado.getNombre());
		assertEquals("Tienda de tecnologia premium", resultado.getDescripcion());
		assertEquals(4.8, resultado.getReputacion());
		assertEquals(true, resultado.isActiva());
		verify(tiendaRepository, times(1)).findById(1L);
		verify(tiendaRepository, times(1)).save(any(Tienda.class));
	}

}
