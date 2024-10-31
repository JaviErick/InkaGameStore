
package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.Asistencia;
import pe.edu.utp.entity.Usuario;

public interface UsuarioDao {
    public boolean createUsuario(Usuario u);
    public boolean updateUsuario(Usuario u);
    public boolean deleteUsuario(int u);
    public boolean ComprobarUsuario(Usuario u);
    public List<Usuario> readAllUsuario(); 
    public String registroEntrada(Asistencia a);
    boolean registroSalida(int asistenciaID, String horaSalida);
}
