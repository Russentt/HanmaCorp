package clan.hanma.inventario_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;

import clan.hanma.inventario_service.clients.MarketplaceFeign;
import clan.hanma.inventario_service.dto.InventarioDTO;
import clan.hanma.inventario_service.dto.ProductoDTO;
import clan.hanma.inventario_service.mapper.InventarioMapper;
import clan.hanma.inventario_service.model.Inventario;
import clan.hanma.inventario_service.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioMapper mapper;

    @Autowired
    private MarketplaceFeign feign;

    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    public Inventario findById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    public Inventario save(Inventario i) {
        return inventarioRepository.save(i);
    }

    public void delete(Long id) {
        inventarioRepository.deleteById(id);
    }

    public Inventario update(Long id, Inventario i) {
        Inventario inv = inventarioRepository.findById(id).orElse(null);
        inv.setBodega(i.getBodega());
        inv.setProductoId(i.getProductoId());
        inv.setStockDisponible(i.getStockDisponible());
        inv.setStockReservado(i.getStockReservado());
        inv.setStockMinimo(i.getStockMinimo());
        inventarioRepository.save(inv);
        return inv;
    }

    public ProductoDTO findByIdDTO(Long id) {
         return feign.findByIdDTO(id);
    }

    public List<Inventario> findByStockDisponible(int stockDisponible) {
        return inventarioRepository.findByStockDisponible(stockDisponible);
    }

    public int findStock(Long id) {
        return feign.findStock(id);
    }

    public ProductoDTO reservarStock(Long id, int cantidad) {
        return feign.reservarStock(id, cantidad);
    }

    public ProductoDTO liberarStock(Long id, int cantidad) {
        return feign.liberarStock(id, cantidad);
    }


}
