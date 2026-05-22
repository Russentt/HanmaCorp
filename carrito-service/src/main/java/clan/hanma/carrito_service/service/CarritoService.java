package clan.hanma.carrito_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.carrito_service.clients.MarketplaceFeign;
import clan.hanma.carrito_service.dto.ProductoDTO;
import clan.hanma.carrito_service.model.Carrito;
import clan.hanma.carrito_service.repository.CarritoRepository;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private MarketplaceFeign feign;

    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    public Carrito findById(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }

    public Carrito save(Carrito c) {
        return carritoRepository.save(c);
    }

    public void delete(Long id) {
        carritoRepository.deleteById(id);
    }

    public ProductoDTO encontrarProductoDTO(Long id) {
        ProductoDTO pDTO = feign.findByIdDTO(id);
        return pDTO;
    }
}
