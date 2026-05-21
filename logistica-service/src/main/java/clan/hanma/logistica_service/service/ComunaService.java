package clan.hanma.logistica_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.logistica_service.model.Comuna;
import clan.hanma.logistica_service.repository.ComunaRepository;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Comuna findById(Long id) {
        return comunaRepository.findById(id).orElse(null);
    }

    public Comuna save(Comuna c) {
        return comunaRepository.save(c);
    }

    public void delete(Long id) {
        comunaRepository.deleteById(id);
    }

    public Comuna update(Long id, Comuna c) {
        Comuna com = comunaRepository.findById(id).orElse(null);
        com.setNombre(c.getNombre());
        com.setRegion(c.getRegion());
        comunaRepository.save(com);
        return com;
    }

}
