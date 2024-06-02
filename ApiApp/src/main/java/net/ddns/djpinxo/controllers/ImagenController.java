package net.ddns.djpinxo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.ddns.djpinxo.models.Imagen;
import net.ddns.djpinxo.models.Item;
import net.ddns.djpinxo.repositories.ImagenRepository;
import net.ddns.djpinxo.repositories.ItemRepository;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ImagenController {

    @Autowired
    private ImagenRepository imagenRepository;

    @PostMapping("/imagenes")
    public ResponseEntity<String> subirImagen(@RequestPart MultipartFile file) {
        try {
        	
        	Imagen imagen = new Imagen();
            imagen.setNombre(file.getOriginalFilename());
            imagen.setTipo(file.getContentType());
            imagen.setDatos(file.getBytes());
            
            imagen=imagenRepository.save(imagen);
        	
        	
        	
            return ResponseEntity.status(HttpStatus.OK).body("Imagen subida con éxito: " + imagen.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @GetMapping("/imagenes/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Optional<Imagen> imagenOptional = imagenRepository.findById(id);

        if (imagenOptional.isPresent()) {
            Imagen imagen = imagenOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(imagen.getTipo()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagen.getNombre() + "\"")
                    .body(imagen.getDatos());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    
    @Autowired
	ItemRepository itemRepository;
    /* Desplazado a ItemController
    @GetMapping("/items/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagenAux(@PathVariable Long id) {
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
    public ResponseEntity<String> subirImagenAux(@PathVariable Long id,@RequestPart MultipartFile file) {
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
    }*/
}
