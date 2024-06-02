package net.ddns.djpinxo.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.djpinxo.models.Usuario;
import net.ddns.djpinxo.repositories.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getUsuarios(@RequestHeader("Authorization") String authorizationHeader) {
		if(validarUsuario(authorizationHeader)) {
			return ResponseEntity.ok().body(usuarioRepository.findAll());
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@GetMapping("/usuarios/{email}")
	public ResponseEntity<Optional<Usuario>> getUsuario(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String email) {
		if(validarUsuario(authorizationHeader) || decodeAuthorizationHeader(authorizationHeader).getEmail().equals(email)) {
			return ResponseEntity.ok().body(usuarioRepository.findById(email));
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping("/usuarios")
	public ResponseEntity<Optional<Usuario>> postUsuario(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Usuario usuario) {
		if(validarUsuario(authorizationHeader)) {
			Optional<Usuario> usuarioSaved = usuarioRepository.findById(usuario.getEmail());
			if (usuarioSaved.isEmpty()) {
				//usuario.setActive(false);
				usuario.setInsertDate(new Date());
				return ResponseEntity.ok().body(Optional.of(usuarioRepository.save(usuario)));
			}
			else return ResponseEntity.ok().body(Optional.empty());
			}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PutMapping("/usuarios/{email}")
	public ResponseEntity<Optional<Usuario>> putUsuario(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Usuario usuario,@PathVariable String email) {
		if(validarUsuario(authorizationHeader) || decodeAuthorizationHeader(authorizationHeader).getEmail().equals(email)) {
			Optional<Usuario> usuarioSaved = usuarioRepository.findById(email);
			if (!usuarioSaved.isEmpty()) {
				usuario.setEmail(email);
				usuario.setUpdateDate(new Date());
				usuarioSaved =Optional.of(usuarioRepository.save(usuario));
			}
			return ResponseEntity.ok().body(usuarioSaved);
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@DeleteMapping("/usuarios/{email}")
	public ResponseEntity<Void> deleteUsuario(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String email) {
		if(validarUsuario(authorizationHeader) || decodeAuthorizationHeader(authorizationHeader).getEmail().equals(email)) {
			usuarioRepository.deleteById(email);
			return ResponseEntity.ok().body(null);
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	
	private boolean validarUsuario(String authorizationHeader) {
		Usuario usuarioAuthorizationHeader  = decodeAuthorizationHeader(authorizationHeader);
		Optional<Usuario> usuarioSaved = usuarioRepository.findById(usuarioAuthorizationHeader.getEmail());
		if (usuarioSaved.isPresent() && usuarioSaved.get().getPassword().equals(usuarioAuthorizationHeader.getPassword()) && usuarioSaved.get().isAdmin()) {
			return true;
		}
		
		return false;
	}
	
	private Usuario decodeAuthorizationHeader(String authorizationHeader) {
		String base64Credentials = authorizationHeader.substring("Basic ".length());
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

        // Split username and password
        String[] credentials = decodedString.split(":", 2);
        String username = credentials[0];
        String password = credentials[1];
        Usuario usuario = new Usuario();
        usuario.setEmail(username);
        usuario.setPassword(password);
        return usuario;
	}

}
