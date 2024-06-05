package net.ddns.djpinxo.AppWeb.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import net.ddns.djpinxo.AppWeb.CustomClientHttpRequestInterceptor;
import net.ddns.djpinxo.AppWeb.models.Contenedor;
import net.ddns.djpinxo.AppWeb.models.Item;
import net.ddns.djpinxo.AppWeb.models.Usuario;



@Service
public class ApiService {
	
	private final String baseURL = "http://djpinxo.ddns.net:81/";
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
   
    
    public List<Usuario> getUsuarios() throws Exception {
    	return restTemplate.getForObject(baseURL+"usuarios", List.class);
    }

    
    public Usuario getUsuario(String userEmail) throws Exception {
    	return restTemplate.getForObject(baseURL+"usuarios/"+userEmail, Usuario.class);
    }

    
    public Usuario insertUsuario(Usuario usuario) throws Exception {        
    	return restTemplate.postForObject(baseURL+"usuarios", usuario, Usuario.class);
    }

    public Usuario updateUsuario(String userEmail, Usuario usuario) throws Exception {
    	try{
    		restTemplate.put(baseURL+"usuarios/"+userEmail, usuario);
    		return usuario;
    	}catch (Exception e) {
    		throw e;
		}
    }

    public void deleteUsuario(String userEmail) throws Exception {
    	restTemplate.delete(baseURL+"usuarios/"+userEmail);
    }




    public List<Item> getItems() throws Exception {
    	return restTemplate.getForObject(baseURL+"items", List.class);
    }

    public List<Item> getItems(String query) throws Exception {
    	return restTemplate.getForObject(baseURL+"items?query="+query, List.class);
    }

    public Item getItem(long id) throws Exception {
    	return restTemplate.getForObject(baseURL+"items/"+id, Item.class);
    }

    public Item insertItem(Item item) throws Exception {
    	return restTemplate.postForObject(baseURL+"items", item, Item.class);
    }

    public Item updateItem(long id, Item item) throws Exception {
    	try{
    		restTemplate.put(baseURL+"items/"+id, item);
    		return item;
    	}catch (Exception e) {
    		throw e;
		}
    }

    public void deleteItem(long id) throws Exception {
    	restTemplate.delete(baseURL+"items/"+id);
    }

    //@Multipart
    public MultiValueMap<String, Object> getItemImagen(long id) throws Exception {
    	return restTemplate.getForObject(baseURL+"items/"+id+"/imagen", MultiValueMap.class);
    }

    public void insertItemImagen(long id, MultiValueMap imagen) throws Exception {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(imagen, headers);
    	restTemplate.postForObject(baseURL+"items/"+id+"/imagen", imagen, Void.class);
    }



    public List<Contenedor> getContenedores() throws Exception {
    	return restTemplate.getForObject(baseURL+"contenedores", List.class);
    }

    public List<Contenedor> getContenedores(String query) throws Exception {
    	return restTemplate.getForObject(baseURL+"contenedores?query="+query, List.class);
    }

    public Contenedor getContenedor(long id) throws Exception {
    	return restTemplate.getForObject(baseURL+"contenedores/"+id, Contenedor.class);
    }

    public Contenedor insertContenedor(Contenedor contenedor) throws Exception {
    	return restTemplate.postForObject(baseURL+"contenedores", contenedor, Contenedor.class);
    }

    public Contenedor updateContenedor(long id, Contenedor contenedor) throws Exception {
    	try{
    		restTemplate.put(baseURL+"contenedores/"+id, contenedor);
    		return contenedor;
    	}catch (Exception e) {
    		throw e;
		}
    }

    public void deleteContenedor(long id) throws Exception {
    	restTemplate.delete(baseURL+"contenedores/"+id);
    }

    public List<Item> getContenedorItems(long id) throws Exception {
    	return restTemplate.getForObject(baseURL+"contenedores/"+id+"/items", List.class);
    }

    public List<Contenedor> getContenedorContenedorHijos(long id) throws Exception {
    	return restTemplate.getForObject(baseURL+"contenedores/"+id+"/contenedorhijos", List.class);
    }


    public Usuario loginUsuario(Usuario usuario) {
    	return restTemplate.postForObject(baseURL+"login", usuario, Usuario.class);
    }

    public Usuario registerUsuario(Usuario usuario) {        
    	return restTemplate.postForObject(baseURL+"register", usuario, Usuario.class);
    }
    
    public void setInterceptor(Usuario usuarioLogin) {
    	restTemplate.setInterceptors(Collections.singletonList(new CustomClientHttpRequestInterceptor(usuarioLogin.getEmail(), usuarioLogin.getPassword())));
    }
    public void removeInterceptor() {
    	restTemplate.setInterceptors( new ArrayList<ClientHttpRequestInterceptor>());
    }
}