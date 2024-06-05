package net.ddns.djpinxo.AppWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.servlet.http.HttpSession;
import net.ddns.djpinxo.AppWeb.models.Usuario;
import net.ddns.djpinxo.AppWeb.services.ApiService;
import net.ddns.djpinxo.AppWeb.utils.HashUtils;

@Controller
public class LoginController {


	@Autowired
    private ApiService apiService;

	@GetMapping("/login")
    public String getLogin(HttpSession session, Model model) {
		session.invalidate();
        return "login/login";
        
        
        
    }
	
	@PostMapping("/login")
    public String postLogin(HttpSession session, Model model, @RequestParam("email") String email, @RequestParam("password") String password) {
		try {
			Usuario usuario = new Usuario();
		
			usuario.setEmail(email);
			usuario.setPassword(HashUtils.hashString(password));
	        usuario = apiService.loginUsuario(usuario);
	        session.setAttribute("usuarioLogin", usuario);
	        apiService.setInterceptor(usuario);
	        return "redirect:/home";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error en el login del usuario");
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		apiService.removeInterceptor();
		return "login/login";
        
        
        
    }
	
	@GetMapping("/logout")
    public String getLogout(HttpSession session, Model model) {
		session.invalidate();
		apiService.removeInterceptor();
        return "redirect:/login";
        
        
        
    }
    
	@GetMapping("/register")
	public String getRegister(HttpSession session, Model model) {
		return "login/register";
	}
	
	@PostMapping("/register")
	public String postRegister(HttpSession session, Model model, @RequestParam("email") String email, @RequestParam("nombre") String nombre, @RequestParam("password") String password, @RequestParam("repeatpassword") String repeatPassword) {
		try {
			Usuario usuario = insertUser (email, nombre, password, repeatPassword);
			
			if (usuario != null) {
				return "redirect:/login";
			}
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error en el registro del usuario");
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "login/register";
	}
	
	private Usuario insertUser(String email, String nombre, String password, String repeatPassword){
        if(validateUserForm(email, nombre, password, repeatPassword)){
            password = HashUtils.hashString(password);
            Usuario usuario = new Usuario(email, nombre, password, false, false);
            return apiService.registerUsuario(usuario);
        }
        else {
        	return null;
        }
    }

    private boolean validateUserForm(String email, String nombre, String password, String repeatPassword){

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


}