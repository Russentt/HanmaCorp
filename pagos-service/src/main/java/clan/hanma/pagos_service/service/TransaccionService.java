package clan.hanma.pagos_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.pagos_service.model.Transaccion;
import clan.hanma.pagos_service.repository.TransaccionRepository;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    public Transaccion findById(Long id) {
        return transaccionRepository.findById(id).orElse(null);
    }

    public Transaccion save(Transaccion t) {
        return transaccionRepository.save(t);
    }

    public void delete(Long id) {
        transaccionRepository.deleteById(id);
    }

    public Transaccion update(Long id, Transaccion t) {
        Transaccion tran = transaccionRepository.findById(id).orElse(null);
        tran.setRespuestaPasarela(t.getRespuestaPasarela());
        tran.setCodigoTransaccion(t.getCodigoTransaccion());
        transaccionRepository.save(tran);
        return tran;
    }


}
