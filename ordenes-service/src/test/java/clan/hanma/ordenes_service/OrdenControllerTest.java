package clan.hanma.ordenes_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import clan.hanma.ordenes_service.controller.OrdenController;
import clan.hanma.ordenes_service.dto.ItemCarritoDTO;
import clan.hanma.ordenes_service.dto.PagoDTO;
import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.service.OrdenService;

@WebMvcTest(OrdenController.class)
@DisplayName("OrdenController Test Suite")
class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrdenService ordenService;

    private ObjectMapper objectMapper;
    private Orden orden;
    private EstadoOrden estadoOrden;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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
    @DisplayName("testFindAll debe retornar todas las ordenes")
    void testFindAll() throws Exception {
        List<Orden> ordenes = new ArrayList<>();
        ordenes.add(orden);

        when(ordenService.findAll()).thenReturn(ordenes);

        mockMvc.perform(get("/ordenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].usuarioId").value(100))
                .andExpect(jsonPath("$[0].total").value(500));

        verify(ordenService, times(1)).findAll();
    }

    @Test
    @DisplayName("testFindById debe retornar orden por ID")
    void testFindById() throws Exception {
        when(ordenService.findById(1L)).thenReturn(orden);

        mockMvc.perform(get("/ordenes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioId").value(100))
                .andExpect(jsonPath("$.total").value(500));

        verify(ordenService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("testSave debe crear nueva orden")
    void testSave() throws Exception {
        when(ordenService.save(any(Orden.class))).thenReturn(orden);

        mockMvc.perform(post("/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioId").value(100));

        verify(ordenService, times(1)).save(any(Orden.class));
    }

    @Test
    @DisplayName("testDelete debe eliminar orden por ID")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/ordenes/1"))
                .andExpect(status().isNoContent());

        verify(ordenService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("testUpdate debe actualizar orden por ID")
    void testUpdate() throws Exception {
        EstadoOrden estadoNuevo = new EstadoOrden();
        estadoNuevo.setId(3L);
        estadoNuevo.setNombre("ENVIADA");

        Orden ordenUpdated = new Orden();
        ordenUpdated.setId(1L);
        ordenUpdated.setUsuarioId(100L);
        ordenUpdated.setTotal(600);
        ordenUpdated.setFechaCreacion(LocalDateTime.now());
        ordenUpdated.setEstadoOrden(estadoNuevo);

        when(ordenService.update(anyLong(), any(Orden.class))).thenReturn(ordenUpdated);

        mockMvc.perform(put("/ordenes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordenUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(600));

        verify(ordenService, times(1)).update(anyLong(), any(Orden.class));
    }

    @Test
    @DisplayName("testObtenerItemsPorUsuario debe retornar items del carrito")
    void testObtenerItemsPorUsuario() throws Exception {
        List<ItemCarritoDTO> items = new ArrayList<>();
        ItemCarritoDTO item1 = new ItemCarritoDTO();
        item1.setProductoId(1L);
        item1.setCantidad(2);
        items.add(item1);

        when(ordenService.obtenerItemsPorUsuario(100L)).thenReturn(items);

        mockMvc.perform(get("/ordenes/items/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productoId").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2));

        verify(ordenService, times(1)).obtenerItemsPorUsuario(100L);
    }

    @Test
    @DisplayName("testVerOrdenPagada debe retornar detalles de pago")
    void testVerOrdenPagada() throws Exception {
        PagoDTO pago = new PagoDTO();
        pago.setMonto(500);
        pago.setMetodoPago("TARJETA_CREDITO");
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstadoPago("APROBADO");

        when(ordenService.verOrdenPagada(1L)).thenReturn(pago);

        mockMvc.perform(get("/ordenes/pago/aprobado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(500))
                .andExpect(jsonPath("$.metodoPago").value("TARJETA_CREDITO"))
                .andExpect(jsonPath("$.estadoPago").value("APROBADO"));

        verify(ordenService, times(1)).verOrdenPagada(1L);
    }
}
