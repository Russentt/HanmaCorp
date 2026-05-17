package clan.hanma.logistica_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.logistica_service.model.Envio;
import clan.hanma.logistica_service.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> findAll() {
        return envioRepository.findAll();
    }

    public Envio findById(Long id) {
        return envioRepository.findById(id).orElse(null);
    }

    public Envio save(Envio e) {
        return envioRepository.save(e);
    }

    public void delete(Long id) {
        envioRepository.deleteById(id);
    }

    public Envio update(Long id, Envio e) {
        Envio env = envioRepository.findById(id).orElse(null);
        env.setDireccionEntrega(e.getDireccionEntrega());
        env.setFechaEntregaEstimada(e.getFechaEntregaEstimada());
        env.setFechaEntregaReal(e.getFechaEntregaReal());
        env.setFechaEnvio(e.getFechaEnvio());
        env.setOrdenId(e.getOrdenId());
        envioRepository.save(env);
        return env;
    }


}
