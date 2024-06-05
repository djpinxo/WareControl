package net.ddns.djpinxo.AppWeb.models;


public class Item {

	private Long id;
	private String nombre;
	private String descripcion;
	private Contenedor contenedor;
	private Imagen imagen;

	public Item() {
		super();
	}
	
	public Item(Long id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Item(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public Item(Long id, String nombre, String descripcion, Contenedor contenedor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.contenedor = contenedor;
	}
	
	public Item(String nombre, String descripcion, Contenedor contenedor) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.contenedor = contenedor;
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

	public Contenedor getContenedor() {
		return contenedor;
	}

	public void setContenedor(Contenedor contenedor) {
		this.contenedor = contenedor;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
	

}
