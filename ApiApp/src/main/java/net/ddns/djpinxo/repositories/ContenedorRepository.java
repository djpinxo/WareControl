package net.ddns.djpinxo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.djpinxo.models.Contenedor;

public interface ContenedorRepository extends JpaRepository<Contenedor, Long> {
	
	List<Contenedor> findByNombreContainsIgnoreCaseOrDescripcionContainsIgnoreCase(String query, String query2);

}
