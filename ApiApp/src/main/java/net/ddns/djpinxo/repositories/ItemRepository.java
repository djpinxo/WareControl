package net.ddns.djpinxo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.djpinxo.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	List<Item> findByNombreContainsIgnoreCaseOrDescripcionContainsIgnoreCase(String query, String query2);

}
