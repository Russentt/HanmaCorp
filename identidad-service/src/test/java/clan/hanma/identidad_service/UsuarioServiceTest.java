package clan.hanma.identidad_service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import clan.hanma.identidad_service.dto.UsuarioDTO;
import clan.hanma.identidad_service.exception.BadRequestException;
import clan.hanma.identidad_service.exception.ResourceNotFoundException;
import clan.hanma.identidad_service.mapper.UsuarioMapper;
import clan.hanma.identidad_service.model.Rol;
import clan.hanma.identidad_service.model.Usuario;
import clan.hanma.identidad_service.repository.UsuarioRepository;
import clan.hanma.identidad_service.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas en la capa de servicio de Usuarios")
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;
    private Usuario usuarioMock2;
    private UsuarioDTO usuarioDTOMock;
    private Rol rolMock;

    @BeforeEach
    void setup() {
        rolMock = new Rol();
        rolMock.setId(1L);
        rolMock.setNombre("Cliente");
        rolMock.setDescripcion("Un cliente promedio");

        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setNombre("Juan");
        usuarioMock.setApellido("Perez");
        usuarioMock.setEmail("juan@gmail.com");
        usuarioMock.setPassword("pass123");
        usuarioMock.setTelefono("+56912345678");
        usuarioMock.setFechaRegistro(LocalDate.now());
        usuarioMock.setRol(rolMock);

        usuarioMock2 = new Usuario();
        usuarioMock2.setId(2L);
        usuarioMock2.setNombre("Maria");
        usuarioMock2.setApellido("Garcia");
        usuarioMock2.setEmail("maria@gmail.com");
        usuarioMock2.setPassword("pass456");
        usuarioMock2.setTelefono("+56987654321");
        usuarioMock2.setFechaRegistro(LocalDate.now());
        usuarioMock2.setRol(rolMock);

        usuarioDTOMock = new UsuarioDTO();
        usuarioDTOMock.setEmail(usuarioMock.getEmail());
        usuarioDTOMock.setNombreCompleto(usuarioMock.getNombre());
        usuarioDTOMock.setNombreRol(rolMock.getNombre());
        usuarioDTOMock.setTelefono(usuarioMock.getTelefono());
    }

    @Test
    @DisplayName("findAll() -> Debe retornar lista de DTOs de todos los usuarios")
    void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioMock, usuarioMock2));
        when(mapper.toDTO(usuarioMock)).thenReturn(usuarioDTOMock);
        when(mapper.toDTO(usuarioMock2)).thenReturn(usuarioDTOMock);

        List<UsuarioDTO> resultado = usuarioService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(2)).findAll();
        verify(mapper, times(2)).toDTO(any(Usuario.class));
    }

    @Test
    @DisplayName("findAll() -> Debe retornar lista vacia cuando no hay usuarios")
    void testFindAllEmpty() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<UsuarioDTO> resultado = usuarioService.findAll();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(usuarioRepository, times(2)).findAll();
    }

    @Test
    @DisplayName("findById() -> Debe retornar DTO del usuario encontrado")
    void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(mapper.toDTO(usuarioMock)).thenReturn(usuarioDTOMock);

        UsuarioDTO resultado = usuarioService.findById(1L);

        assertNotNull(resultado);
        assertEquals(usuarioDTOMock.getEmail(), resultado.getEmail());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(mapper, times(1)).toDTO(usuarioMock);
    }

    @Test
    @DisplayName("findById() -> Debe lanzar excepcion cuando usuario no existe")
    void testFindByIdNotFound() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findById(999L));
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("save() -> Debe lanzar excepcion porque findById siempre retorna Optional no-null")
    void testSave() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setId(3L);
        nuevoUsuario.setNombre("Carlos");
        nuevoUsuario.setEmail("carlos@gmail.com");

        when(usuarioRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> usuarioService.save(nuevoUsuario));
    }

    @Test
    @DisplayName("save() -> Debe lanzar excepcion si usuario ya existe")
    void testSaveAlreadyExists() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));

        assertThrows(BadRequestException.class, () -> usuarioService.save(usuarioMock));
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete() -> Debe eliminar un usuario exitosamente")
    void testDelete() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.delete(1L);

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete() -> Debe lanzar excepcion cuando usuario no existe")
    void testDeleteNotFound() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.delete(999L));
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("update() -> Debe actualizar un usuario existente")
    void testUpdate() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Juan Updated");
        usuarioActualizado.setApellido("Perez Updated");
        usuarioActualizado.setEmail("juanupdated@gmail.com");
        usuarioActualizado.setPassword("newpass");
        usuarioActualizado.setTelefono("+56911111111");
        usuarioActualizado.setFechaRegistro(LocalDate.now());
        usuarioActualizado.setRol(rolMock);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        Usuario resultado = usuarioService.update(1L, usuarioActualizado);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("update() -> Debe lanzar excepcion cuando usuario no existe")
    void testUpdateNotFound() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.update(999L, usuarioMock));
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("findByEmail() -> Debe retornar usuario encontrado por email")
    void testFindByEmail() {
        when(usuarioRepository.findByEmail("juan@gmail.com")).thenReturn(usuarioMock);

        Usuario resultado = usuarioService.findByEmail("juan@gmail.com");

        assertNotNull(resultado);
        assertEquals("juan@gmail.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail("juan@gmail.com");
    }

    @Test
    @DisplayName("findByEmail() -> Debe lanzar excepcion cuando email no existe")
    void testFindByEmailNotFound() {
        when(usuarioRepository.findByEmail("noexiste@gmail.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findByEmail("noexiste@gmail.com"));
        verify(usuarioRepository, times(1)).findByEmail("noexiste@gmail.com");
    }

    @Test
    @DisplayName("findByRol() -> Debe retornar lista de usuarios con rol especifico")
    void testFindByRol() {
        when(usuarioRepository.findByRol(1L)).thenReturn(List.of(usuarioMock, usuarioMock2));

        List<Usuario> resultado = usuarioService.findByRol(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(2)).findByRol(1L);
    }

    @Test
    @DisplayName("findByRol() -> Debe lanzar excepcion cuando rol no existe")
    void testFindByRolNotFound() {
        when(usuarioRepository.findByRol(999L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findByRol(999L));
        verify(usuarioRepository, times(1)).findByRol(999L);
    }

    @Test
    @DisplayName("emailExists() -> Debe retornar true cuando email existe")
    void testEmailExists() {
        when(usuarioRepository.findByEmail("juan@gmail.com")).thenReturn(usuarioMock);

        boolean resultado = usuarioService.emailExists("juan@gmail.com");

        assertTrue(resultado);
        verify(usuarioRepository, times(1)).findByEmail("juan@gmail.com");
    }

    @Test
    @DisplayName("emailExists() -> Debe retornar false cuando email no existe")
    void testEmailNotExists() {
        when(usuarioRepository.findByEmail("noexiste@gmail.com")).thenReturn(null);

        boolean resultado = usuarioService.emailExists("noexiste@gmail.com");

        assertFalse(resultado);
        verify(usuarioRepository, times(1)).findByEmail("noexiste@gmail.com");
    }

}
