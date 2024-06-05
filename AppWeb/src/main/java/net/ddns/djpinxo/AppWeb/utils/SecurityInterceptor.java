package net.ddns.djpinxo.AppWeb.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import net.ddns.djpinxo.AppWeb.models.Usuario;
import net.ddns.djpinxo.AppWeb.services.ApiService;

public class SecurityInterceptor {
	
	
	public static boolean validateSession(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogin");
		if(usuario != null){
			model.addAttribute("usuarioLogin", usuario);
			return true;
		}
		return false;
		
	}

}
