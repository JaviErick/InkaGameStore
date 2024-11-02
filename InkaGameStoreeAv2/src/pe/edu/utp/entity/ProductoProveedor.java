/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.entity;

public class ProductoProveedor {

    private int ProductoPro_ID;
    private String Proveedor;
    private String Categoria;
    private String Descripcion;
    private double Precio;
    private String NombreProductoPro;

    public ProductoProveedor() {
    }

    public ProductoProveedor(int ProductoPro_ID, String Proveedor, String Categoria, String Descripcion, double Precio, String NombreProductoPro) {
        this.ProductoPro_ID = ProductoPro_ID;
        this.Proveedor = Proveedor;
        this.Categoria = Categoria;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
        this.NombreProductoPro = NombreProductoPro;
    }

    public int getProductoPro_ID() {
        return ProductoPro_ID;
    }

    public void setProductoPro_ID(int ProductoPro_ID) {
        this.ProductoPro_ID = ProductoPro_ID;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public String getNombreProductoPro() {
        return NombreProductoPro;
    }

    public void setNombreProductoPro(String NombreProductoPro) {
        this.NombreProductoPro = NombreProductoPro;
    }


   

    // Override del método toString
    @Override
    public String toString() {
        return "ProductoProveedor{"+ "productoPro_ID=" + ProductoPro_ID + ", proveedor='" + Proveedor + '\'' + ", categoria='" + Categoria + '\'' + ", descripcion='" + Descripcion + '\'' + ", precio=" + Precio + ", nombreProductoPro='" + NombreProductoPro + '\'' + '}';
    }
}
