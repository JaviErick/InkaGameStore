package pe.edu.utp.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.PedidoDao;
import pe.edu.utp.entity.DetallePedido;
import pe.edu.utp.entity.Pedido;
import pe.edu.utp.vista.GestorPedidos;

public class PedidosController {

    private Pedido modelo;
    private DetallePedido modelo2;
    private PedidoDao dao;
    private GestorPedidos vista;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;
    private JTextField campoActual = null;

    public PedidosController(Pedido modelo, DetallePedido modelo2, PedidoDao dao, GestorPedidos vista) {
        this.modelo = modelo;
        this.modelo2 = modelo2;
        this.dao = dao;
        this.vista = vista;
        initialize();
    }

    public void iniciar() {
        vista.setTitle("Gestor Pedidos");
        vista.setLocationRelativeTo(null);
        listar();
        renderizar();
        llenarComboBoxCategorias();
        llenarComboBoxProveedores();
    }

    private void listar() {
        System.out.println("Listando Pedidos...");
        dtmodel = (DefaultTableModel) vista.tblPedidos.getModel();
        vista.tblPedidos.setModel(dtmodel);
        dtmodel.setRowCount(0);
        List<Pedido> lista = dao.ListarAllPedidos();
        Object[] objeto = new Object[7];
        for (Pedido pedido : lista) {
            objeto[0] = pedido.getPedidoID();
            objeto[1] = pedido.getNombreUsuario();
            objeto[2] = pedido.getFechaRealizada();
            objeto[3] = pedido.getFechaEntrega();
            objeto[4] = pedido.getTotal();
            objeto[5] = pedido.getEstado();
            dtmodel.addRow(objeto);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
            vista.tblPedidos.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
            vista.tblPedidos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        vista.tblPedidos.setModel(dtmodel);
    }

    private void MostrarDetalle(int PedidoID) {
        System.out.println("Listando Detalle Pedido..");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblDetalles.getModel();
        vista.tblDetalles.setModel(dtmodel);
        List<DetallePedido> lista = dao.MostrarDetallePedido(PedidoID);
        dtmodel.setRowCount(0);
        Object[] objeto = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getNombreProducto();
            objeto[1] = lista.get(i).getNombreProveedor();
            objeto[2] = lista.get(i).getNombreCategoria();
            objeto[3] = lista.get(i).getFechaHoraAgregado();
            objeto[4] = lista.get(i).getCantidad();
            objeto[5] = lista.get(i).getPrecio();
            objeto[6] = lista.get(i).getSubtotal();
            dtmodel.addRow(objeto);
        }
    }

    public void llenarComboBoxCategorias() {
        List<String> categorias = dao.getCategorias();

        if (categorias.isEmpty()) {
            System.out.println("No se encontraron categorías");
        } else {
            vista.cbxCategoria.removeAllItems();
            for (String categoria : categorias) {
                vista.cbxCategoria.addItem(categoria);
            }
        }
    }

    public void llenarComboBoxProveedores() {
        List<String> categorias = dao.getProveedores();

        if (categorias.isEmpty()) {
            System.out.println("No se encontraron categorías");
        } else {
            vista.cbxProveedor.removeAllItems();
            for (String categoria : categorias) {
                vista.cbxProveedor.addItem(categoria);
            }
        }
    }

