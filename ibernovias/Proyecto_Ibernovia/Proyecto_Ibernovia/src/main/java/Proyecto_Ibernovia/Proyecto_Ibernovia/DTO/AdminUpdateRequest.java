package Proyecto_Ibernovia.Proyecto_Ibernovia.DTO;

public class AdminUpdateRequest {
    private Boolean isAdmin;
    private Boolean activo;

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
