package clan.hanma.ordenes_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.ordenes_service.model.EstadoOrden;
import clan.hanma.ordenes_service.repository.EstadoOrdenRepository;

@Service
public class EstadoOrdenService {

    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;

    public List<EstadoOrden> findAll() {
        return estadoOrdenRepository.findAll();
    }

    public EstadoOrden findById(Long id) {
        return estadoOrdenRepository.findById(id).orElse(null);
    }

    public EstadoOrden save(EstadoOrden eo) {
        return estadoOrdenRepository.save(eo);
    }

    public void delete(Long id) {
        estadoOrdenRepository.deleteById(id);
    }

    public EstadoOrden update(Long id, EstadoOrden eo) {
        EstadoOrden estado = estadoOrdenRepository.findById(id).orElse(null);
        estado.setNombre(eo.getNombre());
        estadoOrdenRepository.save(estado);
        return estado;
    }

}
