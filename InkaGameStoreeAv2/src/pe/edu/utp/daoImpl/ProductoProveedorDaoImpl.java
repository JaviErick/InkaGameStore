package pe.edu.utp.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.config.Conexion;
import pe.edu.utp.dao.ProductoProveedorDao;
import pe.edu.utp.entity.ProductoProveedor;

public class ProductoProveedorDaoImpl extends Conexion implements ProductoProveedorDao {

    @Override
    public List<String> getProveedores() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT NombreCompleto FROM Proveedor";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombreCategoria = rs.getString("NombreCompleto");
                categorias.add(nombreCategoria);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }
        return categorias;
    }

    @Override
    public int ObtenerIDProveedor(String nombreProveedor) {
        int categoriaId = -1; // Valor por defecto en caso de no encontrar la categoría
        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection con = getConexion();
        String sql = "SELECT Proveedor_ID FROM proveedor WHERE NombreCompleto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreProveedor);
            rs = ps.executeQuery();

            if (rs.next()) {
                categoriaId = rs.getInt("Proveedor_ID");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID de la categoría: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return categoriaId;
    }

    @Override
    public List<String> getCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT NombreCategoria FROM categoria";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombreCategoria = rs.getString("NombreCategoria");
                categorias.add(nombreCategoria);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return categorias;
    }    //para el item

    @Override
    public int ObtenerIDCategoria(String nombreCategoria) {                                      //metodo2
        int categoriaId = -1; // Valor por defecto en caso de no encontrar la categoría
        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection con = getConexion();
        String sql = "SELECT Categoria_ID FROM categoria WHERE NombreCategoria = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreCategoria);
            rs = ps.executeQuery();

            if (rs.next()) {
                categoriaId = rs.getInt("Categoria_ID");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID de la categoría: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return categoriaId;
    }

    @Override
    public List<ProductoProveedor> buscarProducto(String nombreBuscado) {
        List<ProductoProveedor> productos = new ArrayList<>();
        java.sql.Connection con = getConexion();
        String sql = "SELECT d.ProductoPro_ID, d.NombreProductoPro, a.NombreCompleto AS proveedorNombre, e.NombreCategoria, "
                + "d.Descripcion, d.Precio "
                + "FROM ProductosProveedor d "
                + "INNER JOIN Proveedor a ON d.Proveedor_ID = a.Proveedor_ID "
                + "INNER JOIN Categoria e ON d.Categoria_ID = e.Categoria_ID "
                + "WHERE d.ProductoPro_ID LIKE ? OR d.NombreProductoPro LIKE ? OR a.NombreCompleto LIKE ? OR e.NombreCategoria LIKE ?;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombreBuscado + "%");
            ps.setString(2, "%" + nombreBuscado + "%");
            ps.setString(3, "%" + nombreBuscado + "%");
            ps.setString(4, "%" + nombreBuscado + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int productoID = rs.getInt("ProductoPro_ID");
                String nombreProductoPro = rs.getString("NombreProductoPro");
                String proveedorNombre = rs.getString("proveedorNombre");
                String categoriaNombre = rs.getString("NombreCategoria");
                String descripcion = rs.getString("Descripcion");
                double precio = rs.getDouble("Precio");

                ProductoProveedor producto = new ProductoProveedor(productoID, proveedorNombre, categoriaNombre, descripcion, precio, nombreProductoPro);
                productos.add(producto);  // Agregar el producto a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<ProductoProveedor> filtrarPorProveedor(int Proveedor_ID) {   //
        List<ProductoProveedor> proveedor = new ArrayList<>();
        java.sql.Connection con = getConexion();
        String sql = "SELECT d.ProductoPro_ID, a.NombreCompleto, e.NombreCategoria, d.Descripcion, "
                + "d.Precio, d.NombreProductoPro "
                + "FROM ProductosProveedor d "
                + "INNER JOIN Proveedor a ON d.Proveedor_ID = a.Proveedor_ID "
                + "INNER JOIN Categoria e ON d.Categoria_ID = e.Categoria_ID "
                + "WHERE d.Proveedor_ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Proveedor_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Producto = rs.getInt("ProductoPro_ID");
                String Proveedor = rs.getString("NombreCompleto");
                String Categoria = rs.getString("NombreCategoria");
                String Descripcion = rs.getString("Descripcion");
                double Precio = rs.getDouble("Precio");
                String NombreProductoPro = rs.getString("NombreProductoPro");

                ProductoProveedor prov = new ProductoProveedor(Producto, Proveedor, Categoria, Descripcion, Precio, NombreProductoPro);
                proveedor.add(prov);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }

    @Override
    public List<ProductoProveedor> filtrarPorCategoria(int Categoria_ID) {
        List<ProductoProveedor> productos = new ArrayList<>();
        java.sql.Connection con = getConexion();
        String sql = "SELECT d.ProductoPro_ID, a.NombreCompleto, e.NombreCategoria, d.Descripcion, "
                + "d.Precio, d.NombreProductoPro "
                + "FROM ProductosProveedor d "
                + "INNER JOIN Proveedor a ON d.Proveedor_ID = a.Proveedor_ID "
                + "INNER JOIN Categoria e ON d.Categoria_ID = e.Categoria_ID "
                + "WHERE d.Categoria_ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Categoria_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Producto = rs.getInt("ProductoPro_ID");
                String Proveedor = rs.getString("NombreCompleto");
                String Categoria = rs.getString("NombreCategoria");
                String Descripcion = rs.getString("Descripcion");
                double Precio = rs.getDouble("Precio");
                String NombreProductoPro = rs.getString("NombreProductoPro");

                ProductoProveedor prov = new ProductoProveedor(Producto, Proveedor, Categoria, Descripcion, Precio, NombreProductoPro);
                productos.add(prov);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<ProductoProveedor> filtrarPorRangoDePrecio(double precioMin, double precioMax) {
        List<ProductoProveedor> productos = new ArrayList<>();
        java.sql.Connection con = getConexion();
        String sql = "SELECT d.ProductoPro_ID, d.NombreProductoPro, a.NombreCompleto AS proveedorNombre, e.NombreCategoria, "
                + "d.Descripcion, d.Precio "
                + "FROM ProductosProveedor d "
                + "INNER JOIN Proveedor a ON d.Proveedor_ID = a.Proveedor_ID "
                + "INNER JOIN Categoria e ON d.Categoria_ID = e.Categoria_ID "
                + "WHERE d.Precio BETWEEN ? AND ?;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, precioMin);
            ps.setDouble(2, precioMax);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int productoID = rs.getInt("ProductoPro_ID");
                String nombreProductoPro = rs.getString("NombreProductoPro");
                String proveedorNombre = rs.getString("proveedorNombre");
                String categoriaNombre = rs.getString("NombreCategoria");
                String descripcion = rs.getString("Descripcion");
                double precio = rs.getDouble("Precio");

                ProductoProveedor producto = new ProductoProveedor(productoID, proveedorNombre, categoriaNombre, descripcion, precio, nombreProductoPro);
                productos.add(producto);  // Agregar el producto a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<ProductoProveedor> readAllProductoProveedor() {
        List<ProductoProveedor> listaProdProv = new ArrayList<>();
        String sql = "SELECT d.ProductoPro_ID, a.NombreCompleto, e.NombreCategoria, d.Descripcion, "
                + "d.Precio, d.NombreProductoPro "
                + "FROM ProductosProveedor d "
                + "INNER JOIN Proveedor a ON d.Proveedor_ID = a.Proveedor_ID "
                + "INNER JOIN Categoria e ON d.Categoria_ID = e.Categoria_ID";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int Producto = rs.getInt("ProductoPro_ID");
                String Proveedor = rs.getString("NombreCompleto");
                String Categoria = rs.getString("NombreCategoria");
                String Descripcion = rs.getString("Descripcion");
                double Precio = rs.getDouble("Precio");
                String NombreProductoPro = rs.getString("NombreProductoPro");

                ProductoProveedor prov = new ProductoProveedor(Producto, Proveedor, Categoria, Descripcion, Precio, NombreProductoPro);
                listaProdProv.add(prov);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaProdProv;
    }

}
