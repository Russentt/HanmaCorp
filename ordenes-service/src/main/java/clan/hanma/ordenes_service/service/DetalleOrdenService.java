package clan.hanma.ordenes_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clan.hanma.ordenes_service.repository.DetalleRepository;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleRepository detalleRepository;
}
