package net.ddns.djpinxo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.djpinxo.dtos.ContenedorDTO;
import net.ddns.djpinxo.dtos.DTOMapper;
import net.ddns.djpinxo.models.Contenedor;
import net.ddns.djpinxo.repositories.ContenedorRepository;
import net.ddns.djpinxo.repositories.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContenedorService {

    @Autowired
    private ContenedorRepository contenedorRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DTOMapper dtoMapper;

    public List<ContenedorDTO> findAll() {
        return contenedorRepository.findAll().stream()
            .map(dtoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ContenedorDTO findById(Long id) {
        return contenedorRepository.findById(id)
            .map(dtoMapper::toDTO)
            .orElse(null);
    }

    public ContenedorDTO save(ContenedorDTO dto) {
        Contenedor contenedor = dtoMapper.toEntity(dto);
        Contenedor savedContenedor = contenedorRepository.save(contenedor);
        return dtoMapper.toDTO(savedContenedor);
    }
    
    public void deleteById(Long id) {
        contenedorRepository.deleteById(id);
    }

    // Métodos para items y otras operaciones
}
