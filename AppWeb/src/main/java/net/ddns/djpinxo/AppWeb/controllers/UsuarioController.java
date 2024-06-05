package net.ddns.djpinxo.AppWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.servlet.http.HttpSession;
import net.ddns.djpinxo.AppWeb.models.Usuario;
import net.ddns.djpinxo.AppWeb.services.ApiService;
import net.ddns.djpinxo.AppWeb.utils.HashUtils;
import net.ddns.djpinxo.AppWeb.utils.SecurityInterceptor;



@Controller
public class UsuarioController {

	@Autowired
    private ApiService apiService;
	
	@GetMapping("/usuarios")
	public String homeUsuarios(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		return "redirect:/usuarios/view";
	}
	
	@GetMapping("/usuarios/view")
	public String getUsuarios(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			model.addAttribute("usuarios", apiService.getUsuarios());
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "usuarios/view";
	}

	@GetMapping("/usuarios/select/{email}")
	public String getUsuario(HttpSession session, Model model, @PathVariable String email) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			model.addAttribute("usuario", apiService.getUsuario(email));
			return "usuarios/select";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/usuarios/view";
	}
	
	@GetMapping("/usuarios/insert")
	public String getInsertUsuario(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		return "usuarios/insert";
	}

	@PostMapping("/usuarios/insert")
	public String postInsertUsuario(HttpSession session, Model model, @RequestParam(value="nombre") String nombre, @RequestParam(value="email") String email, @RequestParam(value="password") String password, @RequestParam(value="repeatPassword") String repeatPassword, @RequestParam(value="active", defaultValue="false") boolean active, @RequestParam(value="admin", defaultValue="false") boolean admin) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			Usuario usuario = insertUsuario(nombre, email, password, repeatPassword, active, admin);
			if (usuario != null) {
				return "redirect:/usuarios/select/"+ usuario.getEmail();
			}
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		model.addAttribute("usuario", new Usuario(email , nombre, password, active, admin));
		return "usuarios/insert";
	}
	
	private Usuario insertUsuario(String nombre, String email, String password, String repeatPassword, boolean active, boolean admin) throws Exception {

        if(validateInsertUsuarioForm(nombre, email, password, repeatPassword)){
            
        	Usuario usuario = new Usuario(email, nombre, HashUtils.hashString(password), active, admin);
            
            return apiService.insertUsuario(usuario);
        }
        else {
            return null;
        }
    }
	
	private boolean validateInsertUsuarioForm(String nombre, String email, String password, String repeatPassword) {
        
		boolean result = true;
        if(email.isEmpty()) {
            result = false;
        }
        if(nombre.isEmpty()) {
            result = false;
        }
        if(password.isEmpty()) {
            result = false;
        }
        if(repeatPassword.isEmpty()) {
            result = false;
        }
        if(result && !password.equals(repeatPassword)) {
            result = false;
        }

        return result;
    }
	
	@GetMapping("/usuarios/update/{email}")
	public String getUpdateUsuario(HttpSession session, Model model, @PathVariable String email) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			model.addAttribute("usuario", apiService.getUsuario(email));
			return "usuarios/update";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/usuarios/view";	
	}
	
	
	@PostMapping("/usuarios/update/{email}")
	public String postUpdateUsuario(HttpSession session, Model model, @PathVariable String email, @RequestParam(value="nombre") String nombre, @RequestParam(value="password") String password, @RequestParam(value="repeatPassword") String repeatPassword, @RequestParam(value="active", defaultValue="false") boolean active, @RequestParam(value="admin", defaultValue="false") boolean admin) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			Usuario usuario = updateUsuario(nombre, email, password, repeatPassword, active, admin);
			if (usuario != null) {
				return "redirect:/usuarios/select/"+ usuario.getEmail();
			}
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		model.addAttribute("usuario", new Usuario(email , nombre, password, active, admin));
		return "usuarios/update";
	}
	
	private Usuario updateUsuario(String nombre, String email, String password, String repeatPassword, boolean active, boolean admin) throws Exception {
        
        if(validateUpdateUsuarioForm(nombre, email, password, repeatPassword)){
        	Usuario usuario = apiService.getUsuario(email);

            //userModel=new User(email, name, password, isAdmin, isActive);
            usuario.setNombre(nombre);
            //Se verifica si la contraseña ha cambiado, si no lo hizo no volver a lanzar el hash
            if(!password.equals(usuario.getPassword()))
                password = HashUtils.hashString(password);
            usuario.setPassword(password);
            usuario.setActive(active);
            usuario.setAdmin(admin);
            
            return apiService.updateUsuario(email, usuario);
        }
        else {
        	return null;
        }
    }

    private boolean validateUpdateUsuarioForm(String nombre, String email, String password, String repeatPassword) {
        
    	boolean result = true;
        if(email.isEmpty()) {
            result = false;
        }
        if(nombre.isEmpty()) {
            result = false;
        }
        if(password.isEmpty()) {
            result = false;
        }
        if(repeatPassword.isEmpty()) {
            result = false;
        }
        if(result && !password.equals(repeatPassword)) {
            result = false;
        }

        return result;
    }
	
	@GetMapping("/usuarios/delete/{email}")
	public String getDeleteUsuario(HttpSession session, Model model, @PathVariable String email) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			apiService.deleteUsuario(email);
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/usuarios/view";
	}
	
	
	//gestion usuario propio
	@GetMapping("/usuario/update")
	public String getUpdateUsuarioLoged(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			model.addAttribute("usuario", apiService.getUsuario(((Usuario)session.getAttribute("usuarioLogin")).getEmail()));
			return "usuarios/updateLoged";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/logout";	
	}
	
	
	@PostMapping("/usuario/update")
	public String postUpdateUsuarioLoged(HttpSession session, Model model, @RequestParam(value="nombre") String nombre, @RequestParam(value="password") String password, @RequestParam(value="repeatPassword") String repeatPassword) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			Usuario usuario = updateUsuario(nombre, ((Usuario)session.getAttribute("usuarioLogin")).getEmail(), password, repeatPassword, ((Usuario)session.getAttribute("usuarioLogin")).isActive(), ((Usuario)session.getAttribute("usuarioLogin")).isAdmin());
			if (usuario != null) {
				return "redirect:/logout";
			}
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		model.addAttribute("usuario", session.getAttribute("usuarioLogin"));
		return "usuarios/updateLoged";
	}
	
	@GetMapping("/usuario/delete")
	public String getDeleteUsuario(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			apiService.deleteUsuario(((Usuario)session.getAttribute("usuarioLogin")).getEmail());
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/logout";
	}
}
