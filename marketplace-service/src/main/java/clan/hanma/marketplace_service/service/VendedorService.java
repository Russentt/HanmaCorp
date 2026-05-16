package clan.hanma.marketplace_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.marketplace_service.model.Vendedor;
import clan.hanma.marketplace_service.repository.VendedorRepository;

@Service
public class VendedorService {
    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> findAll() {
        return vendedorRepository.findAll();
    }

    public Vendedor findById(Long id) {
        return vendedorRepository.findById(id).orElse(null);
    }

    public Vendedor save(Vendedor v) {
        return vendedorRepository.save(v);
    }

    public void delete(Long id) {
        vendedorRepository.deleteById(id);
    }

    public Vendedor update(Long id, Vendedor v) {
        Vendedor ven = vendedorRepository.findById(id).orElse(null);
        ven.setTienda(v.getTienda());
        ven.setFechaRegistro(v.getFechaRegistro());
        vendedorRepository.save(ven);
        return ven;
    }

}
