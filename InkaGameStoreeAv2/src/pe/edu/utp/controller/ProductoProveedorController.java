package pe.edu.utp.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.PedidoDao;
import pe.edu.utp.dao.ProductoProveedorDao;
import pe.edu.utp.daoImpl.PedidoDaoImpl;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.DetallePedido;
import pe.edu.utp.entity.Pedido;
import pe.edu.utp.entity.ProductoProveedor;
import pe.edu.utp.vista.ListaProductosProveedores;
import pe.edu.utp.vista.MenuAdmin;
import pe.edu.utp.vista.Pedidos;

public class ProductoProveedorController {

    private ProductoProveedor modelo;
    private ProductoProveedorDao dao;
    private ListaProductosProveedores vista;
    private DefaultTableModel dtmodel = new DefaultTableModel();

    public ProductoProveedorController(ProductoProveedor modelo, ProductoProveedorDao dao, ListaProductosProveedores vista) {
        this.modelo = modelo;
        this.dao = dao;
        this.vista = vista;
        initialize();  // Vincula los eventos a los botones

    }

    public void iniciar() {
        vista.setTitle("Gestor de Productos");
        vista.setLocationRelativeTo(null);
        listar();
        llenarComboBoxCategorias();
        llenarComboBoxProveedores();
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
        if (textField.equals(vista.txtBuscarProd)) {
            return "Buscar producto...";
        }
        return "";
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtBuscarProd)) {
            textField.setText("Buscar producto...");
        }
        textField.setForeground(new Color(153, 153, 153));
    }

    private void initialize() {

        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblProv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblCat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblPrecio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGenerarPedidoo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblAgregarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblGenerarPedidoo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGenerarPedidoo) {
                    System.out.println("label GenerarPedido presionado");  // Depuración
                    Pedidos();
                }
            }
        });
        this.vista.lblAgregarPro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblAgregarPro) {
                    System.out.println("label AgregarPro presionado");  // Depuración
                    agregarProPedido();
                }
            }
        });
        this.vista.lblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblBuscar) {
                    System.out.println("label Buscar presionado");  // Depuración
                    buscarProductos();
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
        this.vista.lblProv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblProv) {
                    System.out.println("label Ver Producto presionado");  // Depuración
                    filtrarPorProveedor();
                }
            }
        });
        this.vista.lblCat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblCat) {
                    System.out.println("label Categoria presionado");  // Depuración
                    filtrarPorCategoria();
                }
            }
        });
        this.vista.lblPrecio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblPrecio) {
                    System.out.println("label Precio presionado");  // Depuración
                    filtrarPorPrecio();
                }
            }
        });

        // Agregar MouseListener a los campos de texto
        vista.txtBuscarProd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtBuscarProd, "Buscar producto...");
            }
        });

    }

    public class PedidoManager {

        public static List<Object[]> listaPedidos = new ArrayList<>();
    }

    private void agregarProPedido() {
        try {
            int selectedRow = vista.tblLista.getSelectedRow();
            if (selectedRow >= 0) {
                Integer idProducto = (Integer) vista.tblLista.getValueAt(selectedRow, 0);
                String NombreProducto = (String) vista.tblLista.getValueAt(selectedRow, 1);
                Double Precio = (Double) vista.tblLista.getValueAt(selectedRow, 4);
                String proveedor = (String) vista.tblLista.getValueAt(selectedRow, 3);
                Integer cantidad = 0;
                Double subtotal = 0.0;
                String fechaHora = new SimpleDateFormat("YYYY-MM-dd").format(new Date()) + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                Object[] producto = new Object[8];
                producto[0] = idProducto;
                producto[1] = NombreProducto;
                producto[2] = Precio;
                producto[3] = cantidad;
                producto[4] = subtotal;
                producto[5] = fechaHora;
                producto[6] = proveedor;

                PedidoManager.listaPedidos.add(producto);
            }
            JOptionPane.showMessageDialog(null, "Producto agregado.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar: " + e.getMessage());
        }
    }

    private void Pedidos() {
        Pedido model = new Pedido();
        DetallePedido model2 = new DetallePedido();
        PedidoDao pdao = new PedidoDaoImpl();
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        Pedidos vist = new Pedidos(usuarioLogeado);
        CrearPedidoController control = new CrearPedidoController(PedidoManager.listaPedidos, model, model2, pdao, vist);
        control.Iniciar();
        DefaultTableModel dtmodel = (DefaultTableModel) vist.tblPedido.getModel();
        dtmodel.setRowCount(0);
        // Agregar datos al modelo de tabla
        for (Object[] item : PedidoManager.listaPedidos) {
            if (item != null && item.length > 0) {
                Object[] fila = {
                    item[0], // idProducto
                    item[1], // nombreProducto
                    item[2], // Precio
                    item[3], // Cantidad
                    item[4], // Subtotal
                    item[5], // fechaHora
                };
                vist.cbxProveedor.setSelectedItem(item[6]);
                dtmodel.addRow(fila);
            }
        }
        

        vist.tblPedido.setModel(dtmodel);
        vist.setVisible(true);
        vista.dispose();
    }

    public void listar() {
        System.out.println("Listando ProductosProveedor...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblLista.getModel();
        vista.tblLista.setModel(dtmodel);
        List<ProductoProveedor> lista = dao.readAllProductoProveedor();
        dtmodel.setRowCount(0);
        Object[] objeto = new Object[7];  // Ajusta según los atributos de tu entidad Clientes
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getProductoPro_ID();
            objeto[1] = lista.get(i).getNombreProductoPro();
            objeto[2] = lista.get(i).getCategoria();
            objeto[3] = lista.get(i).getProveedor();
            objeto[4] = lista.get(i).getPrecio();
            objeto[5] = lista.get(i).getDescripcion();

            dtmodel.addRow(objeto);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < vista.tblLista.getColumnCount(); i++) {
            vista.tblLista.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblLista.getColumnCount(); i++) {
            vista.tblLista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        vista.tblLista.setModel(dtmodel);
    }

    public void llenarComboBoxCategorias() {     //metodo para item
        List<String> categorias = dao.getCategorias();

        if (categorias.isEmpty()) {
            System.out.println("No se encontraron categorías");
        } else {
            vista.cmbSelCat.removeAllItems();
            for (String categoria : categorias) {
                vista.cmbSelCat.addItem(categoria);
            }
        }
    }

    public void llenarComboBoxProveedores() {     //metodo para item
        List<String> proveedores = dao.getProveedores();

        if (proveedores.isEmpty()) {
            System.out.println("No se encontraron categorías");
        } else {
            vista.cmbSelect.removeAllItems();
            for (String proveedor : proveedores) {
                vista.cmbSelect.addItem(proveedor);
            }
        }
    }

    private void buscarProductos() {
        String criterio = vista.txtBuscarProd.getText();
        List<ProductoProveedor> lista = dao.buscarProducto(criterio);
        dtmodel = (DefaultTableModel) vista.tblLista.getModel();
        vista.tblLista.setModel(dtmodel);
        dtmodel.setRowCount(0);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontró ningún producto.",
                    "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
            listar();
        } else {
            Object[] objeto = new Object[7];
            for (int i = 0; i < lista.size(); i++) {
                objeto[0] = lista.get(i).getProductoPro_ID();
                objeto[1] = lista.get(i).getNombreProductoPro();
                objeto[2] = lista.get(i).getCategoria();
                objeto[3] = lista.get(i).getProveedor();
                objeto[4] = lista.get(i).getPrecio();
                objeto[5] = lista.get(i).getDescripcion();

                dtmodel.addRow(objeto);
            }
        }
        vista.tblLista.setModel(dtmodel);
        vista.txtBuscarProd.setText("Buscar producto...");
        vista.txtBuscarProd.setForeground(new Color(153, 153, 153));

    }

    private void regresar() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuAdmin menu = new MenuAdmin(usuarioLogeado);
        menu.show();
        vista.dispose();
    }

    private void filtrarPorProveedor() {
        System.out.println("Listando ProductosProveedor...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblLista.getModel();
        vista.tblLista.setModel(dtmodel);
        String categoriaSeleccionadaNombre = (String) vista.cmbSelect.getSelectedItem();
        if (categoriaSeleccionadaNombre != null) {
            int ProveedorId = dao.ObtenerIDProveedor(categoriaSeleccionadaNombre);
            List<ProductoProveedor> lista = dao.filtrarPorProveedor(ProveedorId);
            dtmodel.setRowCount(0);
            Object[] objeto = new Object[7];  // Ajusta según los atributos de tu entidad Clientes
            for (int i = 0; i < lista.size(); i++) {
                objeto[0] = lista.get(i).getProductoPro_ID();
                objeto[1] = lista.get(i).getNombreProductoPro();
                objeto[2] = lista.get(i).getCategoria();
                objeto[3] = lista.get(i).getProveedor();
                objeto[4] = lista.get(i).getPrecio();
                objeto[5] = lista.get(i).getDescripcion();

                dtmodel.addRow(objeto);
            }
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < vista.tblLista.getColumnCount(); i++) {
                vista.tblLista.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            }
            for (int i = 0; i < vista.tblLista.getColumnCount(); i++) {
                vista.tblLista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            vista.tblLista.setModel(dtmodel);
        }

    }

    private void filtrarPorCategoria() {
        System.out.println("Listando ProductosProveedor...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblLista.getModel();
        vista.tblLista.setModel(dtmodel);
        String categoriaSeleccionadaNombre = (String) vista.cmbSelCat.getSelectedItem();
        if (categoriaSeleccionadaNombre != null) {
            int categoriaId = dao.ObtenerIDCategoria(categoriaSeleccionadaNombre);
            List<ProductoProveedor> lista = dao.filtrarPorCategoria(categoriaId);

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No se encontró ningún producto.",
                        "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
                listar();
            } else {
                dtmodel.setRowCount(0);
                Object[] objeto = new Object[7];  // Ajusta según los atributos de tu entidad Clientes
                for (int i = 0; i < lista.size(); i++) {
                    objeto[0] = lista.get(i).getProductoPro_ID();
                    objeto[1] = lista.get(i).getProveedor();
                    objeto[2] = lista.get(i).getCategoria();
                    objeto[3] = lista.get(i).getDescripcion();
                    objeto[4] = lista.get(i).getPrecio();
                    objeto[5] = lista.get(i).getNombreProductoPro();

                    dtmodel.addRow(objeto);
                }
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);

                for (int i = 0; i < vista.tblLista.getColumnCount(); i++) {
                    vista.tblLista.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
                }
                for (int i = 0; i < vista.tblLista.getColumnCount(); i++) {
                    vista.tblLista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                vista.tblLista.setModel(dtmodel);
            }
        }
    }

    private void filtrarPorPrecio() {
        String rangoSeleccionado = (String) vista.cmbSelRP.getSelectedItem();
        double precioMin = 0;
        double precioMax = Double.MAX_VALUE;  // Usamos Double.MAX_VALUE para representar "sin límite"

        switch (rangoSeleccionado) {
            case "0 - 1000":
                precioMin = 0;
                precioMax = 1000;
                break;
            case "1001 - 3000":
                precioMin = 1001;
                precioMax = 3000;
                break;
            case "3001 - max":
                precioMin = 3001;
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rango no válido");
                return;
        }

        List<ProductoProveedor> productos = dao.filtrarPorRangoDePrecio(precioMin, precioMax);
        dtmodel.setRowCount(0);

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontró ningún producto.",
                    "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
            listar();
        } else {
            Object[] objeto = new Object[7];  // Ajusta según los atributos de tu entidad Clientes
            for (int i = 0;
                    i < productos.size();
                    i++) {
                objeto[0] = productos.get(i).getProductoPro_ID();
                objeto[1] = productos.get(i).getNombreProductoPro();
                objeto[2] = productos.get(i).getCategoria();
                objeto[3] = productos.get(i).getProveedor();
                objeto[4] = productos.get(i).getPrecio();
                objeto[5] = productos.get(i).getDescripcion();

                dtmodel.addRow(objeto);
            }
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0;
                    i < vista.tblLista.getColumnCount();
                    i++) {
                vista.tblLista.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            }
            for (int i = 0;
                    i < vista.tblLista.getColumnCount();
                    i++) {
                vista.tblLista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            vista.tblLista.setModel(dtmodel);
        }
    }
}
