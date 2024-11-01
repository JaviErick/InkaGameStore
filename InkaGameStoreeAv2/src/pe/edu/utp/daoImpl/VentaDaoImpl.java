package pe.edu.utp.daoImpl;

import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.config.Conexion;
import pe.edu.utp.dao.VentaDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;

public class VentaDaoImpl extends Conexion implements VentaDao {

    @Override
    public List<Venta> readAllVenta() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.Ventas_ID, v.Total, v.FechaHoraVenta, c.NombreCompleto AS clienteNombre, u.NombreCompleto AS usuarioNombre "
                + "FROM ventas v "
                + "INNER JOIN cliente c ON c.Cliente_ID = v.Cliente_ID "
                + "INNER JOIN usuario u ON u.Usuario_ID = v.Usuario_ID;";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int ventaID = rs.getInt("Ventas_ID");
                String clienteID = rs.getString("ClienteNombre");
                String usuarioID = rs.getString("UsuarioNombre");
                double total = rs.getDouble("Total");
                String fechaHoraVenta = rs.getString("FechaHoraVenta");

                // Separar la fecha y la hora
                String fecha = fechaHoraVenta.substring(0, 10);  // YYYY-MM-DD  
                String hora = fechaHoraVenta.substring(11);      // HH:MM:SS
                

                Venta venta = new Venta();
                venta.setVentaID(ventaID);
                venta.setClienteID(String.valueOf(clienteID));
                venta.setUsuarioID(String.valueOf(usuarioID));
                venta.setTotal(total);
                venta.setFecha(fecha);
                venta.setHora(hora);

