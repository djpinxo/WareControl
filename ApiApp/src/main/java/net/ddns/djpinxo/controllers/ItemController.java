package net.ddns.djpinxo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.ddns.djpinxo.dtos.DTOMapper;
import net.ddns.djpinxo.dtos.ItemDTO;
import net.ddns.djpinxo.models.Imagen;
import net.ddns.djpinxo.models.Item;
import net.ddns.djpinxo.repositories.ImagenRepository;
import net.ddns.djpinxo.repositories.ItemRepository;
import net.ddns.djpinxo.services.ContenedorService;
import net.ddns.djpinxo.services.ItemService;

@RestController
public class ItemController {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemService itemService;

	@GetMapping("/items")
	public List<Item> getItems(@RequestParam(required = false) String query) {
		if (query==null) {
			return itemRepository.findAll();
		}
		else {
			return itemRepository.findByNombreContainsIgnoreCaseOrDescripcionContainsIgnoreCase(query, query);
		}
	}

	@GetMapping("/items/{id}")
	public Optional<Item> getItem(@PathVariable Long id) {
		return itemRepository.findById(id);
	}

	@PostMapping("/items")
	public Optional<Item> postItem(@RequestBody Item item) {
		Optional<Item> itemSaved = itemRepository.findById(item.getId());
		if (itemSaved.isEmpty()) {
			return Optional.of(itemRepository.save(item));
		}
		else return Optional.empty();
	}

	@PutMapping("/items/{id}")
	public Optional<Item> putItem(@RequestBody Item item,@PathVariable Long id) {
		Optional<Item> itemSaved = itemRepository.findById(id);
		if (itemSaved.isPresent()) {
			itemSaved.get().setNombre(item.getNombre());
			itemSaved.get().setDescripcion(item.getDescripcion());
			itemSaved.get().setContenedor(item.getContenedor());
			//item.setId(id);
			itemSaved =Optional.of(itemRepository.save(itemSaved.get()));
		}
		return itemSaved;
	}
	
	@Autowired
	private ImagenRepository imagenRepository;
	
	@DeleteMapping("/items/{id}")
	public void deleteItem(@PathVariable Long id) {
		Optional <Item> itemSaved = itemRepository.findById(id);
		itemRepository.deleteById(id);
		if (itemSaved.isPresent() && itemSaved.get().getImagen()!=null) {
    		imagenRepository.delete(itemSaved.get().getImagen());
    	}
	}
	
	@GetMapping("/items/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
    	Optional <Item> itemSaved = itemRepository.findById(id);
    	if (itemSaved.isPresent() && itemSaved.get().getImagen()!=null) {
    		
    		Imagen imagen = itemSaved.get().getImagen();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(imagen.getTipo()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagen.getNombre() + "\"")
                    .body(imagen.getDatos());
    	}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @PostMapping("/items/{id}/imagen")
    public ResponseEntity<String> subirImagen(@PathVariable Long id,@RequestPart MultipartFile file) {
    	try {
    		Optional <Item> itemSaved = itemRepository.findById(id);
	    	if (itemSaved.isPresent()) {
	    		Imagen imagen = new Imagen();
	    		if (itemSaved.get().getImagen()!=null) {
	    			imagen = itemSaved.get().getImagen();
	    		}
	            imagen.setNombre(file.getOriginalFilename());
	            imagen.setTipo(file.getContentType());
				imagen.setDatos(file.getBytes());
				itemSaved.get().setImagen(imagen);
				imagen=imagenRepository.save(imagen);
				Item itemSavedFinal = itemRepository.save(itemSaved.get());
				
				return ResponseEntity.status(HttpStatus.OK).body("Imagen subida con éxito: " + itemSavedFinal.getImagen().getId());
	            
	    	}
    	} catch (IOException e) {
		}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
	
	/*
	@GetMapping("/items")
	public List<ItemDTO> getItems() {
		return itemService.findAll();
	}

	@GetMapping("/items/{id}")
	public ItemDTO getItem(@PathVariable Long id) {
		return itemService.findById(id);
	}

	@PostMapping("/items")
	public ItemDTO postItem(@RequestBody ItemDTO itemDTO) {
		ItemDTO itemSaved = itemService.findById(itemDTO.getId());
		if (itemSaved==null) {
			return itemService.save(itemDTO);
		}
		else return null;
	}

	@PutMapping("/items/{id}")
	public ItemDTO putItem(@RequestBody ItemDTO itemDTO,@PathVariable Long id) {
		ItemDTO itemSaved = itemService.findById(id);
		if (itemSaved!=null) {
			itemDTO.setId(id);
			itemSaved = itemService.save(itemDTO);
		}
		return itemSaved;
	}
	
	@DeleteMapping("/items/{id}")
	public void deleteItem(@PathVariable Long id) {
		itemService.deleteById(id);
	}*/

}
