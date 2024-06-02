package net.ddns.djpinxo.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.djpinxo.models.Usuario;
import net.ddns.djpinxo.repositories.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/usuarios")
	public Iterable<Usuario> getUsuarios() {
		return usuarioRepository.findAll();
	}

	@GetMapping("/usuarios/{email}")
	public Optional<Usuario> getUsuario(@PathVariable String email) {
		return usuarioRepository.findById(email);
	}

	@PostMapping("/usuarios")
	public Optional<Usuario> postUsuario(@RequestBody Usuario usuario) {
		Optional<Usuario> usuarioSaved = usuarioRepository.findById(usuario.getEmail());
		if (usuarioSaved.isEmpty()) {
			//usuario.setActive(false);
			usuario.setInsertDate(new Date());
			return Optional.of(usuarioRepository.save(usuario));
		}
		else return Optional.empty();
	}

	@PutMapping("/usuarios/{email}")
	public Optional<Usuario> putUsuario(@RequestBody Usuario usuario,@PathVariable String email) {
		Optional<Usuario> usuarioSaved = usuarioRepository.findById(email);
		if (!usuarioSaved.isEmpty()) {
			usuario.setEmail(email);
			usuario.setUpdateDate(new Date());
			usuarioSaved =Optional.of(usuarioRepository.save(usuario));
		}
		return usuarioSaved;
	}
	
	@DeleteMapping("/usuarios/{email}")
	public void deleteUsuario(@PathVariable String email) {
		usuarioRepository.deleteById(email);
	}

}
