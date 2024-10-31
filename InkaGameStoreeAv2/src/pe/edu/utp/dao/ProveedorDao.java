package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.Proveedor;



/**
 *
 * @author javie
 */
public interface ProveedorDao {
    
    public boolean createProveedor(Proveedor p);
    public boolean updateProveedor(Proveedor p);
    public boolean deleteProveedor(int p);
    public boolean readProveedor(Proveedor p);
    public List<Proveedor> readAllProveedor();
}
