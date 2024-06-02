package net.ddns.djpinxo.dtos;

public class ItemDTO {
	
	private Long id;
    private String nombre;
    private String descripcion;
    private Long contenedorId;
    
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
	public Long getContenedorId() {
		return contenedorId;
	}
	public void setContenedorId(Long contenedorId) {
		this.contenedorId = contenedorId;
	}

}
