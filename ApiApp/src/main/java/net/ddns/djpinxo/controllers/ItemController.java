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

import net.ddns.djpinxo.dtos.DTOMapper;
import net.ddns.djpinxo.dtos.ItemDTO;
import net.ddns.djpinxo.models.Item;
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
	public List<Item> getItems1() {
		return itemRepository.findAll();
	}

	@GetMapping("/items/{id}")
	public Optional<Item> getItem1(@PathVariable Long id) {
		return itemRepository.findById(id);
	}

	@PostMapping("/items")
	public Optional<Item> postItem1(@RequestBody Item item) {
		Optional<Item> itemSaved = itemRepository.findById(item.getId());
		if (itemSaved.isEmpty()) {
			return Optional.of(itemRepository.save(item));
		}
		else return Optional.empty();
	}

	@PutMapping("/items/{id}")
	public Optional<Item> putItem1(@RequestBody Item item,@PathVariable Long id) {
		Optional<Item> itemSaved = itemRepository.findById(id);
		if (!itemSaved.isEmpty()) {
			item.setId(id);
			itemSaved =Optional.of(itemRepository.save(item));
		}
		return itemSaved;
	}
	
	@DeleteMapping("/items/{id}")
	public void deleteItem1(@PathVariable Long id) {
		itemRepository.deleteById(id);
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
