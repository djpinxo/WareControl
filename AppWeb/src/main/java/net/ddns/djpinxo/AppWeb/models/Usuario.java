package net.ddns.djpinxo.AppWeb.models;





public class Usuario {
	
	private String email;
	private String nombre;
	private String password;
	private String insertDate;
	private boolean admin;
	private boolean active;
	private String lastLogin;
	private String updateDate;

	public Usuario(String email, String nombre, String password, boolean active, boolean admin) {
		super();
		this.email = email;
		this.nombre = nombre;
		this.password = password;
		this.active = active;
		this.admin = admin;
	}
	
	public Usuario() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", nombre=" + nombre + ", password=" + password + ", insertDate="
				+ insertDate + ", admin=" + admin + ", active=" + active + ", lastLogin=" + lastLogin + ", updateDate="
				+ updateDate + "]";
	}

	

	
	

}
