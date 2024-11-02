package pe.edu.utp.daoImpl;

import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.utp.dao.PedidoDao;
import pe.edu.utp.entity.DetallePedido;
import pe.edu.utp.entity.Pedido;
import pe.edu.utp.entity.ProductoProveedor;

public class PedidoDaoImpl extends Conexion implements PedidoDao {

    @Override
    public List<ProductoProveedor> BuscarProducto(String nombreProductoBuscado, String Proveedor) {
        List<ProductoProveedor> listaProv = new ArrayList<>();
        String sql = "SELECT d.ProductoPro_ID, d.NombreProductoPro, a.NombreCompleto AS proveedorNombre, e.NombreCategoria, "
                + "d.Descripcion, d.Precio "
                + "FROM ProductosProveedor d "
                + "INNER JOIN Proveedor a ON d.Proveedor_ID = a.Proveedor_ID "
                + "INNER JOIN Categoria e ON d.Categoria_ID = e.Categoria_ID "
                + "WHERE d.NombreProductoPro LIKE ? AND a.NombreCompleto LIKE ?;";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Ajustar el parámetro de búsqueda para el LIKE
            ps.setString(1, "%" + nombreProductoBuscado + "%");
            ps.setString(2, "%" + Proveedor + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productoID = rs.getInt("ProductoPro_ID");
                    String nombreProductoPro = rs.getString("NombreProductoPro");
                    String proveedorNombre = rs.getString("proveedorNombre");
                    String categoriaNombre = rs.getString("NombreCategoria");
                    String descripcion = rs.getString("Descripcion");
                    double precio = rs.getDouble("Precio");

                    ProductoProveedor producto = new ProductoProveedor(productoID, proveedorNombre, categoriaNombre, descripcion, precio, nombreProductoPro);
                    listaProv.add(producto);  // Agregar el producto a la lista
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaProv;
    }

    @Override
    public boolean createPedido(Pedido p, List<DetallePedido> detallePedido) {

        PreparedStatement psPed = null;
        PreparedStatement psDetalle = null;
        ResultSet rs = null;
        java.sql.Connection con = getConexion();

        String psPedido = "INSERT INTO Pedidos (Usuario_ID, FechaRealizaPedido, FechaEntrega, Estado, Total) VALUES (?, ?, ?, ?, ?)";
        String psDetallePedido = "INSERT INTO DetallePedido (Pedido_ID, Producto_ID, Cantidad, PrecioUnitario, FechaHoraAgregado, Subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            psPed = con.prepareStatement(psPedido, Statement.RETURN_GENERATED_KEYS);
            psPed.setInt(1, Integer.parseInt(p.getNombreUsuario()));
            psPed.setString(2, p.getFechaRealizada());
            psPed.setString(3, p.getFechaEntrega());
            psPed.setString(4, p.getEstado());
            psPed.setDouble(5, p.getTotal());

            int rowsAffected = psPed.executeUpdate();
            if (rowsAffected > 0) {

                rs = psPed.getGeneratedKeys();
                if (rs.next()) {
                    int pedidoID = rs.getInt(1);
                    psDetalle = con.prepareStatement(psDetallePedido);

                    for (DetallePedido detalle : detallePedido) {
                        psDetalle.setInt(1, pedidoID); // Venta_ID recuperado
                        psDetalle.setString(2, detalle.getNombreProducto());
                        psDetalle.setInt(3, detalle.getCantidad());
                        psDetalle.setDouble(4, detalle.getPrecio());
                        psDetalle.setString(5, detalle.getFechaHoraAgregado());
                        psDetalle.setDouble(6, detalle.getSubtotal());

                        psDetalle.addBatch();
                    }
                    psDetalle.executeBatch();
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psPed != null) {
                    psPed.close();
                }
                if (psDetalle != null) {
                    psDetalle.close();
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
    public List<Pedido> ListarAllPedidos() {
        List<Pedido> pedido = new ArrayList<>();
        String sql = "SELECT p.Pedido_ID, c.NombreCompleto AS NombreUsuario, p.FechaRealizaPedido, p.FechaEntrega, p.Estado, p.Total "
                + "FROM pedidos p "
                + "INNER JOIN usuario c ON c.Usuario_ID = p.Usuario_ID;";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int pedidoID = rs.getInt("Pedido_ID");
                String Usuario = rs.getString("NombreUsuario");
                String fechaRealizada = rs.getString("FechaRealizaPedido");
                String fechaEntrega = rs.getString("FechaEntrega");
                double total = rs.getDouble("Total");
                String estado = rs.getString("Estado");

                Pedido p = new Pedido();
                p.setPedidoID(pedidoID);
                p.setNombreUsuario(Usuario);
                p.setFechaRealizada(fechaRealizada);
                p.setFechaEntrega(fechaEntrega);
                p.setTotal(total);
                p.setEstado(estado);

                pedido.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }
        return pedido;
    }

    @Override
    public List<DetallePedido> MostrarDetallePedido(int IDPedido) {
        List<DetallePedido> Detallepedido = new ArrayList<>();
        String sql = "SELECT d.Prod_ID, prod.NombreProductoPro AS NombreProducto, d.Cantidad, d.PrecioUnitario, d.FechaHoraAgregado, d.Subtotal, "
                + "prov.NombreCompleto AS NombreProveedor, cat.NombreCategoria "
                + "FROM detallepedido d "
                + "INNER JOIN productosproveedor prod ON d.Prod_ID = prod.ProductoPro_ID "
                + "INNER JOIN proveedor prov ON prod.Proveedor_ID = prov.Proveedor_ID "
                + "INNER JOIN categoria cat ON prod.Categoria_ID = cat.Categoria_ID "
                + "WHERE d.Pedido_ID = ?";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, IDPedido);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int ProductoID = rs.getInt("Prod_ID");
                    String nombreProducto = rs.getString("NombreProducto");
                    int cantidad = rs.getInt("Cantidad");
                    double precio = rs.getDouble("PrecioUnitario");
                    String fechaHoraAgre = rs.getString("FechaHoraAgregado");
                    double subtotal = rs.getDouble("Subtotal");
                    String NombreProv = rs.getString("NombreProveedor");
                    String NombreCat = rs.getString("NombreCategoria");


                    DetallePedido d = new DetallePedido();
                    d.setPedidoID(ProductoID);
                    d.setNombreProducto(nombreProducto);
                    d.setNombreProveedor(NombreProv);
                    d.setNombreCategoria(NombreCat);
                    d.setCantidad(cantidad);
                    d.setPrecio(precio);
                    d.setSubtotal(subtotal);
                    d.setFechaHoraAgregado(fechaHoraAgre);

                    Detallepedido.add(d);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return Detallepedido;
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
    public int ObtenerIDCategoria(String nombreCategoria) {
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
    public List<Pedido> buscarPedido(String nombreBuscado) {
        List<Pedido> pedido = new ArrayList<>();
        java.sql.Connection con = getConexion();
        String sql = "SELECT p.Pedido_ID, c.NombreCompleto AS NombreUsuario, p.FechaRealizaPedido, p.FechaEntrega, p.Estado, p.Total "
                + "FROM pedidos p "
                + "INNER JOIN usuario c ON c.Usuario_ID = p.Usuario_ID "
                + "INNER JOIN detallepedido d ON d.Pedido_ID = p.Pedido_ID "
                + "INNER JOIN productosproveedor prod ON d.Prod_ID = prod.ProductoPro_ID "
                + "INNER JOIN proveedor prov ON prod.Proveedor_ID = prov.Proveedor_ID "
                + "WHERE p.Pedido_ID LIKE ? OR c.NombreCompleto LIKE ? OR prov.NombreCompleto LIKE ?;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombreBuscado + "%");
            ps.setString(2, "%" + nombreBuscado + "%");
            ps.setString(3, "%" + nombreBuscado + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int pedidoID = rs.getInt("Pedido_ID");
                String Usuario = rs.getString("NombreUsuario");
                String fechaRealizada = rs.getString("FechaRealizaPedido");
                String fechaEntrega = rs.getString("FechaEntrega");
                double total = rs.getDouble("Total");
                String estado = rs.getString("Estado");

                Pedido p = new Pedido();
                p.setPedidoID(pedidoID);
                p.setNombreUsuario(Usuario);
                p.setFechaRealizada(fechaRealizada);
                p.setFechaEntrega(fechaEntrega);
                p.setTotal(total);
                p.setEstado(estado);

                pedido.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public List<Pedido> filtrarPorProveedor(int Proveedor_ID) {   //
        List<Pedido> pedido = new ArrayList<>();
        java.sql.Connection con = getConexion();
        String sql = "SELECT p.Pedido_ID, c.NombreCompleto AS NombreUsuario, p.FechaRealizaPedido, p.FechaEntrega, p.Estado, p.Total "
                + "FROM pedidos p "
                + "INNER JOIN usuario c ON c.Usuario_ID = p.Usuario_ID "
                + "INNER JOIN detallepedido d ON d.Pedido_ID = p.Pedido_ID "
                + "INNER JOIN productosproveedor prod ON d.Prod_ID = prod.ProductoPro_ID "
                + "INNER JOIN proveedor prov ON prod.Proveedor_ID = prov.Proveedor_ID "
                + "WHERE prod.Proveedor_ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Proveedor_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int pedidoID = rs.getInt("Pedido_ID");
                String Usuario = rs.getString("NombreUsuario");
                String fechaRealizada = rs.getString("FechaRealizaPedido");
                String fechaEntrega = rs.getString("FechaEntrega");
                double total = rs.getDouble("Total");
                String estado = rs.getString("Estado");

                Pedido p = new Pedido();
                p.setPedidoID(pedidoID);
                p.setNombreUsuario(Usuario);
                p.setFechaRealizada(fechaRealizada);
                p.setFechaEntrega(fechaEntrega);
                p.setTotal(total);
                p.setEstado(estado);

                pedido.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public List<Pedido> filtrarPorCategoria(int Categoria_ID) {
        List<Pedido> pedido = new ArrayList<>();
        java.sql.Connection con = getConexion();
        String sql = "SELECT p.Pedido_ID, c.NombreCompleto AS NombreUsuario, p.FechaRealizaPedido, p.FechaEntrega, p.Estado, p.Total "
                + "FROM pedidos p "
                + "INNER JOIN usuario c ON c.Usuario_ID = p.Usuario_ID "
                + "INNER JOIN detallepedido d ON d.Pedido_ID = p.Pedido_ID "
                + "INNER JOIN productosproveedor prod ON d.Prod_ID = prod.ProductoPro_ID "
                + "INNER JOIN proveedor prov ON prod.Proveedor_ID = prov.Proveedor_ID "
                + "WHERE prod.Categoria_ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Categoria_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int pedidoID = rs.getInt("Pedido_ID");
                String Usuario = rs.getString("NombreUsuario");
                String fechaRealizada = rs.getString("FechaRealizaPedido");
                String fechaEntrega = rs.getString("FechaEntrega");
                double total = rs.getDouble("Total");
                String estado = rs.getString("Estado");

                Pedido p = new Pedido();
                p.setPedidoID(pedidoID);
                p.setNombreUsuario(Usuario);
                p.setFechaRealizada(fechaRealizada);
                p.setFechaEntrega(fechaEntrega);
                p.setTotal(total);
                p.setEstado(estado);

                pedido.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public List<Pedido> filtrarPorRangoDePrecio(double precioMin, double precioMax) {
        List<Pedido> pedido = new ArrayList<>();
        java.sql.Connection con = getConexion();
            String sql = "SELECT p.Pedido_ID, c.NombreCompleto AS NombreUsuario, p.FechaRealizaPedido, p.FechaEntrega, p.Estado, p.Total "
                    + "FROM pedidos p "
                    + "INNER JOIN usuario c ON c.Usuario_ID = p.Usuario_ID "
                    + "INNER JOIN detallepedido d ON d.Pedido_ID = p.Pedido_ID "
                    + "INNER JOIN productosproveedor prod ON d.Prod_ID = prod.ProductoPro_ID "
                    + "WHERE prod.Precio BETWEEN ? AND ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, precioMin);
            ps.setDouble(2, precioMax);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int pedidoID = rs.getInt("Pedido_ID");
                String Usuario = rs.getString("NombreUsuario");
                String fechaRealizada = rs.getString("FechaRealizaPedido");
                String fechaEntrega = rs.getString("FechaEntrega");
                double total = rs.getDouble("Total");
                String estado = rs.getString("Estado");

                Pedido p = new Pedido();
                p.setPedidoID(pedidoID);
                p.setNombreUsuario(Usuario);
                p.setFechaRealizada(fechaRealizada);
                p.setFechaEntrega(fechaEntrega);
                p.setTotal(total);
                p.setEstado(estado);

                pedido.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public boolean updatePedido(Pedido p) {
        PreparedStatement ps = null;
        java.sql.Connection con = getConexion();
        String sql = "UPDATE pedidos SET FechaEntrega = ?, Estado = ? WHERE Pedido_ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getFechaEntrega());
            ps.setString(2, p.getEstado());
            ps.setInt(3, p.getPedidoID());
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
}
