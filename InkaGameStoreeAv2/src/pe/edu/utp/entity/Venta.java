package pe.edu.utp.entity;

public class Venta {
    
    private int ventaID;
    private String clienteID;
    private String usuarioID;
    private double total;
    private String fecha;
    private String hora;

    public Venta() {
    }

    public Venta(int ventaID, String clienteID, String usuarioID, double total, String fecha, String hora) {
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

    public String getClienteID() {
        return clienteID;
    }

    public void setClienteID(String clienteID) {
        this.clienteID = clienteID;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
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
