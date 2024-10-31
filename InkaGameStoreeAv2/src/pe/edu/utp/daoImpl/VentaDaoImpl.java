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
    public boolean createVenta(Venta v, List<DetalleVenta> detalleVenta) {

        PreparedStatement psVenta = null;
        PreparedStatement psDetalleVenta = null;
        ResultSet rs = null;
        java.sql.Connection con = getConexion();

        String sqlVenta = "INSERT INTO Ventas (Cliente_ID, Usuario_ID, Total, FechaHoraVenta) VALUES (?, ?, ?, ?)";
        String sqlDetalleVenta = "INSERT INTO DetalleVenta (Venta_ID, Producto_ID, Cantidad, Precio, FechaHoraAgreCarrito, Subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            
            psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, v.getClienteID());
            psVenta.setInt(2, v.getUsuarioID());
            psVenta.setDouble(3, v.getTotal());
            psVenta.setString(4, v.getFecha()+" "+ v.getHora());
            
            int rowsAffected = psVenta.executeUpdate();
            if (rowsAffected > 0) {
                
                rs = psVenta.getGeneratedKeys();
                if (rs.next()) {
                    int ventaID = rs.getInt(1);
                    psDetalleVenta = con.prepareStatement(sqlDetalleVenta);

                    for (DetalleVenta detalle : detalleVenta) {
                        psDetalleVenta.setInt(1, ventaID); // Venta_ID recuperado
                        psDetalleVenta.setInt(2, detalle.getProductoID());
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

    @Override
    public List<Venta> readAllVenta() {
        List<Venta> listaVenta = new ArrayList<>();
        String sql = "SELECT FROM ";
        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaVenta;
    }
}
