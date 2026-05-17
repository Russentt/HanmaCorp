package clan.hanma.ordenes_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.ordenes_service.model.HistorialEstadoOrden;
import clan.hanma.ordenes_service.repository.HistorialRepository;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    public List<HistorialEstadoOrden> findAll() {
        return historialRepository.findAll();
    }

    public HistorialEstadoOrden findById(Long id) {
        return historialRepository.findById(id).orElse(null);
    }

    public HistorialEstadoOrden save(HistorialEstadoOrden h) {
        return historialRepository.save(h);
    }

    public void delete(Long id) {
        historialRepository.deleteById(id);
    }

    public HistorialEstadoOrden update(Long id, HistorialEstadoOrden h) {
        HistorialEstadoOrden hist = historialRepository.findById(id).orElse(null);
        hist.setOrden(h.getOrden());
        hist.setEstadoOrdenActual(h.getEstadoOrdenActual());
        hist.setEstadoOrdenAnterior(h.getEstadoOrdenAnterior());
        historialRepository.save(hist);
        return hist;
    }
}
