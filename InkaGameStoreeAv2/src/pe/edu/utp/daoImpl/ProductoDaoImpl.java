package pe.edu.utp.daoImpl;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import pe.edu.utp.config.Conexion;
import pe.edu.utp.dao.ProductoDao;
import pe.edu.utp.entity.Productos;

public class ProductoDaoImpl extends Conexion implements ProductoDao {

    @Override
    public boolean createProducto(Productos p) {
        PreparedStatement ps = null;
        java.sql.Connection con = getConexion();

        String sql = "INSERT INTO producto (Categoria_ID, NombreProducto, Proveedor_ID, Precio, Stock, Descripcion, FechaHoraAgregado, Imagen, ImagenRuta) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            int CategoriaID = Integer.parseInt(p.getCategoria());
            ps.setInt(1, CategoriaID);
            ps.setString(2, p.getNombreProducto());
            int ProveedorID = Integer.parseInt(p.getProveedor());
            ps.setInt(3, ProveedorID);
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getDescripcion());
            ps.setString(7, p.getFechaHoraAgregada());
            ps.setString(9, p.getImagenRuta());
            FileInputStream archivofoto = new FileInputStream(p.getImagenRuta());
            ps.setBinaryStream(8, archivofoto);

            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } catch (FileNotFoundException e) {
            System.err.println("Archivo de imagen no encontrado: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    @Override
    public boolean updateProducto(Productos p) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE producto SET Categoria_ID = ?, NombreProducto = ?, Proveedor_ID = ?, Precio = ?, Stock = ?, Descripcion = ?, Imagen = ?, ImagenRuta = ? WHERE Producto_ID = ?";

        try {
            ps = con.prepareStatement(sql);
            int CategoriaID = Integer.parseInt(p.getCategoria());
            ps.setInt(1, CategoriaID);
            System.out.println(CategoriaID);
            ps.setString(2, p.getNombreProducto());
            int ProveedorID = Integer.parseInt(p.getProveedor());
            ps.setInt(3, ProveedorID);
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getDescripcion());
            ps.setString(8, p.getImagenRuta());
            FileInputStream archivofoto = new FileInputStream(p.getImagenRuta());
            ps.setBinaryStream(7, archivofoto);
            ps.setInt(9, p.getProducto_ID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } catch (FileNotFoundException e) {
            System.err.println("Archivo de imagen no encontrado: " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    @Override
    public boolean deleteProducto(int p) {
        PreparedStatement ps = null;
        java.sql.Connection con = getConexion();
        String sql = "DELETE FROM producto WHERE Producto_ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, p);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
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
    }  

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
    public List<Productos> readAllProductos() {
        List<Productos> listaProductos = new ArrayList<>();
        String sql = "SELECT p.Producto_ID, p.NombreProducto, c.NombreCategoria, prov.NombreCompleto, p.Precio, p.Stock, p.Descripcion, p.FechaHoraAgregado, p.Imagen, p.ImagenRuta "
                + "FROM producto p "
                + "INNER JOIN categoria c ON p.Categoria_ID = c.Categoria_ID "
                + "INNER JOIN proveedor prov ON p.Proveedor_ID = prov.Proveedor_ID "
                + "ORDER BY p.Producto_ID";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int productoID = rs.getInt("Producto_ID");
                String nombreProducto = rs.getString("NombreProducto");
                String nombreCategoria = rs.getString("NombreCategoria");
                String nombreProveedor = rs.getString("NombreCompleto");
                double precio = rs.getDouble("Precio");
                int stock = rs.getInt("Stock");
                String descripcion = rs.getString("Descripcion");
                String fechaHoraAgregada = rs.getString("FechaHoraAgregado");
                byte[] imagenBytes = rs.getBytes("Imagen");
                ImageIcon imagenT = null;
                if (imagenBytes != null) {
                    ImageIcon icon = new ImageIcon(imagenBytes);
                    Image img = icon.getImage();
                    Image newImg = img.getScaledInstance(125, 185, Image.SCALE_SMOOTH);
                    imagenT = new ImageIcon(newImg);
                }
                String imagenRuta = rs.getString("ImagenRuta");

                // Crear un objeto Productos con los datos obtenidos de la consulta
                Productos producto = new Productos(productoID, nombreCategoria, nombreProducto, nombreProveedor, precio, stock, descripcion, fechaHoraAgregada, imagenT, imagenRuta);
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaProductos;
    }

    @Override
    public List<Productos> readProductos(String nombreProductoBuscado) {
        List<Productos> listaProductos = new ArrayList<>();
        String sql = "SELECT p.Producto_ID, p.NombreProducto, c.NombreCategoria, prov.NombreCompleto, p.Precio, p.Stock, "
                + "p.Descripcion, p.FechaHoraAgregado, p.Imagen, p.ImagenRuta "
                + "FROM producto p "
                + "INNER JOIN categoria c ON p.Categoria_ID = c.Categoria_ID "
                + "INNER JOIN proveedor prov ON p.Proveedor_ID = prov.Proveedor_ID "
                + "WHERE LOWER(p.NombreProducto) LIKE LOWER(?) "
                + "ORDER BY p.Producto_ID";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Ajustar el parámetro de búsqueda para el LIKE
            ps.setString(1, "%" + nombreProductoBuscado + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productoID = rs.getInt("Producto_ID");
                    String nombreProducto = rs.getString("NombreProducto");
                    String nombreCategoria = rs.getString("NombreCategoria");
                    String nombreProveedor = rs.getString("NombreCompleto");
                    double precio = rs.getDouble("Precio");
                    int stock = rs.getInt("Stock");
                    String descripcion = rs.getString("Descripcion");
                    String fechaHoraAgregada = rs.getString("FechaHoraAgregado");
                    byte[] imagenBytes = rs.getBytes("Imagen");
                    ImageIcon imagenT = null;

                    if (imagenBytes != null) {
                        ImageIcon icon = new ImageIcon(imagenBytes);
                        Image img = icon.getImage();
                        Image newImg = img.getScaledInstance(125, 185, Image.SCALE_SMOOTH);
                        imagenT = new ImageIcon(newImg);
                    }

                    String imagenRuta = rs.getString("ImagenRuta");

                    Productos producto = new Productos(productoID, nombreCategoria, nombreProducto, nombreProveedor, precio, stock, descripcion, fechaHoraAgregada, imagenT, imagenRuta);
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaProductos;
    }

}
