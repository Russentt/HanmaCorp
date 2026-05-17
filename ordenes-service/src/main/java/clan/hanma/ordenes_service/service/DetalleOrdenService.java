package clan.hanma.ordenes_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.ordenes_service.model.DetalleOrden;
import clan.hanma.ordenes_service.repository.DetalleRepository;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleRepository detalleRepository;

    public List<DetalleOrden> findAll() {
        return detalleRepository.findAll();
    }

    public DetalleOrden findById(Long id) {
        return detalleRepository.findById(id).orElse(null);
    }

    public DetalleOrden save(DetalleOrden detalle) {
        return detalleRepository.save(detalle);
    }

    public void delete(Long id) {
        detalleRepository.deleteById(id);
    }

    // No se juzga relevante la implementacion del metodo update para la tabla DetalleOrden.

}
