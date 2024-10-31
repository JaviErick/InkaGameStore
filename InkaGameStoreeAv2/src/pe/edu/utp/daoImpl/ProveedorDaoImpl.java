package pe.edu.utp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.config.Conexion;
import pe.edu.utp.dao.ProveedorDao;
import pe.edu.utp.entity.Proveedor;

/**
 *
 * @author javie
 */
public class ProveedorDaoImpl extends Conexion implements ProveedorDao {

    @Override
    public boolean createProveedor(Proveedor p) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO proveedor (NombreCompleto, RUC, Telefono, Correo, Direccion) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombreCompleto());
            ps.setString(2, p.getRuc());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getDireccion());
            ps.execute();
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
    public boolean updateProveedor(Proveedor p) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE proveedor SET RUC = ?, NombreCompleto = ?, Direccion = ?, Telefono = ?, Correo = ? WHERE Proveedor_ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getRuc());
            ps.setString(2, p.getNombreCompleto());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            ps.setInt(6,p.getProveedorID());
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
    public boolean deleteProveedor(int id) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "DELETE FROM proveedor WHERE Proveedor_ID = ?"; // Asegúrate de usar el nombre correcto de la columna

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
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
    public boolean readProveedor(Proveedor p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Proveedor> readAllProveedor() {
        List<Proveedor> listaProveedor = new ArrayList<>();
        String sql = "SELECT * FROM proveedor"; // Asegúrate de que la tabla se llama "Proveedor"

        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int proveedorID = rs.getInt("Proveedor_ID"); // Ajusta el nombre de la columna según tu base de datos
                String ruc = rs.getString("RUC");
                String nombreCompleto = rs.getString("NombreCompleto");
                String telefono = rs.getString("Telefono");
                String direccion = rs.getString("Direccion");
                String correo = rs.getString("Correo");

                Proveedor proveedor = new Proveedor(proveedorID, nombreCompleto, ruc, telefono, direccion, correo);
                listaProveedor.add(proveedor);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaProveedor;
    }

}