                ventas.add(venta);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return ventas;
    }

    @Override
    public List<Venta> buscardorVentas(String criterio) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.Ventas_ID, v.Total, v.FechaHoraVenta, c.NombreCompleto AS clienteNombre, u.NombreCompleto AS usuarioNombre "
                + "FROM ventas v "
                + "INNER JOIN cliente c ON c.Cliente_ID = v.Cliente_ID "
                + "INNER JOIN usuario u ON u.Usuario_ID = v.Usuario_ID "
                + "WHERE v.Ventas_ID LIKE ? OR c.NombreCompleto LIKE ? OR u.NombreCompleto LIKE ?;";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String criterioBusqueda = "%" + criterio + "%";
            ps.setString(1, criterioBusqueda);
            ps.setString(2, criterioBusqueda);
            ps.setString(3, criterioBusqueda);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int ventaID = rs.getInt("Ventas_ID");
                    String clienteNombre = rs.getString("clienteNombre");
                    String usuarioNombre = rs.getString("usuarioNombre");
                    double total = rs.getDouble("Total");
                    String fechaHoraVenta = rs.getString("FechaHoraVenta");

                    // Separar la fecha y la hora
                    String fecha = fechaHoraVenta.substring(0, 10);  // YYYY-MM-DD
                    String hora = fechaHoraVenta.substring(11);      // HH:MM:SS

                    Venta venta = new Venta();
                    venta.setVentaID(ventaID);
                    venta.setClienteID(clienteNombre);
                    venta.setUsuarioID(usuarioNombre);
                    venta.setTotal(total);
                    venta.setFecha(fecha);
                    venta.setHora(hora);

                    ventas.add(venta);
                }

            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return ventas;
    }

    @Override
    public List<Venta> buscardorVentasPorUsuarioID(int usuarioID) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.Ventas_ID, v.Total, v.FechaHoraVenta, c.NombreCompleto AS clienteNombre, u.NombreCompleto AS usuarioNombre "
                + "FROM ventas v "
                + "INNER JOIN cliente c ON c.Cliente_ID = v.Cliente_ID "
                + "INNER JOIN usuario u ON u.Usuario_ID = v.Usuario_ID "
                + "WHERE u.Usuario_ID = ?;";  // Filtrar solo por el ID de usuario

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioID);  // Asignar el ID de usuario al parámetro de la consulta

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int ventaID = rs.getInt("Ventas_ID");
                    String clienteNombre = rs.getString("clienteNombre");
                    String usuarioNombre = rs.getString("usuarioNombre");
                    double total = rs.getDouble("Total");
                    String fechaHoraVenta = rs.getString("FechaHoraVenta");

                    // Separar la fecha y la hora
                    String fecha = fechaHoraVenta.substring(0, 10);  // YYYY-MM-DD
                    String hora = fechaHoraVenta.substring(11);      // HH:MM:SS

                    Venta venta = new Venta();
                    venta.setVentaID(ventaID);
                    venta.setClienteID(clienteNombre);
                    venta.setUsuarioID(usuarioNombre);
                    venta.setTotal(total);
                    venta.setFecha(fecha);
                    venta.setHora(hora);

                    ventas.add(venta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return ventas;
    }

    @Override
    public List<DetalleVenta> MostrarDetalleVenta(int IDVenta) {
        List<DetalleVenta> Detalleventas = new ArrayList<>();
        String sql = "SELECT p.NombreProducto, d.Cantidad, d.Precio, d.FechaHoraAgreCarrito, d.Subtotal "
                + "FROM detalleventa d "
                + "INNER JOIN producto p ON d.Producto_ID = p.Producto_ID " // JOIN antes del WHERE
                + "WHERE d.Venta_ID = ?";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, IDVenta);  // Asignar el valor de IDVenta

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombreProducto = rs.getString("NombreProducto");
                    int cantidad = rs.getInt("Cantidad");
                    double precio = rs.getDouble("Precio");
                    String fechaHoraAgreCarrito = rs.getString("FechaHoraAgreCarrito");
                    double subtotal = rs.getDouble("Subtotal");

                    // Crear una instancia de `DetalleVenta` y asignar valores
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setProductoID(nombreProducto);
                    detalleVenta.setCantidad(cantidad);
                    detalleVenta.setPrecio(precio);
                    detalleVenta.setSubtotal(subtotal);
                    detalleVenta.setFechHoraAgreCarrito(fechaHoraAgreCarrito);

                    // Añadir a la lista
                    Detalleventas.add(detalleVenta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return Detalleventas;
    }
    
    @Override
    public List<DetalleVenta> MostrarDetalle() {
        List<DetalleVenta> Detalleventas = new ArrayList<>();
        String sql = "SELECT d.Venta_ID, p.NombreProducto, d.Cantidad, d.Precio, d.FechaHoraAgreCarrito, d.Subtotal "
                + "FROM detalleventa d "
                + "INNER JOIN producto p ON d.Producto_ID = p.Producto_ID ";
               

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int ID = rs.getInt("Venta_ID");
                    String nombreProducto = rs.getString("NombreProducto");
                    int cantidad = rs.getInt("Cantidad");
                    double precio = rs.getDouble("Precio");
                    String fechaHoraAgreCarrito = rs.getString("FechaHoraAgreCarrito");
                    double subtotal = rs.getDouble("Subtotal");

                    // Crear una instancia de `DetalleVenta` y asignar valores
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setVentaID(ID);
                    detalleVenta.setProductoID(nombreProducto);
                    detalleVenta.setCantidad(cantidad);
                    detalleVenta.setPrecio(precio);
                    detalleVenta.setSubtotal(subtotal);
                    detalleVenta.setFechHoraAgreCarrito(fechaHoraAgreCarrito);
                    

                    // Añadir a la lista
                    Detalleventas.add(detalleVenta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return Detalleventas;
    }
    

    @Override
    public boolean createVenta(Venta v, List<DetalleVenta> detalleVenta) {

        PreparedStatement psVenta = null;
        PreparedStatement psDetalleVenta = null;
        ResultSet rs = null;
        java.sql.Connection con = getConexion();

        String sqlVenta = "INSERT INTO Ventas (Cliente_ID, Usuario_ID, Total, FechaHoraVenta) VALUES (?, ?, ?, ?)";
        String sqlDetalleVenta = "INSERT INTO DetalleVenta (Venta_ID, Producto_ID, Cantidad, Precio, FechaHoraAgreCarrito, Subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, Integer.parseInt(v.getClienteID()));
            psVenta.setInt(2, Integer.parseInt(v.getUsuarioID()));
            psVenta.setDouble(3, v.getTotal());
            psVenta.setString(4, v.getFecha() + " " + v.getHora());

            int rowsAffected = psVenta.executeUpdate();
            if (rowsAffected > 0) {

                rs = psVenta.getGeneratedKeys();
                if (rs.next()) {
                    int ventaID = rs.getInt(1);
                    psDetalleVenta = con.prepareStatement(sqlDetalleVenta);

                    for (DetalleVenta detalle : detalleVenta) {
                        psDetalleVenta.setInt(1, ventaID); // Venta_ID recuperado
                        psDetalleVenta.setString(2, detalle.getProductoID());
                        psDetalleVenta.setInt(3, detalle.getCantidad());
                        psDetalleVenta.setDouble(4, detalle.getPrecio());
                        psDetalleVenta.setString(5, detalle.getFechHoraAgreCarrito());
                        psDetalleVenta.setDouble(6, detalle.getSubtotal());

                        psDetalleVenta.addBatch();
                    }
                    psDetalleVenta.executeBatch();
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
                if (psVenta != null) {
                    psVenta.close();
                }
                if (psDetalleVenta != null) {
                    psDetalleVenta.close();
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
