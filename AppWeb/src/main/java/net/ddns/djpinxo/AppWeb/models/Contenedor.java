package net.ddns.djpinxo.AppWeb.models;

import java.util.List;


public class Contenedor {
	
	private Long id;
	private String nombre;
	private String descripcion;
	private Contenedor contenedorPadre;
	private List<Contenedor> contenedorHijos;
	private List<Item> items;
	
	public Contenedor() {
		super();
	}
	
	public Contenedor(Long id, String nombre, String descripcion, Contenedor contenedorPadre, List<Contenedor> contenedorHijos, List<Item> items) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.contenedorPadre = contenedorPadre;
		this.contenedorHijos = contenedorHijos;
		this.items = items;
	}


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

	public Contenedor getContenedorPadre() {
		return contenedorPadre;
	}

	public void setContenedorPadre(Contenedor contenedorPadre) {
		this.contenedorPadre = contenedorPadre;
	}

	public List<Contenedor> getContenedorHijos() {
		return contenedorHijos;
	}

	public void setContenedorHijos(List<Contenedor> contenedorHijos) {
		this.contenedorHijos = contenedorHijos;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	

}
