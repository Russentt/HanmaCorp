package clan.hanma.inventario_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.inventario_service.model.Bodega;
import clan.hanma.inventario_service.repository.BodegaRepository;

@Service
public class BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    public List<Bodega> findAll() {
        return bodegaRepository.findAll();
    }

    public Bodega findById(Long id) {
        return bodegaRepository.findById(id).orElse(null);
    }

    public Bodega save(Bodega b) {
        return bodegaRepository.save(b);
    }

    public void delete(Long id) {
        bodegaRepository.deleteById(id);
    }

    public Bodega update(Long id, Bodega b) {
        Bodega bod = bodegaRepository.findById(id).orElse(null);
        bod.setNombre(b.getNombre());
        bod.setDireccion(b.getDireccion());
        bodegaRepository.save(bod);
        return bod;
    }

}
