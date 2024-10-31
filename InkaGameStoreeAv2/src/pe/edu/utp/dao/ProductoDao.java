/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.utp.dao;

import java.util.List;
import pe.edu.utp.entity.Productos;

/**
 *
 * @author AMD
 */
public interface ProductoDao {
    public boolean createProducto(Productos p);
    public boolean updateProducto(Productos p);
    public boolean deleteProducto(int p);
    public int ObtenerIDCategoria(String nombreCategoria);
    public int ObtenerIDProveedor(String nombreProveedor);
    public List<Productos> readProductos(String nombreProductoBuscado);
    public List<String> getCategorias();
    public List<String> getProveedores();
    public List<Productos> readAllProductos(); 
}
