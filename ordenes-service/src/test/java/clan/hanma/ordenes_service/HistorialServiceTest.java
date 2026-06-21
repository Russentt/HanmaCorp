package clan.hanma.ordenes_service;

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

import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.model.HistorialEstadoOrden;
import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.repository.HistorialRepository;
import clan.hanma.ordenes_service.service.HistorialService;

@ExtendWith(MockitoExtension.class)
@DisplayName("HistorialService Test Suite")
class HistorialServiceTest {

    @Mock
    private HistorialRepository historialRepository;

    @InjectMocks
    private HistorialService historialService;

    private Orden orden;
    private EstadoOrden estadoOrdenActual;
    private EstadoOrden estadoOrdenAnterior;
    private HistorialEstadoOrden historialMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock objects
        orden = new Orden();
        orden.setId(1L);
        orden.setUsuarioId(100L);
        orden.setTotal(150);
        orden.setFechaCreacion(LocalDateTime.now());

        estadoOrdenActual = new EstadoOrden();
        estadoOrdenActual.setId(2L);
        estadoOrdenActual.setNombre("CONFIRMADA");

        estadoOrdenAnterior = new EstadoOrden();
        estadoOrdenAnterior.setId(1L);
        estadoOrdenAnterior.setNombre("PENDIENTE");

        historialMock = new HistorialEstadoOrden();
        historialMock.setId(1L);
        historialMock.setOrden(orden);
        historialMock.setEstadoOrdenActual(estadoOrdenActual);
        historialMock.setEstadoOrdenAnterior(estadoOrdenAnterior);
    }

    @Test
    @DisplayName("findAll debe retornar lista de historiales con exito")
    void testFindAllSuccess() {
        List<HistorialEstadoOrden> historiales = new ArrayList<>();
        historiales.add(historialMock);

        when(historialRepository.findAll()).thenReturn(historiales);

        List<HistorialEstadoOrden> result = historialService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(historialRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll debe retornar lista vacia cuando no existen historiales")
    void testFindAllEmpty() {
        List<HistorialEstadoOrden> historiales = new ArrayList<>();

        when(historialRepository.findAll()).thenReturn(historiales);

        List<HistorialEstadoOrden> result = historialService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(historialRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById debe retornar historial cuando existe")
    void testFindByIdSuccess() {
        when(historialRepository.findById(1L)).thenReturn(Optional.of(historialMock));

        HistorialEstadoOrden result = historialService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PENDIENTE", result.getEstadoOrdenAnterior().getNombre());
        assertEquals("CONFIRMADA", result.getEstadoOrdenActual().getNombre());
        verify(historialRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById debe retornar null cuando historial no existe")
    void testFindByIdNotFound() {
        when(historialRepository.findById(999L)).thenReturn(Optional.empty());

        HistorialEstadoOrden result = historialService.findById(999L);

        assertNull(result);
        verify(historialRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("save debe crear historial exitosamente")
    void testSaveSuccess() {
        when(historialRepository.save(any(HistorialEstadoOrden.class))).thenReturn(historialMock);

        HistorialEstadoOrden result = historialService.save(historialMock);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getOrden().getId());
        verify(historialRepository, times(1)).save(any(HistorialEstadoOrden.class));
    }

    @Test
    @DisplayName("delete debe eliminar historial exitosamente")
    void testDeleteSuccess() {
        historialService.delete(1L);

        verify(historialRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("update debe actualizar historial exitosamente")
    void testUpdateSuccess() {
        EstadoOrden estadoOrdenNuevo = new EstadoOrden();
        estadoOrdenNuevo.setId(3L);
        estadoOrdenNuevo.setNombre("ENVIADA");

        HistorialEstadoOrden historialToUpdate = new HistorialEstadoOrden();
        historialToUpdate.setOrden(orden);
        historialToUpdate.setEstadoOrdenActual(estadoOrdenNuevo);
        historialToUpdate.setEstadoOrdenAnterior(estadoOrdenActual);

        HistorialEstadoOrden historialUpdated = new HistorialEstadoOrden();
        historialUpdated.setId(1L);
        historialUpdated.setOrden(orden);
        historialUpdated.setEstadoOrdenActual(estadoOrdenNuevo);
        historialUpdated.setEstadoOrdenAnterior(estadoOrdenActual);

        when(historialRepository.findById(1L)).thenReturn(Optional.of(historialMock));
        when(historialRepository.save(any(HistorialEstadoOrden.class))).thenReturn(historialUpdated);

        HistorialEstadoOrden result = historialService.update(1L, historialToUpdate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ENVIADA", result.getEstadoOrdenActual().getNombre());
        verify(historialRepository, times(1)).findById(1L);
        verify(historialRepository, times(1)).save(any(HistorialEstadoOrden.class));
    }
}
