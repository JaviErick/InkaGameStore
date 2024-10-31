package pe.edu.utp.controller;

import pe.edu.utp.vista.BuscadordProductos;
import pe.edu.utp.vista.Carrito;
import pe.edu.utp.vista.MenuEmpleado;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.ClientesDao;
import pe.edu.utp.dao.ProductoDao;
import pe.edu.utp.dao.VentaDao;
import pe.edu.utp.daoImpl.ClientesDaoImpl;
import pe.edu.utp.daoImpl.VentaDaoImpl;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Productos;
import pe.edu.utp.entity.Carritoo;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;

public class BuscarProductoController implements Runnable {

    String hora, minuto, segundo;
    Thread hilo;
    private Productos modelo;
    private ProductoDao pdao;
    private BuscadordProductos vista;
    ClientesDao daoCliente = new ClientesDaoImpl();

    public static String fecha() {
        Date fecha = new Date();
        SimpleDateFormat formatofecha = new SimpleDateFormat("YYYY-MM-dd");
        return formatofecha.format(fecha);

    }

    public void Hora() {
        Calendar calendario = new GregorianCalendar();
        Date horaactual = new Date();
        calendario.setTime(horaactual);
        hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        while (current == hilo) { // Verifica ambos hilos
            Hora();
            if (current == hilo && vista != null) {
                vista.lblHora.setText(hora + ":" + minuto + ":" + segundo);
            }
        }
    }

    public BuscarProductoController(Productos modelo, ProductoDao pdao, BuscadordProductos vista) {
        this.modelo = modelo;
        this.pdao = pdao;
        this.vista = vista;
        initialize();
    }

    public void iniciar() {
        vista.setTitle("Busqueda Libros");
        vista.setLocationRelativeTo(null);
        vista.lblFecha.setText(fecha());
        hilo = new Thread(this);
        hilo.start();
    }

    private void initialize() {
        this.vista.lblBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblVerCarrito.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblBuscar) {
                    System.out.println("label Buscar presionado");  // Depuración
                    buscar();
                }
            }
        });
        this.vista.lblAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblAgregar) {
                    System.out.println("label Agregar presionado");  // Depuración
                    agregarCarrito();
                }
            }
        });
        this.vista.lblVerCarrito.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblVerCarrito) {
                    System.out.println("label Ver Carrito presionado");  // Depuración
                    verCarrito();
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
        vista.txtBusqueda.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtBusqueda, "Buscar producto...");
            }
        });
    }

    //metodos 
    private void regresar() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuEmpleado a = new MenuEmpleado(usuarioLogeado);
        a.show();
        vista.dispose();
    }

    private void buscar() {
        String nombreProductoBuscado = vista.txtBusqueda.getText().trim();

        if (nombreProductoBuscado.equals("Buscar producto...")) {
            JOptionPane.showMessageDialog(null, "Ingrese el producto a buscar");
        } else {
            List<Productos> productosEncontrados = pdao.readProductos(nombreProductoBuscado);
            limpiarEtiquetas();
            if (productosEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontro el producto");
            } else {
                Productos producto = productosEncontrados.get(0);
                // Asigna los valores a los campos de la vista
                vista.lblID.setText(String.valueOf(producto.getProducto_ID()));
                vista.lblNombre.setText(producto.getNombreProducto());
                vista.lblCategoria.setText(producto.getCategoria());
                vista.lblPrecio.setText(String.valueOf(producto.getPrecio()));
                vista.lblStock.setText(String.valueOf(producto.getStock()));
                vista.txtDescripcion.setText(producto.getDescripcion());
                try {
                    Image foto = Toolkit.getDefaultToolkit().getImage(producto.getImagenRuta());
                    foto = foto.getScaledInstance(214, 157, Image.SCALE_DEFAULT);
                    vista.lblImagen.setIcon(new ImageIcon(foto));
                } catch (Exception e) {
                    System.out.println("Error al cargar la imagen: " + e.getMessage());
                }
            }
        }

    }

    public class CarritoManager {

        public static List<Carritoo> carrito = new ArrayList<>();
    }

    private void agregarCarrito() {
        try {
            int idProducto = Integer.parseInt(vista.lblID.getText());
            String nombreProducto = vista.lblNombre.getText();
            double precio = Double.parseDouble(vista.lblPrecio.getText());
            int cantidad = Integer.parseInt(vista.tcCantidad.getValue().toString());
            String fecha = vista.lblFecha.getText();
            String hora = vista.lblHora.getText();
            double subtotal = precio * cantidad;
            boolean productoExistente = false;
            for (Carritoo item : CarritoManager.carrito) {
                if (item.getIdProducto() == idProducto) {
                    item.setCantidad(item.getCantidad() + cantidad);
                    item.setSubtotal(item.getSubtotal() + subtotal);
                    productoExistente = true;
                    break;
                }
            }
            if (!productoExistente) {
                Carritoo item = new Carritoo(idProducto, nombreProducto, precio, cantidad, fecha, hora, subtotal);
                CarritoManager.carrito.add(item);
            }
            JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");
            limpiarEtiquetas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar al carrito: " + e.getMessage());
        }
    }

    public void verCarrito() {
        Carritoo modeloCarrito = new Carritoo();
        VentaDao daoCarrito = new VentaDaoImpl();
        Venta venta = new Venta();
        DetalleVenta detalle = new DetalleVenta();
        Carrito vistaCarrito = new Carrito(SesionUsuario.getUsuarioLogeado());
        CarritoController carritoController = new CarritoController(CarritoManager.carrito, venta, detalle, modeloCarrito, daoCliente, daoCarrito, vistaCarrito);
        carritoController.iniciar();
        DefaultTableModel dtmodel = (DefaultTableModel) vistaCarrito.tblCarrito.getModel();
        dtmodel.setRowCount(0);

        for (Carritoo item : CarritoManager.carrito) {
            Object[] fila = {
                item.getIdProducto(),
                item.getNombreProducto(),
                item.getPrecio(),
                item.getCantidad(),
                item.getFecha(),
                item.getHora(),
                item.getSubtotal(),};
            dtmodel.addRow(fila);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < vistaCarrito.tblCarrito.getColumnCount(); i++) {
            vistaCarrito.tblCarrito.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vistaCarrito.tblCarrito.getColumnCount(); i++) {
            vistaCarrito.tblCarrito.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        carritoController.calcularTotal();
        vistaCarrito.setVisible(true);
        vista.dispose();

    }

    private void limpiarEtiquetas() {
        System.out.println("Limpiando etiquetas");
        vista.lblID.setText("");
        vista.lblNombre.setText("");
        vista.lblCategoria.setText("");
        vista.lblPrecio.setText("");
        vista.lblStock.setText("");
        vista.txtDescripcion.setText("");
        vista.lblImagen.setIcon(null);
        vista.tcCantidad.setValue(1);
        vista.txtBusqueda.setText("Buscar producto...");
        vista.txtBusqueda.setForeground(new Color(153, 153, 153));
    }

    private JTextField campoActual = null;

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

    private String getPlaceholderText(JTextField textField) {
        if (textField.equals(vista.txtBusqueda)) {
            return "Buscar producto...";
        }
        return "";
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtBusqueda)) {
            textField.setText("Buscar producto...");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

    //////////////////////////////////////////////////////////////
}
