package clan.hanma.marketplace_service.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.marketplace_service.model.Tienda;
import clan.hanma.marketplace_service.repository.TiendaRepository;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> findAll() {
        return tiendaRepository.findAll();
    }

    public Tienda findById(Long id) {
        return tiendaRepository.findById(id).orElse(null);
    }

    public Tienda save(Tienda t) {
        return tiendaRepository.save(t);
    }

    public void delete(Long id) {
        tiendaRepository.deleteById(id);
    }

    public Tienda update(Long id, Tienda t) {
        Tienda tnda = tiendaRepository.findById(id).orElse(null);
        tnda.setActiva(t.isActiva());
        tnda.setNombre(t.getNombre());
        tnda.setDescripcion(t.getDescripcion());
        tnda.setReputacion(t.getReputacion());
        tiendaRepository.save(tnda);
        return tnda;
    }

}
