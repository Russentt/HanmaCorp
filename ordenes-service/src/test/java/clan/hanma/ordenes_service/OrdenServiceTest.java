package clan.hanma.ordenes_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import clan.hanma.ordenes_service.clients.CarritoFeign;
import clan.hanma.ordenes_service.clients.PagoFeign;
import clan.hanma.ordenes_service.dto.ItemCarritoDTO;
import clan.hanma.ordenes_service.dto.PagoDTO;
import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.repository.OrdenRepository;
import clan.hanma.ordenes_service.service.OrdenService;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrdenService Test Suite")
class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private CarritoFeign carritoFeign;

    @Mock
    private PagoFeign pagoFeign;

    @InjectMocks
    private OrdenService ordenService;

    private Orden orden;
    private EstadoOrden estadoOrden;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock objects
        estadoOrden = new EstadoOrden();
        estadoOrden.setId(2L);
        estadoOrden.setNombre("CONFIRMADA");

        orden = new Orden();
        orden.setId(1L);
        orden.setUsuarioId(100L);
        orden.setTotal(500);
        orden.setFechaCreacion(LocalDateTime.now());
        orden.setEstadoOrden(estadoOrden);
    }

    @Test
    @DisplayName("findAll debe retornar lista de ordenes con exito")
    void testFindAllSuccess() {
        List<Orden> ordenes = new ArrayList<>();
        ordenes.add(orden);

        when(ordenRepository.findAll()).thenReturn(ordenes);

        List<Orden> result = ordenService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(ordenRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll debe retornar lista vacia cuando no existen ordenes")
    void testFindAllEmpty() {
        List<Orden> ordenes = new ArrayList<>();

        when(ordenRepository.findAll()).thenReturn(ordenes);

        List<Orden> result = ordenService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(ordenRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById debe retornar orden cuando existe")
    void testFindByIdSuccess() {
        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));

        Orden result = ordenService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getUsuarioId());
        assertEquals(500, result.getTotal());
        verify(ordenRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById debe retornar null cuando orden no existe")
    void testFindByIdNotFound() {
        when(ordenRepository.findById(999L)).thenReturn(Optional.empty());

        Orden result = ordenService.findById(999L);

        assertNull(result);
        verify(ordenRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("save debe crear orden exitosamente")
    void testSaveSuccess() {
        when(ordenRepository.save(any(Orden.class))).thenReturn(orden);

        Orden result = ordenService.save(orden);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getUsuarioId());
        verify(ordenRepository, times(1)).save(any(Orden.class));
    }

    @Test
    @DisplayName("delete debe eliminar orden exitosamente")
    void testDeleteSuccess() {
        ordenService.delete(1L);

        verify(ordenRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("update debe actualizar orden exitosamente")
    void testUpdateSuccess() {
        EstadoOrden estadoNuevo = new EstadoOrden();
        estadoNuevo.setId(3L);
        estadoNuevo.setNombre("ENVIADA");

        Orden ordenToUpdate = new Orden();
        ordenToUpdate.setUsuarioId(100L);
        ordenToUpdate.setTotal(600);
        ordenToUpdate.setEstadoOrden(estadoNuevo);

        Orden ordenUpdated = new Orden();
        ordenUpdated.setId(1L);
        ordenUpdated.setUsuarioId(100L);
        ordenUpdated.setTotal(600);
        ordenUpdated.setFechaCreacion(LocalDateTime.now());
        ordenUpdated.setEstadoOrden(estadoNuevo);

        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));
        when(ordenRepository.save(any(Orden.class))).thenReturn(ordenUpdated);

        Orden result = ordenService.update(1L, ordenToUpdate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(600, result.getTotal());
        assertEquals("ENVIADA", result.getEstadoOrden().getNombre());
        verify(ordenRepository, times(1)).findById(1L);
        verify(ordenRepository, times(1)).save(any(Orden.class));
    }

    @Test
    @DisplayName("obtenerItemsPorUsuario debe retornar items del carrito")
    void testObtenerItemsPorUsuario() {
        List<ItemCarritoDTO> items = new ArrayList<>();
        ItemCarritoDTO item1 = new ItemCarritoDTO();
        item1.setProductoId(1L);
        item1.setCantidad(2);
        items.add(item1);

        when(carritoFeign.obtenerItemsPorUsuario(100L)).thenReturn(items);

        List<ItemCarritoDTO> result = ordenService.obtenerItemsPorUsuario(100L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getProductoId());
        assertEquals(2, result.get(0).getCantidad());
        verify(carritoFeign, times(1)).obtenerItemsPorUsuario(100L);
    }

    @Test
    @DisplayName("verOrdenPagada debe retornar detalles de pago")
    void testVerOrdenPagada() {
        PagoDTO pago = new PagoDTO();
        pago.setMonto(500);
        pago.setMetodoPago("TARJETA_CREDITO");
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstadoPago("APROBADO");

        when(pagoFeign.validarOrdenPagada(1L)).thenReturn(pago);

        PagoDTO result = ordenService.verOrdenPagada(1L);

        assertNotNull(result);
        assertEquals(500, result.getMonto());
        assertEquals("TARJETA_CREDITO", result.getMetodoPago());
        assertEquals("APROBADO", result.getEstadoPago());
        verify(pagoFeign, times(1)).validarOrdenPagada(1L);
    }
}
