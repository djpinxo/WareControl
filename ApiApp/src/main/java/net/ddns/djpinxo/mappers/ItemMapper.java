package net.ddns.djpinxo.mappers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ddns.djpinxo.dtos.ContenedorDTO;
import net.ddns.djpinxo.dtos.ItemDTO;
import net.ddns.djpinxo.models.Contenedor;
import net.ddns.djpinxo.models.Item;
import net.ddns.djpinxo.repositories.ContenedorRepository;
import net.ddns.djpinxo.repositories.ItemRepository;

@Component
public class ItemMapper {
	
	@Autowired
	private ContenedorRepository contenedorRepository;

	@Autowired
	private ItemRepository itemRepository;

    public ContenedorDTO toDTO(Contenedor contenedor) {
        ContenedorDTO dto = new ContenedorDTO();
        dto.setId(contenedor.getId());
        dto.setNombre(contenedor.getNombre());
        dto.setDescripcion(contenedor.getDescripcion());
        dto.setContenedorPadreId(contenedor.getContenedorPadre() != null ? contenedor.getContenedorPadre().getId() : null);
        if (contenedor.getContenedorHijos()!=null) {
        	ArrayList <ContenedorDTO> contenedorHijos = new ArrayList<ContenedorDTO>();
	        for (Contenedor contenedorHijo : contenedor.getContenedorHijos()) {
	        	contenedorHijos.add(this.toDTO(contenedorHijo));
	        }
	        dto.setContenedorHijos(contenedorHijos);
        }
        if (contenedor.getItems()!=null) {
	        ArrayList <ItemDTO> items = new ArrayList<ItemDTO>();
	        for (Item item : contenedor.getItems()) {
	        	items.add(this.toDTO(item));
	        }
	        dto.setItems(items);
        }
        return dto;
    }

    public Contenedor toEntity(ContenedorDTO dto) {
        Contenedor contenedor = new Contenedor();
        contenedor.setId(dto.getId());
        contenedor.setNombre(dto.getNombre());
        contenedor.setDescripcion(dto.getDescripcion());
        // Aquí deberías buscar y asignar la entidad contenedorPadre si es necesario
        if (dto.getContenedorPadreId()!=null) {
        	Optional<Contenedor> contenedorPadre=contenedorRepository.findById(dto.getContenedorPadreId());
        	contenedor.setContenedorPadre(/*(contenedorPadre.isEmpty())?null:*/contenedorPadre.get());
        }
        if(dto.getContenedorHijos()!=null) {
        	ArrayList <Contenedor> contenedorHijos = new ArrayList<Contenedor>();
        	for (ContenedorDTO contenedorHijo : dto.getContenedorHijos()) {
        		contenedorHijos.add(this.toEntity(contenedorHijo));
        	}
        	contenedor.setContenedorHijos(contenedorHijos);
        }
        if(dto.getItems()!=null) {
	        ArrayList <Item> items = new ArrayList<Item>();
	        for (ItemDTO item : dto.getItems()) {
	        	items.add(this.toEntity(item));
	        }
	        contenedor.setItems(items);
        }
        
        return contenedor;
    }

    public ItemDTO toDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setNombre(item.getNombre());
        dto.setDescripcion(item.getDescripcion());
        dto.setContenedorId(item.getContenedor() != null ? item.getContenedor().getId() : null);
        return dto;
    }

    public Item toEntity(ItemDTO dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setNombre(dto.getNombre());
        item.setDescripcion(dto.getDescripcion());
        if (dto.getContenedorId()!=null) {
        	item.setContenedor(contenedorRepository.findById(dto.getContenedorId()).get());
        }
        return item;
    }
}
