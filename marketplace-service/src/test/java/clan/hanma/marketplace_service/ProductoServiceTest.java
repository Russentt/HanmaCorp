package clan.hanma.marketplace_service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import clan.hanma.marketplace_service.dto.ProductoDTO;
import clan.hanma.marketplace_service.mapper.ProductoMapper;
import clan.hanma.marketplace_service.model.Categoria;
import clan.hanma.marketplace_service.model.Producto;
import clan.hanma.marketplace_service.repository.CategoriaRepository;
import clan.hanma.marketplace_service.repository.ProductoRepository;
import clan.hanma.marketplace_service.service.ProductoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de Productos")
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper mapper;

    private ProductoService productoService;

    private Producto productoMock;
    private Producto productoMock2;
    private ProductoDTO productoDTOMock;
    private Categoria categoriaMock;

    @BeforeEach
    void setup() {
        // Create service instance with no-arg constructor
        productoService = new ProductoService();
        
        // Set the mocked dependencies using reflection
        ReflectionTestUtils.setField(productoService, "productoRepository", productoRepository);
        ReflectionTestUtils.setField(productoService, "mapper", mapper);

        categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        categoriaMock.setNombre("Electronica");
        categoriaMock.setDescripcion("Productos electronicos");

        productoMock = new Producto();
        productoMock.setId(1L);
        productoMock.setNombre("Laptop");
        productoMock.setDescripcion("Laptop de alto rendimiento");
        productoMock.setPrecio(1200.00);
        productoMock.setStock(10L);
        productoMock.setCategoria(categoriaMock);

        productoMock2 = new Producto();
        productoMock2.setId(2L);
        productoMock2.setNombre("Mouse");
        productoMock2.setDescripcion("Mouse inalambrico");
        productoMock2.setPrecio(25.00);
        productoMock2.setStock(50L);
        productoMock2.setCategoria(categoriaMock);

        productoDTOMock = new ProductoDTO();
        productoDTOMock.setNombre(productoMock.getNombre());
        productoDTOMock.setDescripcion(productoMock.getDescripcion());
        productoDTOMock.setPrecio(productoMock.getPrecio());
        productoDTOMock.setStock(productoMock.getStock());
    }

    @Test
    @DisplayName("findAll() -> Debe retornar una lista completa de productos")
    void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(productoMock, productoMock2));

        List<Producto> resultado = productoService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Laptop", resultado.get(0).getNombre());
        assertEquals("Mouse", resultado.get(1).getNombre());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll() -> Debe retornar una lista vacia cuando no hay productos")
    void testFindAllEmpty() {
        when(productoRepository.findAll()).thenReturn(List.of());

        List<Producto> resultado = productoService.findAll();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() -> Debe retornar el producto encontrado por ID")
    void testFindById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));

        Producto resultado = productoService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Laptop", resultado.getNombre());
        assertEquals(1200.00, resultado.getPrecio());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById() -> Debe retornar null cuando el producto no existe")
    void testFindByIdNotFound() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        Producto resultado = productoService.findById(999L);

        assertNull(resultado);
        verify(productoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("save() -> Debe guardar un nuevo producto exitosamente")
    void testSave() {
        when(productoRepository.save(productoMock)).thenReturn(productoMock);

        Producto resultado = productoService.save(productoMock);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        assertEquals(1200.00, resultado.getPrecio());
        verify(productoRepository, times(1)).save(productoMock);
    }

    @Test
    @DisplayName("delete() -> Debe eliminar un producto por ID")
    void testDelete() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.delete(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("update() -> Debe actualizar un producto existente")
    void testUpdate() {
        Producto productoActualizado = new Producto();
        productoActualizado.setId(1L);
        productoActualizado.setNombre("Laptop Gaming");
        productoActualizado.setDescripcion("Laptop para gaming");
        productoActualizado.setPrecio(1500.00);
        productoActualizado.setStock(5L);
        productoActualizado.setCategoria(categoriaMock);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = productoService.update(1L, productoActualizado);

        assertNotNull(resultado);
        assertEquals("Laptop Gaming", resultado.getNombre());
        assertEquals(1500.00, resultado.getPrecio());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("findByCategoriaId() -> Debe retornar productos de una categoria especifica")
    void testFindByCategoriaId() {
        when(productoRepository.findByCategoriaId(1L)).thenReturn(List.of(productoMock, productoMock2));

        List<Producto> resultado = productoService.findByCategoriaId(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findByCategoriaId(1L);
    }

    @Test
    @DisplayName("findByTiendaId() -> Debe retornar productos de una tienda especifica")
    void testFindByTiendaId() {
        when(productoRepository.findByTiendaId(1L)).thenReturn(List.of(productoMock));

        List<Producto> resultado = productoService.findByTiendaId(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoRepository, times(1)).findByTiendaId(1L);
    }

    @Test
    @DisplayName("findByStock() -> Debe retornar productos con stock especifico")
    void testFindByStock() {
        when(productoRepository.findByStock(50)).thenReturn(List.of(productoMock2));

        List<Producto> resultado = productoService.findByStock(50);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(50L, resultado.get(0).getStock());
        verify(productoRepository, times(1)).findByStock(50);
    }

    @Test
    @DisplayName("findStock() -> Debe retornar el stock de un producto")
    void testFindStock() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(mapper.toDTO(productoMock)).thenReturn(productoDTOMock);

        Long resultado = productoService.findStock(1L);

        assertNotNull(resultado);
        assertEquals(10L, resultado);
        verify(productoRepository, times(1)).findById(1L);
        verify(mapper, times(1)).toDTO(productoMock);
    }

    @Test
    @DisplayName("findByPrice() -> Debe retornar productos dentro del rango de precio")
    void testFindByPrice() {
        when(productoRepository.findByPrice(20, 1300)).thenReturn(List.of(productoMock, productoMock2));

        List<Producto> resultado = productoService.findByPrice(20, 1300);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository, times(1)).findByPrice(20, 1300);
    }

    @Test
    @DisplayName("findByIdDto() -> Debe retornar DTO del producto encontrado")
    void testFindByIdDto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(mapper.toDTO(productoMock)).thenReturn(productoDTOMock);

        ProductoDTO resultado = productoService.findByIdDto(1L);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        verify(productoRepository, times(1)).findById(1L);
        verify(mapper, times(1)).toDTO(productoMock);
    }

    @Test
    @DisplayName("reservarStock() -> Debe disminuir el stock del producto")
    void testReservarStock() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDTO(any(Producto.class))).thenReturn(productoDTOMock);

        ProductoDTO resultado = productoService.reservarStock(1L, 3);

        assertNotNull(resultado);
        assertEquals(7L, productoMock.getStock());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
        verify(mapper, times(1)).toDTO(any(Producto.class));
    }

    @Test
    @DisplayName("liberarStock() -> Debe incrementar el stock del producto")
    void testLiberarStock() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDTO(any(Producto.class))).thenReturn(productoDTOMock);

        ProductoDTO resultado = productoService.liberarStock(1L, 5);

        assertNotNull(resultado);
        assertEquals(15L, productoMock.getStock());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
        verify(mapper, times(1)).toDTO(any(Producto.class));
    }

}
