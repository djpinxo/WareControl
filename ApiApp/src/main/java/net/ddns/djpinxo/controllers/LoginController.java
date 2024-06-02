package net.ddns.djpinxo.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.djpinxo.models.Usuario;
import net.ddns.djpinxo.repositories.UsuarioRepository;

/*
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
		Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);
		// ...
		return  null;
	}

	public record LoginRequest(String username, String password) {
	}

}*/

@RestController
public class LoginController {
	@Autowired
	UsuarioRepository usuarioRepository;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario ) {
		if(usuario==null || usuario.getEmail()==null || usuario.getEmail().isEmpty() || usuario.getPassword()==null || usuario.getPassword().isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing email or password");
		}
		Optional<Usuario> usuarioSaved = usuarioRepository.findById(usuario.getEmail());
		if (usuarioSaved.isEmpty() || usuarioSaved.get().getPassword()==null || usuarioSaved.get().getPassword().isEmpty() || !usuarioSaved.get().getPassword().equals(usuario.getPassword()) || !usuarioSaved.get().isActive()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing email or password");
		}
		
		//proceso de cambio a la nueva ultima fecha
		Date localDateLastLogin = usuarioSaved.get().getLastLogin();
		usuarioSaved.get().setLastLogin(new Date());
		usuarioRepository.save(usuarioSaved.get());
		
		usuarioSaved.get().setLastLogin(localDateLastLogin);
		//usuarioSaved.get().setPassword(null);
		return ResponseEntity.ok(usuarioSaved);
		
	}
	
	@PostMapping("/register")
	public Optional<Usuario> register(@RequestBody Usuario usuario) {
		Optional<Usuario> usuarioSaved = usuarioRepository.findById(usuario.getEmail());
		if (usuarioSaved.isEmpty()) {
			//usuario.setActive(false);
			usuario.setInsertDate(new Date());
			return Optional.of(usuarioRepository.save(usuario));
		}
		else return Optional.empty();
	}


}