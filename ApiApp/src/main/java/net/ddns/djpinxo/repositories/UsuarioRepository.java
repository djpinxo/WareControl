package net.ddns.djpinxo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import net.ddns.djpinxo.models.Item;
import net.ddns.djpinxo.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
