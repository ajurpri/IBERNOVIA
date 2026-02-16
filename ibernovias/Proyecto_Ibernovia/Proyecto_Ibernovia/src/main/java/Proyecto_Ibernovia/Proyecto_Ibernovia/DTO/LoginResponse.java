package Proyecto_Ibernovia.Proyecto_Ibernovia.DTO;

public class LoginResponse {
    private String token;
    private Long userId;
    private String email;
    private String nombre;
    private String apellido;
    private Boolean isAdmin;

    public LoginResponse(String token, Long userId, String email, String nombre, String apellido, Boolean isAdmin) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
