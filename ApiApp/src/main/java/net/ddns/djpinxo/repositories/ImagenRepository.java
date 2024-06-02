package net.ddns.djpinxo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.ddns.djpinxo.models.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
