package net.ddns.djpinxo.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contenedor {
	
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private String descripcion;
	@ManyToOne
	private Contenedor contenedorPadre;
	@OneToMany(mappedBy = "contenedorPadre")
	@JsonIgnore
	private List<Contenedor> contenedorHijos;
	@OneToMany(mappedBy = "contenedor")
	@JsonIgnore
	private List<Item> items;
	
	public Contenedor() {
		super();
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
