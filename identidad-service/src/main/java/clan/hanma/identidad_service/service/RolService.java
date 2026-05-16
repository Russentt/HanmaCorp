package clan.hanma.identidad_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.identidad_service.model.Rol;
import clan.hanma.identidad_service.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol save(Rol r) {
        return rolRepository.save(r);
    }

    public void delete(Long id) {
        rolRepository.deleteById(id);
    }

    public Rol update(Long id, Rol r) {
        Rol rol = rolRepository.findById(id).orElse(null);
        rol.setNombre(r.getNombre());
        rol.setDescripcion(r.getDescripcion());
        return rolRepository.save(r);
    }

}
