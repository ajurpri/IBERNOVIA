package Proyecto_Ibernovia.Proyecto_Ibernovia.DTO;

import java.time.LocalDateTime;

public class AdminUserResponse {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private Boolean activo;
    private Boolean isAdmin;
    private LocalDateTime createdAt;

    public AdminUserResponse(Long id, String email, String nombre, String apellido, Boolean activo, Boolean isAdmin, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = activo;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
