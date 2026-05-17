package clan.hanma.resenas_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.resenas_service.model.Resena;
import clan.hanma.resenas_service.repository.ResenaRepository;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public List<Resena> findAll() {
        return resenaRepository.findAll();
    }

    public Resena findById(Long id) {
        return resenaRepository.findById(id).orElse(null);
    }

    public Resena save(Resena r) {
        return resenaRepository.save(r);
    }

    public void delete(Long id) {
        resenaRepository.deleteById(id);
    }

    public Resena update(Long id, Resena r) {
        Resena res = resenaRepository.findById(id).orElse(null);
        res.setVisible(r.isVisible());
        res.setPuntuacion(r.getPuntuacion());
        res.setTitulo(r.getTitulo());
        res.setComentario(r.getComentario());
        resenaRepository.save(res);
        return res;
    }

}
