package pe.edu.utp.entity;

public class Usuario {
    
    private int Usuario_ID;
    private String Rol;
    private String NombreCompleto;
    private String DNI;
    private String Telefono;
    private String Correo;
    private String Contraseña;
    

    public Usuario() {
    }

    public Usuario(int Usuario_ID, String Rol, String NombreCompleto, String DNI, String Telefono, String Correo, String Contraseña) {
        this.Usuario_ID = Usuario_ID;
        this.Rol = Rol;
        this.NombreCompleto = NombreCompleto;
        this.DNI = DNI;
        this.Telefono = Telefono;
        this.Correo = Correo;
        this.Contraseña = Contraseña;
        
    }

    public int getUsuario_ID() {
        return Usuario_ID;
    }

    public void setUsuario_ID(int Usuario_ID) {
        this.Usuario_ID = Usuario_ID;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }
    
    public String getContraseñaOculta() {
        return "*********";  // Método para obtener la representación oculta
    }

    @Override
    public String toString() {
        return "Empleado{" + "Usuario_ID=" + Usuario_ID + ", Rol=" + Rol + ", NombreCompleto=" + NombreCompleto + ", DNI=" + DNI + ", Telefono=" + Telefono + ", Correo=" + Correo + ", Contraseña=" + Contraseña + '}';
    }

}

