package clan.hanma.identidad_service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import clan.hanma.identidad_service.model.Rol;
import clan.hanma.identidad_service.repository.RolRepository;
import clan.hanma.identidad_service.service.RolService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de Roles")
public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rolMock;
    private Rol rolMock2;

    @BeforeEach
    void setup() {
        rolMock = new Rol();
        rolMock.setId(1L);
        rolMock.setNombre("Cliente");
        rolMock.setDescripcion("Un cliente promedio de Hanma Corporation");

        rolMock2 = new Rol();
        rolMock2.setId(2L);
        rolMock2.setNombre("Administrador");
        rolMock2.setDescripcion("Administrador del sistema");
    }

    @Test
    @DisplayName("findAll() -> Debe retornar una lista completa de roles")
    void testFindAll() {
        when(rolRepository.findAll()).thenReturn(List.of(rolMock, rolMock2));

        List<Rol> resultado = rolService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Cliente", resultado.get(0).getNombre());
        assertEquals("Administrador", resultado.get(1).getNombre());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll() -> Debe retornar una lista vacia cuando no hay roles")
    void testFindAllEmpty() {
        when(rolRepository.findAll()).thenReturn(List.of());

        List<Rol> resultado = rolService.findAll();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() -> Debe retornar el rol encontrado por ID")
    void testFindById() {
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rolMock));

        Rol resultado = rolService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Cliente", resultado.getNombre());
        assertEquals("Un cliente promedio de Hanma Corporation", resultado.getDescripcion());
        verify(rolRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById() -> Debe retornar null cuando el rol no existe")
    void testFindByIdNotFound() {
        when(rolRepository.findById(999L)).thenReturn(Optional.empty());

        Rol resultado = rolService.findById(999L);

        assertNull(resultado);
        verify(rolRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("save() -> Debe guardar un nuevo rol exitosamente")
    void testSave() {
        when(rolRepository.save(rolMock)).thenReturn(rolMock);

        Rol resultado = rolService.save(rolMock);

        assertNotNull(resultado);
        assertEquals("Cliente", resultado.getNombre());
        assertEquals("Un cliente promedio de Hanma Corporation", resultado.getDescripcion());
        verify(rolRepository, times(1)).save(rolMock);
    }

    @Test
    @DisplayName("delete() -> Debe eliminar un rol por ID")
    void testDelete() {
        doNothing().when(rolRepository).deleteById(1L);

        rolService.delete(1L);

        verify(rolRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("update() -> Debe actualizar un rol existente")
    void testUpdate() {
        Rol rolActualizado = new Rol();
        rolActualizado.setId(1L);
        rolActualizado.setNombre("Cliente Premium");
        rolActualizado.setDescripcion("Cliente con acceso premium");

        when(rolRepository.findById(1L)).thenReturn(Optional.of(rolMock));
        when(rolRepository.save(any(Rol.class))).thenReturn(rolActualizado);

        Rol resultado = rolService.update(1L, rolActualizado);

        assertNotNull(resultado);
        assertEquals("Cliente Premium", resultado.getNombre());
        assertEquals("Cliente con acceso premium", resultado.getDescripcion());
        verify(rolRepository, times(1)).findById(1L);
        verify(rolRepository, times(1)).save(any(Rol.class));
    }

}
