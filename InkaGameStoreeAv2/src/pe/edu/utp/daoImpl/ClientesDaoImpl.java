package pe.edu.utp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.config.Conexion;
import pe.edu.utp.dao.ClientesDao;
import pe.edu.utp.entity.Clientes;

/**
 *
 * @author javie
 */
public class ClientesDaoImpl extends Conexion implements ClientesDao {

    @Override
    public boolean createClientes(Clientes c) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO cliente (NombreCompleto, Telefono, Direccion, DNI) VALUES (?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombreCompleto());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getDNI());
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
    public boolean updateClientes(Clientes c) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE cliente SET NombreCompleto = ?, Telefono = ?, Direccion = ? , DNI = ? WHERE Cliente_ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombreCompleto());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getDNI());
            ps.setInt(5, c.getCliente_ID());
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
    public boolean deleteClientes(int id) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERE Cliente_ID = ?"; // Asegúrate de usar el nombre correcto de la columna

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
    public List<Clientes> readAllClientes() {
        List<Clientes> listaClientes = new ArrayList<>();
        String sql = "SELECT Cliente_ID, DNI, NombreCompleto, Telefono, Direccion FROM cliente ORDER BY Cliente_ID ASC"; // Puedes cambiar ASC a DESC si quieres orden descendente

        try (Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int clienteID = rs.getInt("Cliente_ID");
                String dni = rs.getString("DNI");
                String nombreCompleto = rs.getString("NombreCompleto");
                String telefono = rs.getString("Telefono");
                String direccion = rs.getString("Direccion");

                Clientes cliente = new Clientes(clienteID, dni, nombreCompleto, telefono, direccion);
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaClientes;
    }

    @Override
    public List<Clientes> readClientes(String dniBuscado) {
        List<Clientes> listaCliente = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE DNI =(?)";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Ajustar el parámetro de búsqueda para el LIKE
            ps.setString(1, dniBuscado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int ClienteID = rs.getInt("Cliente_ID");
                    String NombreCompleto = rs.getString("NombreCompleto");
                    String DNI = rs.getString("DNI");
                    String Telefono = rs.getString("Telefono");
                    String Direccion = rs.getString("Direccion");

                    Clientes cliente = new Clientes(ClienteID, NombreCompleto, Telefono, Direccion, DNI);
                    listaCliente.add(cliente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }
        return listaCliente;
    }

}
