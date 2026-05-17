package clan.hanma.resenas_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.resenas_service.model.ReaccionResena;
import clan.hanma.resenas_service.repository.ReaccionResenaRepository;

@Service
public class ReaccionResenaService {

    @Autowired
    private ReaccionResenaRepository reaccionRepository;

    public List<ReaccionResena> findAll() {
        return reaccionRepository.findAll();
    }

    public ReaccionResena findById(Long id) {
        return reaccionRepository.findById(id).orElse(null);
    }

    public ReaccionResena save(ReaccionResena r) {
        return reaccionRepository.save(r);
    }

    public void delete(Long id) {
        reaccionRepository.deleteById(id);
    }

    public ReaccionResena update(Long id, ReaccionResena rr) {
        ReaccionResena reaccion = reaccionRepository.findById(id).orElse(null);
        reaccion.setTipo(rr.getTipo());
        reaccion.setResena(rr.getResena());
        reaccionRepository.save(reaccion);
        return reaccion;
    }

}
