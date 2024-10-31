package pe.edu.utp.entity;

import javax.swing.Icon;

public class Productos {
    
    private int Producto_ID;
    private String Categoria;
    private String NombreProducto;
    private String Proveedor;
    private double Precio;
    private int Stock;
    private String Descripcion;
    private String FechaHoraAgregada;
    private Icon Imagen;
    private String ImagenRuta;

    public Productos() {
    }

    public Productos(int Producto_ID, String Categoria, String NombreProducto, String Proveedor, double Precio, int Stock, String Descripcion, String FechaHoraAgregada, Icon Imagen, String ImagenRuta) {
        this.Producto_ID = Producto_ID;
        this.Categoria = Categoria;
        this.NombreProducto = NombreProducto;
        this.Proveedor = Proveedor;
        this.Precio = Precio;
        this.Stock = Stock;
        this.Descripcion = Descripcion;
        this.FechaHoraAgregada = FechaHoraAgregada;
        this.Imagen = Imagen;
        this.ImagenRuta = ImagenRuta;
    }

    public int getProducto_ID() {
        return Producto_ID;
    }

    public void setProducto_ID(int Producto_ID) {
        this.Producto_ID = Producto_ID;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String NombreProducto) {
        this.NombreProducto = NombreProducto;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getFechaHoraAgregada() {
        return FechaHoraAgregada;
    }

    public void setFechaHoraAgregada(String FechaHoraAgregada) {
        this.FechaHoraAgregada = FechaHoraAgregada;
    }

    public Icon getImagen() {
        return Imagen;
    }

    public void setImagen(Icon Imagen) {
        this.Imagen = Imagen;
    }

    public String getImagenRuta() {
        return ImagenRuta;
    }

    public void setImagenRuta(String ImagenRuta) {
        this.ImagenRuta = ImagenRuta;
    }

    @Override
    public String toString() {
        return "Productos{" + "Producto_ID=" + Producto_ID + ", Categoria=" + Categoria + ", NombreProducto=" + NombreProducto + ", Proveedor=" + Proveedor + ", Precio=" + Precio + ", Stock=" + Stock + ", Descripcion=" + Descripcion + ", FechaHoraAgregada=" + FechaHoraAgregada + ", Imagen=" + Imagen + ", ImagenRuta=" + ImagenRuta + '}';
    }
    
    
    
}
