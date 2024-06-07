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
import net.ddns.djpinxo.AppWeb.models.Contenedor;
import net.ddns.djpinxo.AppWeb.models.Usuario;
import net.ddns.djpinxo.AppWeb.services.ApiService;
import net.ddns.djpinxo.AppWeb.utils.SecurityInterceptor;

@Controller
public class ContenedorController {

	
	@Autowired
    private ApiService apiService;

	@GetMapping("/contenedores")
	public String homeContenedores(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		return "redirect:/contenedores/view";
	}
	
	@GetMapping("/contenedores/view")
	public String getContenedores(HttpSession session, Model model, @RequestParam(value="query", required = false) String query) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			if (query==null) {
				model.addAttribute("contenedores", apiService.getContenedores());
			}
			else {
				model.addAttribute("contenedores", apiService.getContenedores(query));
				model.addAttribute("query", query);
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
		return "contenedores/view";
	}

	@GetMapping("/contenedores/select/{id}")
	public String getContenedor(HttpSession session, Model model, @PathVariable Long id) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			model.addAttribute("contenedor", apiService.getContenedor(id));
			model.addAttribute("contenedorContenedorhijos", apiService.getContenedorContenedorHijos(id));
			model.addAttribute("contenedorItems", apiService.getContenedorItems(id));
			return "contenedores/select";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/contenedores/view";	
	}
	
	@GetMapping("/contenedores/insert")
	public String getInsertContenedor(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		return "contenedores/insert";
	}

	@PostMapping("/contenedores/insert")
	public String postInsertContenedor(HttpSession session, Model model, @RequestParam(value="nombre") String nombre, @RequestParam(value="descripcion") String descripcion, @RequestParam(value="contenedorPadreId") String sContenedorPadreId) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			Contenedor contenedor = insertContenedor(nombre, descripcion, sContenedorPadreId);
			if (contenedor != null) {
				return "redirect:/contenedores/select/"+ contenedor.getId();
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
		Contenedor contenedorPadre = (sContenedorPadreId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorPadreId), null, null, null, null, null);
		model.addAttribute("contenedor", new Contenedor(null, nombre, descripcion, contenedorPadre, null, null));
		return "contenedores/insert";
	}
	
	private Contenedor insertContenedor(String nombre, String descripcion, String sContenedorPadreId) throws Exception{

        if(validateInsertContenedorForm(nombre, descripcion, sContenedorPadreId)){
        	
            Contenedor contenedorPadre = (sContenedorPadreId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorPadreId), null, null, null, null, null);
            Contenedor contenedor=new Contenedor(0l, nombre, descripcion, contenedorPadre, null, null);
            
            return apiService.insertContenedor(contenedor);
        }
        else {
        	 return null;
        }
    }

    private boolean validateInsertContenedorForm(String nombre, String descripcion, String sContenedorPadreId){

        boolean result=true;
        if(nombre.isEmpty()) {
            result = false;
        }
        if(!sContenedorPadreId.isEmpty()){
            try{
                Long.valueOf(sContenedorPadreId);
            }catch (NumberFormatException e) {
                result = false;
            }
        }

        return result;
    }
	
	@GetMapping("/contenedores/update/{id}")
	public String getUpdateContenedor(HttpSession session, Model model, @PathVariable Long id) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			model.addAttribute("contenedor", apiService.getContenedor(id));
			return "contenedores/update";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/contenedores/view";	
	}

	@PostMapping("/contenedores/update/{id}")
	public String postUpdateContenedor(HttpSession session, Model model, @PathVariable Long id, @RequestParam(value="nombre") String nombre, @RequestParam(value="descripcion") String descripcion, @RequestParam(value="contenedorPadreId") String sContenedorPadreId) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			Contenedor contenedor = updateContenedor(id, nombre, descripcion, sContenedorPadreId);
			if (contenedor != null) {
				return "redirect:/contenedores/select/"+ contenedor.getId();
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
		Contenedor contenedorPadre = (sContenedorPadreId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorPadreId), null, null, null, null, null);
		model.addAttribute("contenedor", new Contenedor(id, nombre, descripcion, contenedorPadre, null, null));
		return "contenedores/update";
	}
	
	private Contenedor updateContenedor(long id, String nombre, String descripcion, String sContenedorPadreId) throws Exception{

        if(validateUpdateContenedorForm(id, nombre, descripcion, sContenedorPadreId)){
        	Contenedor contenedor = apiService.getContenedor(id);
            Contenedor contenedorPadre = (sContenedorPadreId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorPadreId), null, null, null, null, null);
            //contenedorModel=new Contenedor(0l,name,descripcion, contenedorPadre, null, null);
            contenedor.setNombre(nombre);
            contenedor.setDescripcion(descripcion);
            contenedor.setContenedorPadre(contenedorPadre);
            
            return apiService.updateContenedor(id, contenedor);
        }
        else {
        	return null;
        }
    }

    private boolean validateUpdateContenedorForm(long id, String nombre, String descripcion, String sContenedorPadreId){

        boolean result = true;
        if(nombre.isEmpty()) {
            result = false;
        }
        if(!sContenedorPadreId.isEmpty()){
            try{
                Long.valueOf(sContenedorPadreId);
            }catch (NumberFormatException e) {
                result = false;
            }
        }

        return result;
    }
	
	@GetMapping("/contenedores/delete/{id}")
	public String getDeleteContenedor(HttpSession session, Model model, @PathVariable Long id) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			apiService.deleteContenedor(id);
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/contenedores/view";
	}
	
	/*@GetMapping("/contenedores/select/{id}/contenedorhijos")
	public String getContenedorContenedorHijos(HttpSession session, Model model, @PathVariable Long id) {
		Optional<Contenedor> contenedor = contenedorRepository.findById(id);
		if(!contenedor.isEmpty()) {
			return contenedor.get().getContenedorHijos();
		}
		return null;
	}
	
	@GetMapping("/contenedores/select/{id}/items")
	public String getContenedorItems(HttpSession session, Model model, @PathVariable Long id) {
		Optional<Contenedor> contenedor = contenedorRepository.findById(id);
		if(!contenedor.isEmpty()) {
			return contenedor.get().getItems();
		}
		return null;
	}*/

}
