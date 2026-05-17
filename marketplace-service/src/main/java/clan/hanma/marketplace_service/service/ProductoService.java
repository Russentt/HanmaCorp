package clan.hanma.marketplace_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.marketplace_service.model.Producto;
import clan.hanma.marketplace_service.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto p) {
        return productoRepository.save(p);
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto update(Long id, Producto p) {
        Producto pdto = productoRepository.findById(id).orElse(null);
        pdto.setCategoria(p.getCategoria().g);
        pdto.setNombre(p.getNombre());
        pdto.setPrecio(p.getPrecio());
        pdto.setStock(p.getStock());
        pdto.setDescripcion(p.getDescripcion());
        productoRepository.save(pdto);
        return pdto;
    }

}
