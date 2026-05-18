package clan.hanma.pagos_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.pagos_service.model.Reembolso;
import clan.hanma.pagos_service.repository.ReembolsoRepository;

@Service
public class ReembolsoService {
    @Autowired
    private ReembolsoRepository reembolsoRepository;

    public List<Reembolso> findAll() {
        return reembolsoRepository.findAll();
    }

    public Reembolso findById(Long id) {
        return reembolsoRepository.findById(id).orElse(null);
    }

    public Reembolso save(Reembolso r) {
        return reembolsoRepository.save(r);
    }

    public void delete(Long id) {
        reembolsoRepository.deleteById(id);
    }

    public Reembolso update(Long id, Reembolso r) {
        Reembolso re = reembolsoRepository.findById(id).orElse(null);
        re.setMonto(r.getMonto());
        re.setMotivo(r.getMotivo());
        re.setPago(r.getPago());
        reembolsoRepository.save(re);
        return re;
    }

}
