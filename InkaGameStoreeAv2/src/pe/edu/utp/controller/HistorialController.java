/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.controller;

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
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;
import pe.edu.utp.vista.Historial;
import pe.edu.utp.vista.MenuAdmin;
import pe.edu.utp.vista.MenuEmpleado;

/**
 *
 * @author Jessica Parra
 */
public class HistorialController {

    private Venta modelo;
    private VentaDao dao;
    private Historial vista;
    private JTextField campoActual = null;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;

    public HistorialController(Venta modelo, VentaDao dao, Historial vista) {
        this.modelo = modelo;
        this.dao = dao;
        this.vista = vista;
        initialize();
    }

    public void iniciar() {
        vista.setTitle("Historial de Ventas");
        vista.setLocationRelativeTo(null);
        int usuarioID = SesionUsuario.getIdLogeado();
        listar(usuarioID);
        calcularTotalVentas();
    }

    public void listar(int usuarioID) { 
        System.out.println("Listando ventas...");
        dtmodel = (DefaultTableModel) vista.tblvent.getModel();
        vista.tblvent.setModel(dtmodel);
        List<Venta> lista = dao.buscardorVentasPorUsuarioID(usuarioID);
        dtmodel.setRowCount(0);
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < vista.tblvent.getColumnCount(); i++) {
            vista.tblvent.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            vista.tblvent.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        vista.tblvent.setModel(dtmodel);
    }

    private int calcularNumeroVentasRecursivo(int filaActual) {
        if (filaActual >= vista.tblvent.getRowCount()) {
            return 0;  // No hay más filas
        }
        return 1 + calcularNumeroVentasRecursivo(filaActual + 1);  // Contamos esta fila y seguimos
    }

    private void calcularTotalVentas() {
        int totalVentas = calcularNumeroVentasRecursivo(0);
        vista.lbltvent.setText(String.valueOf(totalVentas));  // Mostrar el total de ventas como un entero
    }

    private void MostrarDetalle(int VentaID) {
        System.out.println("Listando ventas...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tbldvent.getModel();
        vista.tbldvent.setModel(dtmodel);
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

        for (int i = 0; i < vista.tbldvent.getColumnCount(); i++) {
            vista.tbldvent.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tbldvent.getColumnCount(); i++) {
            vista.tbldvent.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }
    
    private void RegresarMenu() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuEmpleado m = new MenuEmpleado(usuarioLogeado);
        m.show();
        vista.dispose();
    }
    
    private void initialize() {
        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblregresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblregresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblregresar) {
                    System.out.println("label Regresar presionado");  // Depuración
                    RegresarMenu();
                }
            }
        });

        // Agregar MouseListener a la tabla para detectar doble clic
        vista.tblvent.addMouseListener(new MouseAdapter() {
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

    }
}
