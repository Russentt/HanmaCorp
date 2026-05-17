package clan.hanma.marketplace_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.marketplace_service.clients.IdentidadFeign;
import clan.hanma.marketplace_service.dto.UsuarioDTO;
import clan.hanma.marketplace_service.dto.VendedorDTO;
import clan.hanma.marketplace_service.mapper.VendedorMapper;
import clan.hanma.marketplace_service.model.Vendedor;
import clan.hanma.marketplace_service.repository.VendedorRepository;

@Service
public class VendedorService {
    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private IdentidadFeign feign;

    @Autowired
    private VendedorMapper mapper;

    public List<Vendedor> findAll() {
        return vendedorRepository.findAll();
    }

    public Vendedor findById(Long id) {
        return vendedorRepository.findById(id).orElse(null);
    }

    public Vendedor findByUsuarioId(Long id) {
        return vendedorRepository.findByUsuarioId(id);
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
        vendedorRepository.save(ven);
        return ven;
    }

    public UsuarioDTO existeUsuario(Long id) {
        return feign.findById(id);
    }


}
