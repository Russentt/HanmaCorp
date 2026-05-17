package clan.hanma.marketplace_service.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.marketplace_service.model.Categoria;
import clan.hanma.marketplace_service.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria c) {
        return categoriaRepository.save(c);
    }

    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria update(Long id, Categoria c) {
        Categoria cat = categoriaRepository.findById(id).orElse(null);
        cat.setDescripcion(c.getDescripcion());
        cat.setNombre(c.getNombre());
        categoriaRepository.save(cat);
        return cat;
    }

}
