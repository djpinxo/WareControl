package net.ddns.djpinxo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.djpinxo.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
