package net.ddns.djpinxo.warecontrol.data.model;

import java.sql.Date;
import java.time.LocalDate;

public class User {
    private String email;
    private String nombre;
    private String password;
    private String insertDate;
    private boolean admin;
    private boolean active;
    private String lastLogin;

    public User() {
        super();
    }

    public User(String email, String nombre, String password) {
        super();
        this.email = email;
        this.nombre = nombre;
        this.password = password;
    }

    public User(String email, String nombre, String password, boolean admin, boolean active) {
        super();
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.admin = admin;
        this.active = active;
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
}
