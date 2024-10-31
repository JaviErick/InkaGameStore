package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.Clientes;



/**
 *
 * @author javie
 */
public interface ClientesDao {
    
    public boolean createClientes(Clientes c);
    public boolean updateClientes(Clientes c);
    public boolean deleteClientes(int c);
    public List<Clientes> readAllClientes();
    public List<Clientes> readClientes(String dniBuscado);
}
