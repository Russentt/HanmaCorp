package clan.hanma.inventario_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.inventario_service.model.MovimientoStock;
import clan.hanma.inventario_service.repository.MovimientoStockRepository;

@Service
public class MovimientoStockService {

    @Autowired
    private MovimientoStockRepository movimientoRepository;

    public List<MovimientoStock> findAll() {
        return movimientoRepository.findAll();
    }

    public MovimientoStock findById(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    public MovimientoStock save(MovimientoStock m) {
        return movimientoRepository.save(m);
    }

    public void delete(Long id) {
        movimientoRepository.deleteById(id);
    }

    public MovimientoStock update(Long id, MovimientoStock m) {
        MovimientoStock mov = movimientoRepository.findById(id).orElse(null);
        mov.setTipoMovimiento(m.getTipoMovimiento());
        mov.setFechaMovimiento(m.getFechaMovimiento());
        mov.setInventario(m.getInventario());
        mov.setCantidad(m.getCantidad());
        movimientoRepository.save(mov);
        return mov;

    }

}
