package clan.hanma.logistica_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.logistica_service.model.Region;
import clan.hanma.logistica_service.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region findById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    public Region save(Region r) {
        return regionRepository.save(r);
    }

    public void delete(Long id) {
        regionRepository.deleteById(id);
    }

    public Region update(Long id, Region r) {
        Region reg = regionRepository.findById(id).orElse(null);
        reg.setNombre(r.getNombre());
        regionRepository.save(reg);
        return reg;
    }

}
