package clan.hanma.ordenes_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.ordenes_service.model.Orden;
import clan.hanma.ordenes_service.repository.OrdenRepository;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    public Orden findById(Long id) {
        return ordenRepository.findById(id).orElse(null);
    }

    public Orden save(Orden o) {
        return ordenRepository.save(o);
    }

    public void delete(Long id) {
        ordenRepository.deleteById(id);
    }

    public Orden update(Long id, Orden o) {
        Orden ord = ordenRepository.findById(id).orElse(null);
        ord.setEstadoOrden(o.getEstadoOrden());
        ord.setTotal(o.getTotal());
        ordenRepository.save(ord);
        return ord;
    }

}
