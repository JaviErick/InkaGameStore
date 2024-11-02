package pe.edu.utp.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.PedidoDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.DetallePedido;
import pe.edu.utp.entity.Pedido;
import pe.edu.utp.entity.ProductoProveedor;
import pe.edu.utp.vista.ListaProductosProveedores;
import pe.edu.utp.vista.MenuAdmin;
import pe.edu.utp.vista.Pedidos;

public class CrearPedidoController implements Runnable {

    private Pedido modelo;
    private DetallePedido modelo2;
    private PedidoDao dao;
    private Pedidos vista;
    public DefaultTableModel dtmodel2 = new DefaultTableModel();
    private List<Object[]> lista;
    private JTextField campoActual = null;
    private String proveedorPedido = null;
    String hora, minuto, segundo;
    Thread hilo;

    public CrearPedidoController(List<Object[]> listaPedidos, Pedido modelo, DetallePedido modelo2, PedidoDao dao, Pedidos vista ) {
        this.lista = listaPedidos;
        this.modelo = modelo;
        this.modelo2 = modelo2;
        this.dao = dao;
        this.vista = vista;
        initialize();
    }

    public void Iniciar() {
        vista.setTitle("Pedidos");
        vista.setLocationRelativeTo(null);
        vista.lblFecha.setText(fecha());
        hilo = new Thread(this);
        hilo.start();
        llenarComboBoxProveedores();
        renderizar();
    }

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

    //METODOS
    private void CrearPedido() {
        try {
            System.out.println("Guardando Pedido...");  // Depuración
            modelo.setNombreUsuario(String.valueOf(SesionUsuario.getIdLogeado()));
            modelo.setFechaRealizada(vista.lblFecha.getText());
            modelo.setFechaEntrega(null);
            modelo.setEstado("Pendiente");
            double total = 0;
            for (int i = 0; i < vista.tblPedido.getRowCount(); i++) {
                double subtotal = Double.parseDouble(vista.tblPedido.getValueAt(i, 4).toString());
                total += subtotal;
            }
            modelo.setTotal(total);
            // Crear una lista para los detalles de la venta
            List<DetallePedido> detallesPedido = new ArrayList<>();

            for (int i = 0; i < vista.tblPedido.getRowCount(); i++) {
                int idproducto = (int) vista.tblPedido.getValueAt(i, 0);
                int cantidad = (int) vista.tblPedido.getValueAt(i, 3);
                double precio = (double) vista.tblPedido.getValueAt(i, 2);
                String fechaHora = vista.tblPedido.getValueAt(i, 5).toString();
                double subtotal = Double.parseDouble(vista.tblPedido.getValueAt(i, 4).toString());

                // Crear una nueva instancia de detalle de venta (modelo3)
                DetallePedido detalle = new DetallePedido();
                detalle.setNombreProducto(String.valueOf(idproducto));
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precio);
                detalle.setFechaHoraAgregado(fechaHora);
                detalle.setSubtotal(subtotal);
                // Agregar cada detalle a la lista
                detallesPedido.add(detalle);
            }

            if (dao.createPedido(modelo, detallesPedido)) {
                JOptionPane.showMessageDialog(null, "Pedido Realizado");
                proveedorPedido = null;
                lista.clear();
                dtmodel2.setRowCount(0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
            }

        } catch (NumberFormatException e) {
            System.out.println("a");
        }
    }

    private void buscarProducto() {
        String nombreProductoBuscado = vista.txtBusqueda.getText().trim();
        String proveedor = vista.cbxProveedor.getSelectedItem().toString();

        if (nombreProductoBuscado.equals("Buscar producto...") || proveedor.isEmpty() || nombreProductoBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el producto a buscar");
        } else {
            List<ProductoProveedor> productosEncontrados = dao.BuscarProducto(nombreProductoBuscado, proveedor);
            if (productosEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontro el producto");
            } else {
                ProductoProveedor producto = productosEncontrados.get(0);
                // Asigna los valores a los campos de la vista
                vista.lblProductID.setText(String.valueOf(producto.getProductoPro_ID()));
                vista.lblNombreProducto.setText(producto.getNombreProductoPro());
                vista.lblCategoria.setText(producto.getCategoria());
                vista.lblPrecio.setText(String.valueOf(producto.getPrecio()));
                vista.txtDescripcion.setText(producto.getDescripcion());
            }
        }
    }

    public void llenarComboBoxProveedores() {
        List<String> proveedores = dao.getProveedores();

        if (proveedores.isEmpty()) {
            System.out.println("No se encontraron Proveedores");
        } else {
            vista.cbxProveedor.removeAllItems();
            for (String proveedor : proveedores) {
                vista.cbxProveedor.addItem(proveedor);
            }
        }
    }

    private void calcular() {
        int cantidad = Integer.parseInt(vista.spnCantidad.getValue().toString());
        double precio = Double.parseDouble(vista.lblPrecio.getText());

        double subtotal = cantidad * precio;

        vista.lblSubtotal.setText(String.valueOf(subtotal));
    }


