package pe.edu.utp.entity;

public class DetalleVenta {

    private String productoID;
    private int cantidad;
    private double precio;
    private double subtotal;
    private String fechHoraAgreCarrito;

    public DetalleVenta() {
    }

    public DetalleVenta(String productoID, int cantidad, double precio, double subtotal, String fechHoraAgreCarrito) {
        this.productoID = productoID;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
        this.fechHoraAgreCarrito = fechHoraAgreCarrito;
    }

    public String getFechHoraAgreCarrito() {
        return fechHoraAgreCarrito;
    }

    public void setFechHoraAgreCarrito(String fechHoraAgreCarrito) {
        this.fechHoraAgreCarrito = fechHoraAgreCarrito;
    }

    
    public String getProductoID() {
        return productoID;
    }

    public void setProductoID(String productoID) {
        this.productoID = productoID;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + ", productoID=" + productoID + ", cantidad=" + cantidad + ", precio=" + precio + ", subtotal=" + subtotal + '}';
    }
}
