/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import pe.edu.utp.dao.ClientesDao;
import pe.edu.utp.dao.ProductoDao;
import pe.edu.utp.dao.VentaDao;
import pe.edu.utp.daoImpl.ClientesDaoImpl;
import pe.edu.utp.daoImpl.ProductoDaoImpl;
import pe.edu.utp.daoImpl.VentaDaoImpl;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Carritoo;
import pe.edu.utp.entity.Clientes;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Productos;
import pe.edu.utp.entity.Venta;
import pe.edu.utp.vista.BuscadordProductos;
import pe.edu.utp.vista.Carrito;
import pe.edu.utp.vista.RegistrodClientes;

/**
 *
 * @author Jessica Parra
 */
public class RegistroClienteController {

    private Clientes modelo;
    private ClientesDao pdao;
    private RegistrodClientes vista;
    private JTextField campoActual = null;

    public RegistroClienteController(Clientes modelo, ClientesDao pdao, RegistrodClientes vista) {
        this.modelo = modelo;
        this.pdao = pdao;
        this.vista = vista;
        initialize();  // Vincula los eventos a los botones
    }

    public void iniciar() {
        vista.setTitle("Registro de Clientes");
        vista.setLocationRelativeTo(null);
    }

    private void guardarCliente() {
        try {
            System.out.println("Guardando cliente...");  // Depuración
            modelo.setDNI(vista.txtdni.getText());
            modelo.setNombreCompleto(vista.txtnombre.getText());
            modelo.setTelefono(vista.txttelefono.getText());
            modelo.setDireccion(vista.txtdireccion.getText());

            if (pdao.createClientes(modelo)) {
                JOptionPane.showMessageDialog(null, "Cliente Guardado");
                regresar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
            }

        } catch (NumberFormatException e) {
            System.out.println("a");
        }
    }

    private void regresar() {
        Productos modeloProducto = new Productos();
        ProductoDao productoDao = new ProductoDaoImpl(); // Asegúrate de que esto esté correctamente implementado
        BuscadordProductos vistaProductos = new BuscadordProductos(SesionUsuario.getUsuarioLogeado());

        BuscarProductoController ventaController = new BuscarProductoController(modeloProducto, productoDao, vistaProductos);
        ventaController.verCarrito();

        vista.dispose();
    }

    private void initialize() {
        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblRegistrar) {
                    System.out.println("label Registrar presionado");  // Depuración
                    guardarCliente();
                }
            }
        });
        this.vista.lblRegresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblRegresar) {
                    System.out.println("label Regresar presionado");  // Depuración
                    regresar();
                }
            }
        });

        // Agregar MouseListener a los campos de texto
        vista.txtdni.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtdni, "Ingrese su DNI...");
            }
        });

        vista.txtnombre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtnombre, "Ingrese sus Nombres y Apellidos...");
            }
        });

        vista.txttelefono.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txttelefono, "Ingrese su Telefono...");
            }
        });

        vista.txtdireccion.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtdireccion, "Ingrese su Dirección...");
            }
        });

    }

    private void txtMousePressed(JTextField textField, String placeholder) {
        // Si hay un campo actualmente seleccionado y no es el mismo, restablecer su placeholder
        if (campoActual != null && campoActual != textField) {
            if (campoActual.getText().isEmpty() || campoActual.getText().equals(getPlaceholderText(campoActual))) {
                resetPlaceholder(campoActual);
            }
        }
        campoActual = textField;
        if (textField.getText().equals(placeholder) && textField.getForeground().equals(new Color(153, 153, 153))) {
            textField.setText("");
            textField.setForeground(Color.black);
        }
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtdni)) {
            textField.setText("Ingrese su DNI...");
        } else if (textField.equals(vista.txtnombre)) {
            textField.setText("Ingrese sus Nombres y Apellidos...");
        } else if (textField.equals(vista.txttelefono)) {
            textField.setText("Ingrese su Telefono...");
        } else if (textField.equals(vista.txtdireccion)) {
            textField.setText("Ingrese su Dirección...");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

    private String getPlaceholderText(JTextField textField) {
        if (textField.equals(vista.txtdni)) {
            return "Ingrese su DNI...";
        } else if (textField.equals(vista.txtnombre)) {
            return "Ingrese sus Nombres y Apellidos...";
        } else if (textField.equals(vista.txttelefono)) {
            return "Ingrese su Telefono...";
        } else if (textField.equals(vista.txtdireccion)) {
            return "Ingrese su Dirección...";
        }
        return "";
    }

}
