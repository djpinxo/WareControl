package net.ddns.djpinxo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import net.ddns.djpinxo.models.Usuario;
import net.ddns.djpinxo.repositories.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
		.httpBasic()
		.and().authorizeHttpRequests()
		//.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
		//.authorizeHttpRequests().anyRequest().permitAll();
		//.authorizeHttpRequests().anyRequest().denyAll();
		//.authorizeHttpRequests().anyRequest().authenticated();
		//.authorizeHttpRequests().anyRequest().authenticated()
		.requestMatchers("/login", "/register").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable()
		.build();
	}


}

@Service
class UserDetailServicePersonalizado implements UserDetailsService{

	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findById(email);
		if (usuario.isPresent()) {
			return new UserDetailPersonalizado(usuario.get()); 
		}
		throw new UsernameNotFoundException("Usuario no encontrado: " + email);
	}
	
}



class UserDetailPersonalizado implements UserDetails{
	
	private Usuario usuario;
	
	public UserDetailPersonalizado(Usuario usuario) {
		this.usuario=usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return usuario.getEmail();
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return usuario.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return usuario.isActive();
	}
	
}
