package pe.edu.utp.entity;

public class DetallePedido {
    
    private int PedidoID;
    private String NombreProducto;
    private String NombreProveedor;
    private String NombreCategoria;
    private int Cantidad;
    private double Precio;
    private String FechaHoraAgregado;
    private double Subtotal;

    public DetallePedido() {
    }

    public DetallePedido(int PedidoID, String NombreProducto, String NombreProveedor, String NombreCategoria, int Cantidad, double Precio, String FechaHoraAgregado, double Subtotal) {
        this.PedidoID = PedidoID;
        this.NombreProducto = NombreProducto;
        this.NombreProveedor = NombreProveedor;
        this.NombreCategoria = NombreCategoria;
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.FechaHoraAgregado = FechaHoraAgregado;
        this.Subtotal = Subtotal;
    }

    public int getPedidoID() {
        return PedidoID;
    }

    public void setPedidoID(int PedidoID) {
        this.PedidoID = PedidoID;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String NombreProducto) {
        this.NombreProducto = NombreProducto;
    }

    public String getNombreProveedor() {
        return NombreProveedor;
    }

    public void setNombreProveedor(String NombreProveedor) {
        this.NombreProveedor = NombreProveedor;
    }

    public String getNombreCategoria() {
        return NombreCategoria;
    }

    public void setNombreCategoria(String NombreCategoria) {
        this.NombreCategoria = NombreCategoria;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public String getFechaHoraAgregado() {
        return FechaHoraAgregado;
    }

    public void setFechaHoraAgregado(String FechaHoraAgregado) {
        this.FechaHoraAgregado = FechaHoraAgregado;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(double Subtotal) {
        this.Subtotal = Subtotal;
    }

    @Override
    public String toString() {
        return "DetallePedido{" + "PedidoID=" + PedidoID + ", NombreProducto=" + NombreProducto + ", NombreProveedor=" + NombreProveedor + ", NombreCategoria=" + NombreCategoria + ", Cantidad=" + Cantidad + ", Precio=" + Precio + ", FechaHoraAgregado=" + FechaHoraAgregado + ", Subtotal=" + Subtotal + '}';
    }

    

}
