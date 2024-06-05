package net.ddns.djpinxo.AppWeb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import net.ddns.djpinxo.AppWeb.models.Usuario;
import net.ddns.djpinxo.AppWeb.utils.SecurityInterceptor;

@Controller
public class HomeController {
	
	
	@GetMapping("/home")
    public String getHome(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
        return "home/home";
        
        
        
    }
}
