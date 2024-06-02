package net.ddns.djpinxo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.djpinxo.dtos.ContenedorDTO;
import net.ddns.djpinxo.models.Contenedor;
import net.ddns.djpinxo.models.Item;
import net.ddns.djpinxo.repositories.ContenedorRepository;
import net.ddns.djpinxo.services.ContenedorService;

@RestController
public class ContenedorController {

	@Autowired
	ContenedorRepository contenedorRepository;
	//@Autowired
	//ContenedorService contenedorService;

	@GetMapping("/contenedores")
	public List<Contenedor> getContenedores() {
		return contenedorRepository.findAll();
	}

	@GetMapping("/contenedores/{id}")
	public Optional<Contenedor> getContenedor(@PathVariable Long id) {
		return contenedorRepository.findById(id);
	}

	@PostMapping("/contenedores")
	public Optional<Contenedor> postContenedor(@RequestBody Contenedor contenedor) {
		Optional<Contenedor> contenedorSaved = contenedorRepository.findById(contenedor.getId());
		if (contenedorSaved.isEmpty()) {
			return Optional.of(contenedorRepository.save(contenedor));
		}
		else return Optional.empty();
	}

	@PutMapping("/contenedores/{id}")
	public Optional<Contenedor> putContenedor(@RequestBody Contenedor contenedor,@PathVariable Long id) {
		Optional<Contenedor> contenedorSaved = contenedorRepository.findById(id);
		if (!contenedorSaved.isEmpty()) {
			contenedor.setId(id);
			contenedorSaved =Optional.of(contenedor);
			contenedorRepository.save(contenedor);
		}
		return contenedorSaved;
	}
	
	@DeleteMapping("/contenedores/{id}")
	public void deleteContenedor(@PathVariable Long id) {
		contenedorRepository.deleteById(id);
	}
	
	@GetMapping("/contenedores/{id}/contenedorhijos")
	public List<Contenedor> getContenedorContenedorHijos(@PathVariable Long id) {
		Optional<Contenedor> contenedor = contenedorRepository.findById(id);
		if(!contenedor.isEmpty()) {
			return contenedor.get().getContenedorHijos();
		}
		return null;
	}
	
	@GetMapping("/contenedores/{id}/items")
	public List<Item> getContenedorItems(@PathVariable Long id) {
		Optional<Contenedor> contenedor = contenedorRepository.findById(id);
		if(!contenedor.isEmpty()) {
			return contenedor.get().getItems();
		}
		return null;
	}

	/*
	@GetMapping("/contenedores")
	public List<ContenedorDTO> getContenedores() {
		return contenedorService.findAll();
	}

	@GetMapping("/contenedores/{id}")
	public ContenedorDTO getContenedor(@PathVariable Long id) {
		return contenedorService.findById(id);
	}

	@PostMapping("/contenedores")
	public ContenedorDTO postContenedor(@RequestBody ContenedorDTO contenedorDTO) {
		ContenedorDTO contenedorSaved = contenedorService.findById(contenedorDTO.getId());
		if (contenedorSaved==null) {
			return contenedorService.save(contenedorDTO);
		}
		else return null;
	}

	@PutMapping("/contenedores/{id}")
	public ContenedorDTO putContenedor(@RequestBody ContenedorDTO contenedor,@PathVariable Long id) {
		ContenedorDTO contenedorSaved = contenedorService.findById(id);
		if (contenedorSaved!=null) {
			contenedor.setId(id);
			contenedorSaved = contenedor;
			contenedorService.save(contenedor);
		}
		return contenedorSaved;
	}
	
	@DeleteMapping("/contenedores/{id}")
	public void deleteContenedor(@PathVariable Long id) {
		contenedorService.deleteById(id);
	}*/

}