    private void buscarPedido() {
        String criterio = vista.txtBusqueda.getText();
        List<Pedido> lista = dao.buscarPedido(criterio);
        dtmodel = (DefaultTableModel) vista.tblPedidos.getModel();
        vista.tblPedidos.setModel(dtmodel);
        dtmodel.setRowCount(0);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontró ningún Pedido.",
                    "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
            listar();
        } else {
            Object[] objeto = new Object[7];
            for (Pedido pedido : lista) {
                objeto[0] = pedido.getPedidoID();
                objeto[1] = pedido.getNombreUsuario();
                objeto[2] = pedido.getFechaRealizada();
                objeto[3] = pedido.getFechaEntrega();
                objeto[4] = pedido.getTotal();
                objeto[5] = pedido.getEstado();
                dtmodel.addRow(objeto);
            }
        }
        vista.tblPedidos.setModel(dtmodel);
        vista.txtBusqueda.setText("Buscar Pedido...");
        vista.txtBusqueda.setForeground(new Color(153, 153, 153));

    }

    private void filtrarPorProveedor() {
        System.out.println("Listando ProductosProveedor...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblPedidos.getModel();
        vista.tblPedidos.setModel(dtmodel);
        String categoriaSeleccionadaNombre = (String) vista.cbxProveedor.getSelectedItem();
        if (categoriaSeleccionadaNombre != null) {
            int ProveedorId = dao.ObtenerIDProveedor(categoriaSeleccionadaNombre);
            List<Pedido> lista = dao.filtrarPorProveedor(ProveedorId);
            dtmodel.setRowCount(0);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No se encontró ningún Pedido.",
                        "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
                listar();
            } else {
                Object[] objeto = new Object[7];
                for (Pedido pedido : lista) {
                    objeto[0] = pedido.getPedidoID();
                    objeto[1] = pedido.getNombreUsuario();
                    objeto[2] = pedido.getFechaRealizada();
                    objeto[3] = pedido.getFechaEntrega();
                    objeto[4] = pedido.getTotal();
                    objeto[5] = pedido.getEstado();
                    dtmodel.addRow(objeto);
                }
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);

                for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
                    vista.tblPedidos.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
                }
                for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
                    vista.tblPedidos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                vista.tblPedidos.setModel(dtmodel);
            }

        }
    }

    private void filtrarPorCategoria() {
        System.out.println("Listando ProductosProveedor...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblPedidos.getModel();
        vista.tblPedidos.setModel(dtmodel);
        String categoriaSeleccionadaNombre = (String) vista.cbxCategoria.getSelectedItem();
        if (categoriaSeleccionadaNombre != null) {
            int categoriaId = dao.ObtenerIDCategoria(categoriaSeleccionadaNombre);
            List<Pedido> lista = dao.filtrarPorCategoria(categoriaId);
            dtmodel.setRowCount(0);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No se encontró ningún Pedido.",
                        "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
                listar();
            } else {
                Object[] objeto = new Object[7];
                for (Pedido pedido : lista) {
                    objeto[0] = pedido.getPedidoID();
                    objeto[1] = pedido.getNombreUsuario();
                    objeto[2] = pedido.getFechaRealizada();
                    objeto[3] = pedido.getFechaEntrega();
                    objeto[4] = pedido.getTotal();
                    objeto[5] = pedido.getEstado();
                    dtmodel.addRow(objeto);
                }
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);

                for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
                    vista.tblPedidos.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
                }
                for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
                    vista.tblPedidos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                vista.tblPedidos.setModel(dtmodel);
            }
        }

    }

    private void filtrarPorPrecio() {
        String rangoSeleccionado = (String) vista.cbxPrecio.getSelectedItem();
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

        List<Pedido> lista = dao.filtrarPorRangoDePrecio(precioMin, precioMax);
        dtmodel.setRowCount(0);
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontró ningún Pedido.",
                    "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
            listar();
        } else {
            Object[] objeto = new Object[7];
            for (Pedido pedido : lista) {
                objeto[0] = pedido.getPedidoID();
                objeto[1] = pedido.getNombreUsuario();
                objeto[2] = pedido.getFechaRealizada();
                objeto[3] = pedido.getFechaEntrega();
                objeto[4] = pedido.getTotal();
                objeto[5] = pedido.getEstado();
                dtmodel.addRow(objeto);
            }
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
                vista.tblPedidos.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            }
            for (int i = 0; i < vista.tblPedidos.getColumnCount(); i++) {
                vista.tblPedidos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            vista.tblPedidos.setModel(dtmodel);
        }
    }

    private void ModificarPedido() {
        int selectedRow = vista.tblPedidos.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.valueOf(vista.tblPedidos.getValueAt(selectedRow, 0).toString());
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.out.println("Modificando cliente...");
                String fecha = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
                modelo.setFechaEntrega(fecha);
                modelo.setEstado(vista.cbxEstado.getSelectedItem().toString());
                modelo.setPedidoID((id));

                if (dao.updatePedido(modelo)) {
                    JOptionPane.showMessageDialog(null, "Pedido Modificado");
                    dtmodel.setRowCount(0);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Modificar");
                }
            }
        }
    }

    private void renderizar() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < vista.tblDetalles.getColumnCount(); i++) {
            vista.tblDetalles.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblDetalles.getColumnCount(); i++) {
            vista.tblDetalles.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void initialize() {
        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblPrecio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblCambiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblBuscar) {
                    System.out.println("label Buscar presionado");
                    buscarPedido();
                }
            }
        });
        this.vista.lblRegresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblRegresar) {
                    System.out.println("label Regresar presionado");  // Depuración

                }
            }
        });
        this.vista.lblProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblProveedor) {
                    System.out.println("label AProveedor presionado");  // Depuración
                    filtrarPorProveedor();
                }
            }
        });
        this.vista.lblCategoria.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblCategoria) {
                    System.out.println("label Relizar Categoria presionado");  // Depuración
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
        this.vista.lblCambiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblCambiar) {
                    System.out.println("label Cambiar presionado");  // Depuración
                    ModificarPedido();
                }
            }
        });
        this.vista.lblReporte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblReporte) {
                    System.out.println("label Reporte presionado");  // Depuración

                }
            }
        });
        // Agregar MouseListener a la tabla para detectar doble clic
        vista.tblPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int PedidoID = (int) target.getValueAt(row, 0);
                    MostrarDetalle(PedidoID);
                }
            }
        });
        // Agregar MouseListener a los campos de texto
        vista.txtBusqueda.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtBusqueda, "Buscar Pedido...");
            }
        });
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
            return "Buscar Pedido...";
        }
        return "";
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtBusqueda)) {
            textField.setText("Buscar Pedido...");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }
}
