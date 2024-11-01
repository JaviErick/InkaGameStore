package pe.edu.utp.controller;

import pe.edu.utp.vista.GestorProveedores;
import pe.edu.utp.vista.MenuAdmin;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.ProveedorDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Proveedor;
import pe.edu.utp.service.ExcelProveedores;
/**
 *
 * @author javie
 */
public class ProveedorController {

    private Proveedor modelo;
    private ProveedorDao pdao;
    private GestorProveedores vista;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;
    private JTextField campoActual = null;

    // Constructor del controlador
    public ProveedorController(Proveedor modelo, ProveedorDao pdao, GestorProveedores vista) {
        this.modelo = modelo;
        this.pdao = pdao;
        this.vista = vista;
        initialize();  // Vincula los eventos a los botones
    }

    // Método para iniciar la vista
    public void iniciar() {
        vista.setTitle("Gestor de Proveedor");
        vista.setLocationRelativeTo(null);
        vista.txtID.setEnabled(false);
        listar();  // Lista los clientes al inicio
    }

    // Método para eliminar un cliente seleccionado de la tabla
    private void eliminarProveedor() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar a este proveedor?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Eliminando Proveedor...");  // Depuración
            int fila = vista.tblProveedor.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int id = Integer.parseInt(vista.tblProveedor.getValueAt(fila, 0).toString());
                if (pdao.deleteProveedor(id)) {
                    JOptionPane.showMessageDialog(null, "Proveedor Eliminado");
                    limpiarTabla();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                }
            }
        }
    }

    // Método para modificar un cliente existente
    private void modificarProveedor() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Modificando Proveedor...");
            modelo.setProveedorID(Integer.parseInt(vista.txtID.getText()));
            modelo.setRuc(vista.txtRuc.getText());
            modelo.setNombreCompleto(vista.txtNombre.getText());
            modelo.setTelefono(vista.txtTelefono.getText());
            modelo.setDireccion(vista.txtDireccion.getText());
            modelo.setCorreo(vista.txtCorreo.getText());
            System.out.println(modelo);

            if (pdao.updateProveedor(modelo)) {
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                limpiar();
                limpiarTabla();
                listar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
            }
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiar() {
        System.out.println("Limpiando formulario...");  // Depuración
        vista.txtRuc.setText("Ingrese su RUC...");
        vista.txtRuc.setForeground(new Color(153, 153, 153)); // Color del placeholder

        vista.txtNombre.setText("Ingrese sus Nombres y Apellidos...");
        vista.txtNombre.setForeground(new Color(153, 153, 153));

        vista.txtTelefono.setText("Ingrese su Telefono...");
        vista.txtTelefono.setForeground(new Color(153, 153, 153));

        vista.txtDireccion.setText("Ingrese su Dirección...");
        vista.txtDireccion.setForeground(new Color(153, 153, 153));

        vista.txtCorreo.setText("Ingrese su Correo...");
        vista.txtCorreo.setForeground(new Color(153, 153, 153));

        vista.txtID.setText("AutoIncrementable");
    }

    // Método para guardar un Proveedor en la base de datos
    private void guardarProveedor() {
        String codigoBuscar = vista.txtID.getText().trim();
        boolean encontrado = false;
        // Recorrer las filas de la tabla para comprobar si el código ya existe
        for (int i = 0; i < vista.tblProveedor.getRowCount(); i++) {
            Object valorCelda = vista.tblProveedor.getValueAt(i, 0);  // Cambia 0 por el índice de la columna que necesites
            // Comparar el valor de la celda con el código buscado
            if (codigoBuscar.equals(valorCelda.toString())) {
                encontrado = true;
                modificarProveedor();
                limpiar();
                break;
            }
}


        // Si el código no se encontró en la tabla, agregar un nuevo producto
        if (!encontrado) {
            try {
                System.out.println("Guardando Proveedor...");  // Depuración
                modelo.setRuc(vista.txtRuc.getText());
                modelo.setNombreCompleto(vista.txtNombre.getText());
                modelo.setTelefono(vista.txtTelefono.getText());
                modelo.setDireccion(vista.txtDireccion.getText());
                modelo.setCorreo(vista.txtCorreo.getText());

                if (pdao.createProveedor(modelo)) {
                    JOptionPane.showMessageDialog(null, "Proveedor Guardado");
                    limpiar();
                    limpiarTabla();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                }

            } catch (NumberFormatException e) {
                System.out.println("a");
            }
        }

    }

    private void SeleccionarProveedor() {
        int selectedRow = vista.tblProveedor.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.parseInt(vista.tblProveedor.getValueAt(selectedRow, 0).toString());
            String Ruc = vista.tblProveedor.getValueAt(selectedRow, 1).toString();
            String nombre = vista.tblProveedor.getValueAt(selectedRow, 2).toString();
            String telefono = vista.tblProveedor.getValueAt(selectedRow, 3).toString();
            String direccion = vista.tblProveedor.getValueAt(selectedRow, 4).toString();
            String correo = vista.tblProveedor.getValueAt(selectedRow, 5).toString();

            vista.txtID.setText(String.valueOf(id));
            vista.txtRuc.setText(Ruc);
            vista.txtNombre.setText(nombre);
            vista.txtTelefono.setText(telefono);
            vista.txtDireccion.setText(direccion);
            vista.txtCorreo.setText(correo);

            vista.txtRuc.setForeground(Color.black);
            vista.txtNombre.setForeground(Color.black);
            vista.txtTelefono.setForeground(Color.black);
            vista.txtTelefono.setForeground(Color.black);
            vista.txtDireccion.setForeground(Color.black);
            vista.txtCorreo.setForeground(Color.black);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar");
        }

    }
 private void generarReporteExcel() {
    // Obtener lista de clientes
    List<Proveedor> listaProveedores = pdao.readAllProveedor();  // Obtiene todos los clientes desde ClientesDao
    if (!listaProveedores.isEmpty()) {
        // Llamar al servicio para generar el reporte
        ExcelProveedores.generarReporteProveedores(listaProveedores);
    } else {
        JOptionPane.showMessageDialog(null, "No hay clientes para generar el reporte.");
    }
    }
    // Método para limpiar la tabla de Proveedores
    private void limpiarTabla() {
        System.out.println("Limpiando tabla de Proveedores...");  // Depuración
        dtmodel.setRowCount(0);
    }

    private void RegresarMenu() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuAdmin m = new MenuAdmin(usuarioLogeado);
        m.show();
    }

    private void listar() {
        System.out.println("Listando clientes...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblProveedor.getModel();
        vista.tblProveedor.setModel(dtmodel);
        List<Proveedor> lista = pdao.readAllProveedor();
        Object[] objeto = new Object[6];  // Ajusta según los atributos de tu entidad Clientes
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getProveedorID();
            objeto[1] = lista.get(i).getRuc();
            objeto[2] = lista.get(i).getNombreCompleto();
            objeto[3] = lista.get(i).getTelefono();
            objeto[4] = lista.get(i).getDireccion();
            objeto[5] = lista.get(i).getCorreo();
            dtmodel.addRow(objeto);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < vista.tblProveedor.getColumnCount(); i++) {
            vista.tblProveedor.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblProveedor.getColumnCount(); i++) {
            vista.tblProveedor.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        vista.tblProveedor.setModel(dtmodel);
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
        if (textField.equals(vista.txtRuc)) {
            textField.setText("Ingrese su RUC...");
        } else if (textField.equals(vista.txtNombre)) {
            textField.setText("Ingrese sus Nombres y Apellidos...");
        } else if (textField.equals(vista.txtTelefono)) {
            textField.setText("Ingrese su Telefono...");
        } else if (textField.equals(vista.txtDireccion)) {
            textField.setText("Ingrese su Dirección...");
        } else if (textField.equals(vista.txtCorreo)) {
            textField.setText("Ingrese su Correo...");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

    private String getPlaceholderText(JTextField textField) {
        if (textField.equals(vista.txtRuc)) {
            return "Ingrese su RUC...";
        } else if (textField.equals(vista.txtNombre)) {
            return "Ingrese sus Nombres y Apellidos...";
        } else if (textField.equals(vista.txtTelefono)) {
            return "Ingrese su Telefono...";
        } else if (textField.equals(vista.txtDireccion)) {
            return "Ingrese su Dirección...";
        } else if (textField.equals(vista.txtCorreo)) {
            return "Ingrese su Correo...";
        }
        return "";
    }

    // Método para inicializar los listeners
    private void initialize() {

        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGuardar) {
                    System.out.println("label Guardar presionado");  // Depuración
                    guardarProveedor();
                }
            }
        });
        this.vista.lblModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblModificar) {
                    System.out.println("label Modificar presionado");  // Depuración
                    SeleccionarProveedor();
                }
            }
        });
        this.vista.lblEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblEliminar) {
                    System.out.println("label Eliminar presionado");  // Depuración
                    eliminarProveedor();
                }
            }
        });
        this.vista.lblLimpiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblLimpiar) {
                    System.out.println("label Limpiar presionado");  // Depuración
                    limpiar();
                }
            }
        });
        this.vista.lblRegresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblRegresar) {
                    System.out.println("label Regresar presionado");  // Depuración
                    RegresarMenu();
                    vista.dispose();
                }
            }
        });

        // Agregar MouseListener a los campos de texto
        vista.txtRuc.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtRuc, "Ingrese su RUC...");
            }
        });

        vista.txtNombre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtNombre, "Ingrese sus Nombres y Apellidos...");
            }
        });

        vista.txtTelefono.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtTelefono, "Ingrese su Telefono...");
            }
        });

        vista.txtDireccion.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtDireccion, "Ingrese su Dirección...");
            }
        });

        vista.txtCorreo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtCorreo, "Ingrese su Correo...");
            }
        });

        this.vista.lblExcel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblExcel) {
                    System.out.println("label Excel presionado");  // Depuración
                    generarReporteExcel();  // Llamada al método para generar el reporte
                }
            }
        });

    }
}
