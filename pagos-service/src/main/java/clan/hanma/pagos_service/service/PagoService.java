package clan.hanma.pagos_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.pagos_service.model.Pago;
import clan.hanma.pagos_service.repository.PagoRepository;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public Pago findById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago save(Pago p) {
        return pagoRepository.save(p);
    }

    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    public Pago update(Long id, Pago p) {
        Pago pag = pagoRepository.findById(id).orElse(null);
        pag.setMetodoPago(p.getMetodoPago());
        pag.setMonto(p.getMonto());
        pag.setEstadoPago(p.getEstadoPago());
        pagoRepository.save(pag);
        return pag;
    }

}
