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

import clan.hanma.marketplace_service.model.Categoria;
import clan.hanma.marketplace_service.repository.CategoriaRepository;
import clan.hanma.marketplace_service.service.CategoriaService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de Categorias")
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoriaMock;
    private Categoria categoriaMock2;

    @BeforeEach
    void setup() {
        categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        categoriaMock.setNombre("Electronica");
        categoriaMock.setDescripcion("Productos electronicos en general");

        categoriaMock2 = new Categoria();
        categoriaMock2.setId(2L);
        categoriaMock2.setNombre("Ropa");
        categoriaMock2.setDescripcion("Prendas de vestir y accesorios");
    }

    @Test
    @DisplayName("findAll() -> Debe retornar una lista completa de categorias")
    void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoriaMock, categoriaMock2));

        List<Categoria> resultado = categoriaService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Electronica", resultado.get(0).getNombre());
        assertEquals("Ropa", resultado.get(1).getNombre());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll() -> Debe retornar una lista vacia cuando no hay categorias")
    void testFindAllEmpty() {
        when(categoriaRepository.findAll()).thenReturn(List.of());

        List<Categoria> resultado = categoriaService.findAll();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() -> Debe retornar la categoria encontrada por ID")
    void testFindById() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));

        Categoria resultado = categoriaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Electronica", resultado.getNombre());
        assertEquals("Productos electronicos en general", resultado.getDescripcion());
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById() -> Debe retornar null cuando la categoria no existe")
    void testFindByIdNotFound() {
        when(categoriaRepository.findById(999L)).thenReturn(Optional.empty());

        Categoria resultado = categoriaService.findById(999L);

        assertNull(resultado);
        verify(categoriaRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("save() -> Debe guardar una nueva categoria exitosamente")
    void testSave() {
        when(categoriaRepository.save(categoriaMock)).thenReturn(categoriaMock);

        Categoria resultado = categoriaService.save(categoriaMock);

        assertNotNull(resultado);
        assertEquals("Electronica", resultado.getNombre());
        assertEquals("Productos electronicos en general", resultado.getDescripcion());
        verify(categoriaRepository, times(1)).save(categoriaMock);
    }

    @Test
    @DisplayName("delete() -> Debe eliminar una categoria por ID")
    void testDelete() {
        doNothing().when(categoriaRepository).deleteById(1L);

        categoriaService.delete(1L);

        verify(categoriaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("update() -> Debe actualizar una categoria existente")
    void testUpdate() {
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setId(1L);
        categoriaActualizada.setNombre("Electronica Premium");
        categoriaActualizada.setDescripcion("Productos electronicos de lujo");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Categoria resultado = categoriaService.update(1L, categoriaActualizada);

        assertNotNull(resultado);
        assertEquals("Electronica Premium", resultado.getNombre());
        assertEquals("Productos electronicos de lujo", resultado.getDescripcion());
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

}
