package pe.edu.utp.entity;

public class Carritoo {

    private int idProducto;
    private String nombreProducto;
    private double precio;
    private int cantidad;
    private String fecha;
    private String hora;
    private double subtotal;

    public Carritoo() {
    }

    public Carritoo(int idProducto, String nombreProducto, double precio, int cantidad, String fecha, String hora, double subtotal) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.hora = hora;
        this.subtotal = subtotal;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Carritoo{" + "idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", precio=" + precio + ", cantidad=" + cantidad + ", fecha=" + fecha + ", hora=" + hora + ", subtotal=" + subtotal + '}';
    }
    
    
}
