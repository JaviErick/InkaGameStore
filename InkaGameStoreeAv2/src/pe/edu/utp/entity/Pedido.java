package pe.edu.utp.entity;

public class Pedido {
    private int pedidoID;
    private String NombreUsuario;
    private String FechaRealizada;
    private String FechaEntrega;
    private String Estado;
    private double total;

    public Pedido() {
    }

    public Pedido(int pedidoID, String NombreUsuario, String FechaRealizada, String FechaEntrega, String Estado, double total) {
        this.pedidoID = pedidoID;
        this.NombreUsuario = NombreUsuario;
        this.FechaRealizada = FechaRealizada;
        this.FechaEntrega = FechaEntrega;
        this.Estado = Estado;
        this.total = total;
    }

    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getFechaRealizada() {
        return FechaRealizada;
    }

    public void setFechaRealizada(String FechaRealizada) {
        this.FechaRealizada = FechaRealizada;
    }

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(String FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" + "pedidoID=" + pedidoID + ", NombreUsuario=" + NombreUsuario + ", FechaRealizada=" + FechaRealizada + ", FechaEntrega=" + FechaEntrega + ", Estado=" + Estado + ", total=" + total + '}';
    }

}
