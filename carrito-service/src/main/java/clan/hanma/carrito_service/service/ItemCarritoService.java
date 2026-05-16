package clan.hanma.carrito_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.carrito_service.model.ItemCarrito;
import clan.hanma.carrito_service.repository.ItemCarritoRepository;

@Service
public class ItemCarritoService {

    @Autowired
    private ItemCarritoRepository itemRepository;

    public List<ItemCarrito> findAll() {
        return itemRepository.findAll();
    }

    public ItemCarrito findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public ItemCarrito save(ItemCarrito ic) {
        return itemRepository.save(ic);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemCarrito update(Long id, ItemCarrito ic) {
        ItemCarrito item = itemRepository.findById(id).orElse(null);
        item.setCantidad(ic.getCantidad()); // Se puede cambiar el numero de unidades del item
        item.setProductoId(ic.getProductoId()); // Se puede cambiar el producto a comprar.
        itemRepository.save(item);
        return item;
    }
}