    private void AgregarProducto() {
        dtmodel2 = (DefaultTableModel) vista.tblPedido.getModel();

        int idProducto = Integer.parseInt(vista.lblProductID.getText());
        String nombreProducto = vista.lblNombreProducto.getText();
        String proveedorActual = vista.cbxProveedor.getSelectedItem().toString();
        double precio = Double.parseDouble(vista.lblPrecio.getText());
        int cantidad = Integer.parseInt(vista.spnCantidad.getValue().toString());
        double subtotal = Double.parseDouble(vista.lblSubtotal.getText());
        String fechaHora = vista.lblFecha.getText() + " " + vista.lblHora.getText();

        if (proveedorPedido == null) {
            proveedorPedido = proveedorActual;
        }

        if (!proveedorPedido.equals(proveedorActual)) {
            JOptionPane.showMessageDialog(null, "No puede agregar productos de diferentes proveedores. Complete el pedido primero.");
            limpiar();
            return;
        }

        boolean productoExistente = false;

        for (Object[] item : lista) {
            int idExistente = (int) item[0];
            if (idExistente == idProducto) {
                int cantidadExistente = (int) item[3];
                item[3] = cantidadExistente + cantidad;
                item[4] = (double) item[2] * (int) item[3];
                productoExistente = true;
                break;
            }
        }

        if (!productoExistente) {
            Object[] producto = new Object[6];
            producto[0] = idProducto;
            producto[1] = nombreProducto;
            producto[2] = precio;
            producto[3] = cantidad;
            producto[4] = subtotal;
            producto[5] = fechaHora;

            lista.add(producto);
        }

        dtmodel2.setRowCount(0);

        for (Object[] item : lista) {
            if (item != null && item.length > 0) {
                dtmodel2.addRow(item);
            }
        }
        limpiar();
    }

    private void Eliminar() {
        int selectedRow = vista.tblPedido.getSelectedRow();
        if (selectedRow >= 0) {
            Integer idProducto = (Integer) vista.tblPedido.getValueAt(selectedRow, 0);

            for (int i = 0; i < lista.size(); i++) {
                Object[] item = lista.get(i);
                if (item[0].equals(idProducto)) { 
                    lista.remove(i);
                    break;
                }
            }

            DefaultTableModel dtmodel = (DefaultTableModel) vista.tblPedido.getModel();
            dtmodel.setRowCount(0);

            for (Object[] item : lista) {
                dtmodel.addRow(item);
            }

            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
        }
    }

    private void limpiar() {
        vista.txtBusqueda.setText("Buscar producto...");
        vista.txtBusqueda.setForeground(new Color(153, 153, 153));
        vista.lblProductID.setText("");
        vista.lblNombreProducto.setText("");
        vista.lblCategoria.setText("");
        vista.lblPrecio.setText("");
        vista.txtDescripcion.setText("");
        vista.spnCantidad.setValue(1);
        vista.lblSubtotal.setText("");

    }

    private void renderizar() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < vista.tblPedido.getColumnCount(); i++) {
            vista.tblPedido.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblPedido.getColumnCount(); i++) {
            vista.tblPedido.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void seleccionar() {
        int selectedRow = vista.tblPedido.getSelectedRow();
        if (selectedRow >= 0) {
            // Obtener los valores de la fila seleccionada
            String nombreProducto = vista.tblPedido.getValueAt(selectedRow, 1).toString();
            String proveedorActual = vista.cbxProveedor.getSelectedItem().toString();
            System.out.println("odi");
            // Llamar al método de búsqueda en el dao con el nombre del producto y proveedor actual
            List<ProductoProveedor> productosEncontrados = dao.BuscarProducto(nombreProducto, proveedorActual);

            if (!productosEncontrados.isEmpty()) {
                // Asignar el primer resultado (se asume que es único)
                ProductoProveedor producto = productosEncontrados.get(0);

                vista.lblProductID.setText(String.valueOf(producto.getProductoPro_ID()));
                vista.lblNombreProducto.setText(producto.getNombreProductoPro());
                vista.lblCategoria.setText(producto.getCategoria());
                vista.lblPrecio.setText(String.valueOf(producto.getPrecio()));
                vista.txtDescripcion.setText(producto.getDescripcion());
                vista.spnCantidad.setValue(1);

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto en el proveedor seleccionado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar");
        }
    }
    
    private void regresar(){
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuAdmin m = new MenuAdmin(usuarioLogeado);
        m.show();
        vista.dispose();
    }
    
    private void VerCatalogo(){
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        ListaProductosProveedores m = new ListaProductosProveedores(usuarioLogeado);
        m.show();
        vista.dispose();
    }

    private void txtMousePressed(JTextField textField, String placeholder) {

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

    private void initialize() {
        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRealizarPedido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblInformacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblProcesar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblProcesar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblProcesar) {
                    System.out.println("label Procesar presionado");
                    calcular();
                }
            }
        });
        this.vista.lblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblBuscar) {
                    System.out.println("label Buscar presionado");
                    buscarProducto();
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
        this.vista.lblAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblAgregar) {
                    System.out.println("label Agregar presionado");  // Depuración
                    AgregarProducto();
                }
            }
        });
        this.vista.lblRealizarPedido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblRealizarPedido) {
                    System.out.println("label Relizar Pedido presionado");  // Depuración
                    CrearPedido();
                }
            }
        });
        this.vista.lblInformacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblInformacion) {
                    System.out.println("label Informacion presionado");  // Depuración
                    VerCatalogo();
                }
            }
        });
        this.vista.lblEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblEliminar) {
                    System.out.println("label Eliminar presionado");  // Depuración
                    Eliminar();
                }
            }
        });
        // Agregar MouseListener a la tabla para detectar doble clic
        vista.tblPedido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detectar doble clic
                    seleccionar();
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

}
