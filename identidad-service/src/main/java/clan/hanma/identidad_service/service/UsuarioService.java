package clan.hanma.identidad_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.identidad_service.dto.UsuarioDTO;
import clan.hanma.identidad_service.mapper.UsuarioMapper;
import clan.hanma.identidad_service.model.Usuario;
import clan.hanma.identidad_service.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper mapper;

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario u: usuarios) {
            dtos.add(mapper.toDTO(u));
        }
        return dtos;
    }

    public UsuarioDTO findById(Long id) {
        Usuario u = usuarioRepository.findById(id).orElse(null);
        return mapper.toDTO(u);
    }

    public Usuario save(Usuario u) {
        return usuarioRepository.save(u);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario u = usuarioRepository.findById(id).orElse(null);
        u.setNombre(usuario.getNombre());
        u.setApellido(usuario.getApellido());
        u.setEmail(usuario.getEmail());
        u.setFechaRegistro(usuario.getFechaRegistro());
        u.setTelefono(usuario.getTelefono());
        u.setPassword(usuario.getPassword());
        u.setRol(usuario.getRol());
        return usuarioRepository.save(u);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> findByRol(Long id) {
        return usuarioRepository.findByRol(id);
    }

    public boolean emailExists(String email) {
        if (usuarioRepository.findByEmail(email) == null) return false;
        return true;
    }

}
