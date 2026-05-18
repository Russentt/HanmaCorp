package clan.hanma.pagos_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clan.hanma.pagos_service.model.EstadoPago;
import clan.hanma.pagos_service.repository.EstadoPagoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoPagoService {

    @Autowired
    private EstadoPagoRepository estadoRepository;

    public List<EstadoPago> findAll() {
        return estadoRepository.findAll();
    }

    public EstadoPago findById(Long id) {
        return estadoRepository.findById(id).orElse(null);
    }

    public EstadoPago save(EstadoPago estP) {
        return estadoRepository.save(estP);
    }

    public void delete(Long id) {
        estadoRepository.deleteById(id);
    }

    public EstadoPago update(Long id, EstadoPago estP) {
        EstadoPago est = estadoRepository.findById(id).orElse(null);
        est.setNombre(estP.getNombre());
        estadoRepository.save(est);
        return est;
    }

}
