package pe.edu.utp.dto;

public class InicioSesionDTO {

    private int id;
    private String nombreCompleto;
    private String rol;

    public InicioSesionDTO() {
    }

    public InicioSesionDTO(int id, String nombreCompleto, String rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return "InicioSesionDTO{" + "id=" + id + ", nombreCompleto=" + nombreCompleto + ", rol=" + rol + '}';
    }


}
