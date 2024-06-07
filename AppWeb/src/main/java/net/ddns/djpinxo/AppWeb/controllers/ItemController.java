package net.ddns.djpinxo.AppWeb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.servlet.http.HttpSession;
import net.ddns.djpinxo.AppWeb.models.Contenedor;
import net.ddns.djpinxo.AppWeb.models.Item;
import net.ddns.djpinxo.AppWeb.services.ApiService;
import net.ddns.djpinxo.AppWeb.utils.SecurityInterceptor;

@Controller
public class ItemController {

	@Autowired
    private ApiService apiService;

	@GetMapping("/items")
	public String homeItems(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		return "redirect:/items/view";
	}
	
	@GetMapping("/items/view")
	public String getItems(HttpSession session, Model model, @RequestParam(required = false) String query) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			if (query==null) {
				model.addAttribute("items", apiService.getItems());
			}
			else {
				model.addAttribute("items", apiService.getItems(query));
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
		return "items/view";
	}

	@GetMapping("/items/select/{id}")
	public String getItem(HttpSession session, Model model, @PathVariable Long id) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			model.addAttribute("item", apiService.getItem(id));
			return "items/select";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/items/view";
	}
	
	@GetMapping("/items/insert")
	public String getInsertItem(HttpSession session, Model model) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		return "items/insert";
	}

	@PostMapping("/items/insert")
	public String postInsertItem(HttpSession session, Model model, @RequestParam(value="nombre") String nombre, @RequestParam(value="descripcion") String descripcion, @RequestParam(value="contenedorId") String sContenedorId) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			Item item = insertItem(nombre, descripcion, sContenedorId);
			if (item != null) {
				return "redirect:/items/select/"+ item.getId();
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
		Contenedor contenedor = (sContenedorId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorId), null, null, null, null, null);
		model.addAttribute("item", new Item(null, nombre, descripcion, contenedor));
		return "items/insert";
	}
	
	private Item insertItem(String nombre, String descripcion, String sContenedorId) throws Exception{

        if(validateInsertItemForm(nombre, descripcion, sContenedorId)){
            
        	Contenedor contenedor = (sContenedorId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorId), null, null, null, null, null);
            Item item=new Item(0l, nombre, descripcion, contenedor);
            
            return apiService.insertItem(item);
        }
        else {
            return null;
        }
    }

    private boolean validateInsertItemForm(String nombre, String descripcion, String sContenedorId) {
        
        boolean result=true;
        if(nombre.isEmpty()) {
            result = false;
        }
        if(!sContenedorId.isEmpty()){
            try{
                Long.valueOf(sContenedorId);
            }catch (NumberFormatException e) {
                result = false;
            }
        }

        return result;
    }

    @GetMapping("/items/update/{id}")
	public String getUpdateItem(HttpSession session, Model model, @PathVariable Long id) {
    	if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try{
			model.addAttribute("item", apiService.getItem(id));
			return "items/update";
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/items/view";	
	}
    
	@PostMapping("/items/update/{id}")
	public String postUpdateItem(HttpSession session, Model model, @PathVariable Long id, @RequestParam(value="nombre") String nombre, @RequestParam(value="descripcion") String descripcion, @RequestParam(value="contenedorId") String sContenedorId) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			Item item = updateItem(id, nombre, descripcion, sContenedorId);
			if (item != null) {
				return "redirect:/items/select/"+ item.getId();
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
		Contenedor contenedor = (sContenedorId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorId), null, null, null, null, null);
		model.addAttribute("item", new Item(id, nombre, descripcion, contenedor));
		return "items/update";
	}
	
	private Item updateItem(long id, String nombre, String descripcion, String sContenedorId) throws Exception{
        
        if(validateUpdateItemForm(id, nombre, descripcion, sContenedorId)){
        	Item item = apiService.getItem(id);
            Contenedor contenedor = (sContenedorId.isEmpty())?null:new Contenedor(Long.valueOf(sContenedorId), null, null, null, null, null);
            item.setNombre(nombre);
            item.setDescripcion(descripcion);
            item.setContenedor(contenedor);
            
            return apiService.updateItem(id, item);
        }
        else {
        	return null;
        }
    }

    private boolean validateUpdateItemForm(long id, String nombre, String descripcion, String sContenedorId) throws Exception{
        
        boolean result = true;
        if(nombre.isEmpty()) {
            result = false;
        }
        if(!sContenedorId.isEmpty()){
            try{
                Long.valueOf(sContenedorId);
            }catch (NumberFormatException e) {
                result = false;
            }
        }

        return result;
    }
	
	@GetMapping("/items/delete/{id}")
	public String getDeleteItem(HttpSession session, Model model, @PathVariable Long id) {
		if(!SecurityInterceptor.validateSession(session, model))return "redirect:/logout";
		try {
			apiService.deleteItem(id);
		}
		catch (HttpClientErrorException.Unauthorized e) {
			model.addAttribute("error", "error - " + e.getMessage());
			return "redirect:/logout";
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error - " + e.getMessage());
		}
		return "redirect:/items/view";
	}
	
	@GetMapping("/items/select/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagen(HttpSession session, Model model, @PathVariable Long id) {
    	
    	if(!SecurityInterceptor.validateSession(session, model))return null;
		try{
			return apiService.getItemImagen(id);
		}
		catch (HttpClientErrorException.Unauthorized e) {
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    /*
    @PostMapping("/items/{id}/imagen")
    public ResponseEntity<String> subirImagen(@PathVariable Long id,@RequestPart MultipartFile file) {
    	try {
    		Optional <Item> itemSaved = itemRepository.findById(id);
	    	if (itemSaved.isPresent()) {
	    		Imagen imagen = new Imagen();
	    		if (itemSaved.get().getImagen()!=null) {
	    			imagen = itemSaved.get().getImagen();
	    		}
	            imagen.setNombre(file.getOriginalFilename());
	            imagen.setTipo(file.getContentType());
				imagen.setDatos(file.getBytes());
				itemSaved.get().setImagen(imagen);
				imagen=imagenRepository.save(imagen);
				Item itemSavedFinal = itemRepository.save(itemSaved.get());
				
				return ResponseEntity.status(HttpStatus.OK).body("Imagen subida con éxito: " + itemSavedFinal.getImagen().getId());
	            
	    	}
    	} catch (IOException e) {
		}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
*/
}
