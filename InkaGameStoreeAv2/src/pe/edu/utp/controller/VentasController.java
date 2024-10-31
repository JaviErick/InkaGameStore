package pe.edu.utp.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.VentaDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Clientes;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;
import pe.edu.utp.vista.GestorVentas;
import pe.edu.utp.vista.MenuAdmin;

/**
 *
 * @author Jessica Parra
 */
public class VentasController {

    private Venta modelo;
    private VentaDao dao;
    private GestorVentas vista;
    private JTextField campoActual = null;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;

    public VentasController(Venta modelo, VentaDao dao, GestorVentas vista) {
        this.modelo = modelo;
        this.dao = dao;
        this.vista = vista;
        initialize();
    }

    public void iniciar() {
        vista.setTitle("Gestor de Ventas");
        vista.setLocationRelativeTo(null);
        listar();
        calcularTotalVentas();
        calcularTotalIngresos();
    }

    public void listar() {
        System.out.println("Listando ventas...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblVenta.getModel();
        vista.tblVenta.setModel(dtmodel);
        List<Venta> lista = dao.readAllVenta();
        dtmodel.setRowCount(0);
        Object[] objeto = new Object[7];  // Ajusta según los atributos de tu entidad Clientes
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getVentaID();
            objeto[1] = lista.get(i).getClienteID();  // Esto está bien, no necesita conversión
            objeto[2] = lista.get(i).getUsuarioID();
            objeto[3] = lista.get(i).getTotal();
            objeto[4] = lista.get(i).getFecha();
            objeto[5] = lista.get(i).getHora();
            dtmodel.addRow(objeto);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < vista.tblVenta.getColumnCount(); i++) {
            vista.tblVenta.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblVenta.getColumnCount(); i++) {
            vista.tblVenta.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        vista.tblVenta.setModel(dtmodel);
    }

    private void buscarVentas() {
        String criterio = vista.txtBuscar.getText();
        List<Venta> lista = dao.buscardorVentas(criterio);
        dtmodel = (DefaultTableModel) vista.tblVenta.getModel();
        vista.tblVenta.setModel(dtmodel);
        dtmodel.setRowCount(0);
        // Verificar si la lista está vacía para mostrar el mensaje
        if (lista.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(vista, "No se encontró ninguna venta.",
                    "Búsqueda sin resultados", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            listar();
        } else {
            Object[] objeto = new Object[7];
            for (Venta venta : lista) {
                objeto[0] = venta.getVentaID();
                objeto[1] = venta.getClienteID();
                objeto[2] = venta.getUsuarioID();
                objeto[3] = venta.getTotal();
                objeto[4] = venta.getFecha();
                objeto[5] = venta.getHora();
                dtmodel.addRow(objeto);
            }
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < vista.tblVenta.getColumnCount(); i++) {
            vista.tblVenta.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblVenta.getColumnCount(); i++) {
            vista.tblVenta.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        vista.tblVenta.setModel(dtmodel);
    }

    private void MostrarDetalle(int VentaID) {
        System.out.println("Listando ventas...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblDetalleVenta.getModel();
        vista.tblDetalleVenta.setModel(dtmodel);
        List<DetalleVenta> lista = dao.MostrarDetalleVenta(VentaID);
        dtmodel.setRowCount(0);
        Object[] objeto = new Object[6];  // Ajusta según los atributos de tu entidad Clientes
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getProductoID();
            objeto[1] = lista.get(i).getCantidad();  // Esto está bien, no necesita conversión
            objeto[2] = lista.get(i).getPrecio();
            objeto[3] = lista.get(i).getSubtotal();
            objeto[4] = lista.get(i).getFechHoraAgreCarrito();
            dtmodel.addRow(objeto);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < vista.tblDetalleVenta.getColumnCount(); i++) {
            vista.tblDetalleVenta.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblDetalleVenta.getColumnCount(); i++) {
            vista.tblDetalleVenta.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }
    
    private void RegresarMenu() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuAdmin m = new MenuAdmin(usuarioLogeado);
        m.show();
        vista.dispose();
    }

    private double calcularTotalRecursivo(int filaActual) {
        if (filaActual >= vista.tblVenta.getRowCount()) {
            return 0;
        }
        double totalVenta = Double.parseDouble(vista.tblVenta.getValueAt(filaActual, 3).toString());
        return totalVenta + calcularTotalRecursivo(filaActual + 1);
    }

    private void calcularTotalIngresos() {
        double total = calcularTotalRecursivo(0);
        DecimalFormat sf = new DecimalFormat("#,##0.00"); // Formato para mostrar dos decimales
        vista.lblTotalIngresos.setText(sf.format(total));
    }

    
    private int calcularNumeroVentasRecursivo(int filaActual) {
        if (filaActual >= vista.tblVenta.getRowCount()) {
            return 0;  // No hay más filas
        }
        return 1 + calcularNumeroVentasRecursivo(filaActual + 1);  // Contamos esta fila y seguimos
    }

    private void calcularTotalVentas() {
        int totalVentas = calcularNumeroVentasRecursivo(0);
        vista.lblTotalVentas.setText(String.valueOf(totalVentas));  // Mostrar el total de ventas como un entero
    }

    private void initialize() {
        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblBuscar) {
                    System.out.println("label Registrar presionado");  // Depuración
                    if ("Buscar Ventas...".equals(vista.txtBuscar.getText()) || vista.txtBuscar.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Ingresa el criterio a buscar");
                    } else {
                        buscarVentas();
                    }

                }
            }
        });
        this.vista.lblRegresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblRegresar) {
                    System.out.println("label Regresar presionado");  // Depuración
                    RegresarMenu();
                }
            }
        });

        // Agregar MouseListener a la tabla para detectar doble clic
        vista.tblVenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detectar doble clic
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();  // Obtener la fila seleccionada
                    int ventaID = (int) target.getValueAt(row, 0);  // Asumimos que el ID está en la primera columna (columna 0)

                    // Llamar al método que maneja la acción
                    MostrarDetalle(ventaID);
                }
            }
        });

        // Agregar MouseListener a los campos de texto
        vista.txtBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtBuscar, "Buscar Ventas...");
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
        if (textField.equals(vista.txtBuscar)) {
            textField.setText("Buscar Ventas...");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

    private String getPlaceholderText(JTextField textField) {
        if (textField.equals(vista.txtBuscar)) {
            return "Buscar Ventas...";
        }
        return "";
    }
}
