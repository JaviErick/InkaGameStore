
package pe.edu.utp.entity;


public class Clientes {
    private int Cliente_ID;
    private String DNI;
    private String NombreCompleto;
    private String Telefono;
    private String Direccion;

    public Clientes() {
    }

    public Clientes(int Cliente_ID, String NombreCompleto, String Telefono, String Direccion, String DNI) {
        this.Cliente_ID = Cliente_ID;
        this.NombreCompleto = NombreCompleto;
        this.Telefono = Telefono;
        this.Direccion = Direccion;
        this.DNI = DNI;
    }

    public int getCliente_ID() {
        return Cliente_ID;
    }

    public void setCliente_ID(int Cliente_ID) {
        this.Cliente_ID = Cliente_ID;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    @Override
    public String toString() {
        return "Clientes{" + "Cliente_ID=" + Cliente_ID + ", NombreCompleto=" + NombreCompleto + ", Telefono=" + Telefono + ", Direccion=" + Direccion + ", DNI=" + DNI + '}';
    }
    
    
    
}
