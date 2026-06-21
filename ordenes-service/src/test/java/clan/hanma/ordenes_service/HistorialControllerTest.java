package clan.hanma.ordenes_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import clan.hanma.ordenes_service.controller.HistorialController;
import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.model.HistorialEstadoOrden;
import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.service.HistorialService;

@WebMvcTest(HistorialController.class)
@DisplayName("HistorialController Test Suite")
class HistorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistorialService historialService;

    private ObjectMapper objectMapper;
    private Orden orden;
    private EstadoOrden estadoOrdenActual;
    private EstadoOrden estadoOrdenAnterior;
    private HistorialEstadoOrden historialMock;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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
    @DisplayName("testFindAll debe retornar todos los historiales")
    void testFindAll() throws Exception {
        List<HistorialEstadoOrden> historiales = new ArrayList<>();
        historiales.add(historialMock);

        when(historialService.findAll()).thenReturn(historiales);

        mockMvc.perform(get("/historial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].orden.id").value(1))
                .andExpect(jsonPath("$[0].estadoOrdenAnterior.nombre").value("PENDIENTE"))
                .andExpect(jsonPath("$[0].estadoOrdenActual.nombre").value("CONFIRMADA"));

        verify(historialService, times(1)).findAll();
    }

    @Test
    @DisplayName("testFindById debe retornar historial por ID")
    void testFindById() throws Exception {
        when(historialService.findById(1L)).thenReturn(historialMock);

        mockMvc.perform(get("/historial/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orden.id").value(1))
                .andExpect(jsonPath("$.estadoOrdenAnterior.nombre").value("PENDIENTE"))
                .andExpect(jsonPath("$.estadoOrdenActual.nombre").value("CONFIRMADA"));

        verify(historialService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("testSave debe crear nuevo historial")
    void testSave() throws Exception {
        when(historialService.save(any(HistorialEstadoOrden.class))).thenReturn(historialMock);

        mockMvc.perform(post("/historial")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historialMock)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orden.id").value(1));

        verify(historialService, times(1)).save(any(HistorialEstadoOrden.class));
    }

    @Test
    @DisplayName("testDelete debe eliminar historial por ID")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/historial/1"))
                .andExpect(status().isNoContent());

        verify(historialService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("testUpdate debe actualizar historial por ID")
    void testUpdate() throws Exception {
        EstadoOrden estadoOrdenNuevo = new EstadoOrden();
        estadoOrdenNuevo.setId(3L);
        estadoOrdenNuevo.setNombre("ENVIADA");

        HistorialEstadoOrden historialUpdated = new HistorialEstadoOrden();
        historialUpdated.setId(1L);
        historialUpdated.setOrden(orden);
        historialUpdated.setEstadoOrdenActual(estadoOrdenNuevo);
        historialUpdated.setEstadoOrdenAnterior(estadoOrdenActual);

        when(historialService.save(any(HistorialEstadoOrden.class))).thenReturn(historialUpdated);

        mockMvc.perform(put("/historial/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historialUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estadoOrdenActual.nombre").value("ENVIADA"));

        verify(historialService, times(1)).save(any(HistorialEstadoOrden.class));
    }
}
