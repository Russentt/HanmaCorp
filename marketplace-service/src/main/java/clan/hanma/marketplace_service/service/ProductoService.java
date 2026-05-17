package clan.hanma.marketplace_service.service;

import clan.hanma.marketplace_service.repository.CategoriaRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.marketplace_service.dto.ProductoDTO;
import clan.hanma.marketplace_service.mapper.ProductoMapper;
import clan.hanma.marketplace_service.model.Producto;
import clan.hanma.marketplace_service.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper mapper;

    ProductoService(CategoriaRepository categoriaRepository) {
    }

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
        pdto.setCategoria(p.getCategoria());
        pdto.setNombre(p.getNombre());
        pdto.setPrecio(p.getPrecio());
        pdto.setStock(p.getStock());
        pdto.setDescripcion(p.getDescripcion());
        productoRepository.save(pdto);
        return pdto;
    }

    public List<Producto> findByCategoriaId(Long id) {
        return productoRepository.findByCategoriaId(id);
    }

    public List<Producto> findByTiendaId(Long id) {
        return productoRepository.findByTiendaId(id);
    }

    public List<Producto> findByStock(int stock) {
        return productoRepository.findByStock(stock);
    }

    public Long findStock(Long id) {
        Producto p = productoRepository.findById(id).orElse(null);
        return p.getStock();
    }

    public List<Producto> findByPrice(int min, int max) {
        return productoRepository.findByPrice(min, max);
    }

    public ProductoDTO findByIdDto(Long id) {
        Producto p = productoRepository.findById(id).orElse(null);
        return mapper.toDTO(p);

    }

}
