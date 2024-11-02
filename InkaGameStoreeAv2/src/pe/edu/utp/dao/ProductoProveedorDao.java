package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.ProductoProveedor;

public interface ProductoProveedorDao {

    public List<ProductoProveedor> buscarProducto(String nombreBuscado);

    public List<ProductoProveedor> filtrarPorProveedor(int Proveedor_ID);

    public List<ProductoProveedor> filtrarPorRangoDePrecio(double precioMin, double precioMax);

    public List<ProductoProveedor> readAllProductoProveedor();

    public List<String> getCategorias();

    public int ObtenerIDCategoria(String nombreCategoria);

    public List<ProductoProveedor> filtrarPorCategoria(int Categoria_ID);

    public int ObtenerIDProveedor(String nombreProveedor);

    public List<String> getProveedores();
}
