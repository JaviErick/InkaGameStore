package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;

public interface VentaDao {
    
    public boolean createVenta(Venta v, List<DetalleVenta> detalleVenta);
    public List<Venta> readAllVenta();
    public List<DetalleVenta> MostrarDetalleVenta( int IDVenta);
    public List<Venta> buscardorVentas(String criterio);
    public List<Venta> buscardorVentasPorUsuarioID(int usuarioID);
    public List<DetalleVenta> MostrarDetalle();

}
