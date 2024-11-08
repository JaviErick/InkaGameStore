package pe.edu.utp.entity;

public class Proveedor {

    private int proveedorID; 
    private String nombreCompleto;
    private String ruc;
    private String telefono; 
    private String direccion; 
    private String correo;

    public Proveedor() {
    }

    public Proveedor(int proveedorID, String nombreCompleto, String ruc, String telefono, String direccion, String correo) {
        this.proveedorID = proveedorID;
        this.nombreCompleto = nombreCompleto;
        this.ruc = ruc;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public int getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(int proveedorID) {
        this.proveedorID = proveedorID;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "proveedorID=" + proveedorID + ", nombreCompleto=" + nombreCompleto + ", ruc=" + ruc + ", telefono=" + telefono + ", direccion=" + direccion + ", correo=" + correo + '}';
    }
    
    
    
}
