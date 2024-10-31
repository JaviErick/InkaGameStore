package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;

public interface VentaDao {
    
    public boolean createVenta(Venta v, List<DetalleVenta> detalleVenta);
    public List<Venta> readAllVenta();

}
