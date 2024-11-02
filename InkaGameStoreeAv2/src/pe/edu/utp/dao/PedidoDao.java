package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.DetallePedido;
import pe.edu.utp.entity.Pedido;
import pe.edu.utp.entity.ProductoProveedor;

public interface PedidoDao {
    public List<ProductoProveedor> BuscarProducto(String nombreProductoBuscado, String Proveedor);
    public boolean createPedido(Pedido p, List<DetallePedido> detallePedido);
    public List<String> getProveedores();
    public List<Pedido> ListarAllPedidos();
    public List<DetallePedido> MostrarDetallePedido(int IDPedido);
    public int ObtenerIDProveedor(String nombreProveedor);
    public List<String> getCategorias();
    public int ObtenerIDCategoria(String nombreCategoria);
    public List<Pedido> buscarPedido(String nombreBuscado);
    public List<Pedido> filtrarPorProveedor(int Proveedor_ID);
    public List<Pedido> filtrarPorCategoria(int Categoria_ID);
    public List<Pedido> filtrarPorRangoDePrecio(double precioMin, double precioMax);
    public boolean updatePedido(Pedido p);
}
