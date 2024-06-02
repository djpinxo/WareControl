package net.ddns.djpinxo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import net.ddns.djpinxo.models.Contenedor;

public interface ContenedorRepository extends JpaRepository<Contenedor, Long> {

}
