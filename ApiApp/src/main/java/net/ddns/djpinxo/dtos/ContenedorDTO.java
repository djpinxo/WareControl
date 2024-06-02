package net.ddns.djpinxo.dtos;

import java.util.List;


public class ContenedorDTO {
	
	private Long id;
    private String nombre;
    private String descripcion;
    private Long contenedorPadreId;
    private List<ContenedorDTO> contenedorHijos;
	private List<ItemDTO> items;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getContenedorPadreId() {
		return contenedorPadreId;
	}
	public void setContenedorPadreId(Long contenedorPadreId) {
		this.contenedorPadreId = contenedorPadreId;
	}
	public List<ContenedorDTO> getContenedorHijos() {
		return contenedorHijos;
	}
	public void setContenedorHijos(List<ContenedorDTO> contenedorHijos) {
		this.contenedorHijos = contenedorHijos;
	}
	public List<ItemDTO> getItems() {
		return items;
	}
	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}
	
    
    

    // Getters y setters

}
