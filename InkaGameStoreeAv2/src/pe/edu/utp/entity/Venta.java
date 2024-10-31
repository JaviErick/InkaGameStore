package pe.edu.utp.entity;

public class Venta {
    
    private int ventaID;
    private int clienteID;
    private int usuarioID;
    private double total;
    private String fecha;
    private String hora;

    public Venta() {
    }

    public Venta(int ventaID, int clienteID, int usuarioID, double total, String fecha, String hora) {
        this.ventaID = ventaID;
        this.clienteID = clienteID;
        this.usuarioID = usuarioID;
        this.total = total;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getVentaID() {
        return ventaID;
    }

    public void setVentaID(int ventaID) {
        this.ventaID = ventaID;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Venta{" + "ventaID=" + ventaID + ", clienteID=" + clienteID + ", usuarioID=" + usuarioID + ", total=" + total + ", fecha=" + fecha + ", hora=" + hora + '}';
    }

}
