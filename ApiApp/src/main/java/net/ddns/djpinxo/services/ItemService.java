package net.ddns.djpinxo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.djpinxo.dtos.ItemDTO;
import net.ddns.djpinxo.dtos.DTOMapper;
import net.ddns.djpinxo.models.Item;
import net.ddns.djpinxo.repositories.ContenedorRepository;
import net.ddns.djpinxo.repositories.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ContenedorRepository contenedorRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DTOMapper dtoMapper;

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
            .map(dtoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ItemDTO findById(Long id) {
        return itemRepository.findById(id)
            .map(dtoMapper::toDTO)
            .orElse(null);
    }

    public ItemDTO save(ItemDTO dto) {
        Item item = dtoMapper.toEntity(dto);
        Item savedItem = itemRepository.save(item);
        return dtoMapper.toDTO(savedItem);
    }
    
    public void deleteById(Long id) {
    	itemRepository.deleteById(id);
    }

    // Métodos para items y otras operaciones
}
