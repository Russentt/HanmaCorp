package clan.hanma.logistica_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.logistica_service.model.DireccionEntrega;
import clan.hanma.logistica_service.repository.DireccionEntregaRepository;

@Service
public class DireccionEntregaService {

    @Autowired
    private DireccionEntregaRepository direccionRepository;

    public List<DireccionEntrega> findAll() {
        return direccionRepository.findAll();
    }

    public DireccionEntrega findById(Long id) {
        return direccionRepository.findById(id).orElse(null);
    }

    public DireccionEntrega save(DireccionEntrega d) {
        return direccionRepository.save(d);
    }

    public void delete(Long id) {
        direccionRepository.deleteById(id);
    }

    public DireccionEntrega update(Long id, DireccionEntrega d) {
        DireccionEntrega dir = direccionRepository.findById(id).orElse(null);
        dir.setCalle(d.getCalle());
        dir.setCodigoPostal(d.getCodigoPostal());
        dir.setComuna(d.getComuna());
        dir.setNumero(d.getNumero());
        dir.setReferencia(d.getReferencia());
        dir.setDepartamento(d.getDepartamento());
        direccionRepository.save(dir);
        return dir;
    }
}
