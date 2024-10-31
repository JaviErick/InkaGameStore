package pe.edu.utp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.edu.utp.config.Conexion;
import pe.edu.utp.entity.Usuario;
import pe.edu.utp.dao.UsuarioDao;
import pe.edu.utp.entity.Asistencia;

/**
 *
 * @author javie
 */
public class UsuarioDaoImpl extends Conexion implements UsuarioDao {

    @Override
    public boolean createUsuario(Usuario u) {
        PreparedStatement ps = null;
        java.sql.Connection con = getConexion();

        String sql = "INSERT INTO usuario (Rol_ID, NombreCompleto, DNI, Telefono, Correo, Contraseña) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            int rolID = Integer.parseInt(u.getRol());
            ps.setInt(1, rolID);
            ps.setString(2, u.getNombreCompleto());
            ps.setString(3, u.getDNI());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getContraseña());
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
    public boolean updateUsuario(Usuario u) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE usuario SET Rol_ID = ?, NombreCompleto = ?, DNI = ?, Telefono = ?, Correo = ?, Contraseña = ? WHERE Usuario_ID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(u.getRol()));
            ps.setString(2, u.getNombreCompleto());
            ps.setString(3, u.getDNI());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getContraseña());
            ps.setInt(7, u.getUsuario_ID());
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
    public boolean deleteUsuario(int E) {
        PreparedStatement ps = null;
        java.sql.Connection con = getConexion();
        String sql = "DELETE FROM usuario WHERE Usuario_ID = ?"; // Asegúrate de usar el nombre correcto de la columna

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, E);
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
    public boolean ComprobarUsuario(Usuario E) {
        return false;
    }

    @Override
    public List<Usuario> readAllUsuario() {
        List<Usuario> listaEmpleado = new ArrayList<>();
        String sql = "SELECT u.Usuario_ID, u.NombreCompleto, u.DNI, u.Telefono, u.Correo, u.Contraseña, r.NombreRol "
                + "FROM usuario u "
                + "INNER JOIN rol r ON u.Rol_ID = r.Rol_ID;";

        try (java.sql.Connection con = getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int usuarioID = rs.getInt("Usuario_ID");
                String rol = rs.getString("NombreRol");
                String nombreCompleto = rs.getString("NombreCompleto");
                String dni = rs.getString("DNI");
                String telefono = rs.getString("Telefono");
                String correo = rs.getString("Correo");
                String contraseña = rs.getString("Contraseña");

                Usuario usuario = new Usuario(usuarioID, rol, nombreCompleto, dni, telefono, correo, contraseña);
                listaEmpleado.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }

        return listaEmpleado;
    }

    @Override
    public String registroEntrada(Asistencia a) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection con = getConexion();

        String sqlEntrada = "INSERT INTO registroasistencia (Usuario_ID, Fecha, Entrada_Hora, Salida_Hora) VALUES (?, ?, ?, ?)";

        try {
            // Insertar registro de entrada
            ps = con.prepareStatement(sqlEntrada, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, a.getUsuarioID());
            ps.setString(2, a.getFecha());
            ps.setString(3, a.getHoraIngreso());
            ps.setString(4, null); // Salida_Hora se deja como null al inicio
            ps.executeUpdate(); // Cambiado a executeUpdate para la inserción

            // Obtener el ID de la asistencia generada
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int asistenciaID = rs.getInt(1);
                
                return String.valueOf(asistenciaID);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            // Cierra los recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return null;
    }

        @Override
        public boolean registroSalida(int asistenciaID, String horaSalida) {
            PreparedStatement psSalida = null;
            java.sql.Connection con = getConexion();

            String sqlSalida = "UPDATE registroasistencia SET Salida_Hora = (?) WHERE Asistencia_ID = (?)";

            try {
                psSalida = con.prepareStatement(sqlSalida);
                psSalida.setString(1, horaSalida); 
                psSalida.setInt(2, asistenciaID); 

                int rowsAffected = psSalida.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println(e);
                return false;
            } finally {
                try {
                    if (psSalida != null) {
                        psSalida.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        }

}
