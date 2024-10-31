package pe.edu.utp.controller;

import pe.edu.utp.vista.GestorClientes;
import pe.edu.utp.vista.MenuAdmin;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.ClientesDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Clientes;

public class ClientesController {

    private Clientes modelo;
    private ClientesDao pdao;
    private GestorClientes vista;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;
    private JTextField campoActual = null;

    // Constructor del controlador
    public ClientesController(Clientes modelo, ClientesDao pdao, GestorClientes vista) {
        this.modelo = modelo;
        this.pdao = pdao;
        this.vista = vista;
        initialize();  // Vincula los eventos a los botones
    }

    // Método para iniciar la vista
    public void iniciar() {
        vista.setTitle("Gestor de Clientes");
        vista.setLocationRelativeTo(null);
        vista.txtID.setEnabled(false);
        listar();

    }

    private void RegresarMenu() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuAdmin m = new MenuAdmin(usuarioLogeado);
        m.show();
    }

    // Método para guardar un cliente en la base de datos
    private void guardarCliente() {
        String codigoBuscar = vista.txtID.getText().trim();
        boolean encontrado = false;
        // Recorrer las filas de la tabla para comprobar si el código ya existe
        for (int i = 0; i < vista.tblClientes.getRowCount(); i++) {
            Object valorCelda = vista.tblClientes.getValueAt(i, 0);  // Cambia 0 por el índice de la columna que necesites
            // Comparar el valor de la celda con el código buscado
            if (codigoBuscar.equals(valorCelda.toString())) {
                encontrado = true;
                modificarCliente();
                limpiar();
                break;
            }
        }

        // Si el código no se encontró en la tabla, agregar un nuevo producto
        if (!encontrado) {
            try {
                System.out.println("Guardando cliente...");  // Depuración
                modelo.setDNI(vista.txtDni.getText());
                modelo.setNombreCompleto(vista.txtNombres.getText());
                modelo.setTelefono(vista.txtTelefono.getText());
                modelo.setDireccion(vista.txtDireccion.getText());

                if (pdao.createClientes(modelo)) {
                    JOptionPane.showMessageDialog(null, "Cliente Guardado");
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

    // Método para modificar un cliente existente
    private void modificarCliente() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Modificando cliente...");  // Depuración
            modelo.setDNI(vista.txtDni.getText());
            modelo.setNombreCompleto(vista.txtNombres.getText());
            modelo.setTelefono(vista.txtTelefono.getText());
            modelo.setDireccion(vista.txtDireccion.getText());
            modelo.setCliente_ID(Integer.parseInt(vista.txtID.getText()));

            if (pdao.updateClientes(modelo)) {
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                limpiar();
                limpiarTabla();
                listar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
            }
        }
    }

    // Método para eliminar un cliente seleccionado de la tabla
    private void eliminarCliente() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar a este cliente?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Eliminando cliente...");  // Depuración
            int fila = vista.tblClientes.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int id = Integer.parseInt(vista.tblClientes.getValueAt(fila, 0).toString());
                if (pdao.deleteClientes(id)) {
                    JOptionPane.showMessageDialog(null, "Cliente Eliminado");
                    limpiarTabla();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                }
            }
        }
    }

    private void SeleccionarCliente() {
        int selectedRow = vista.tblClientes.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.parseInt(vista.tblClientes.getValueAt(selectedRow, 0).toString());
            String dni = vista.tblClientes.getValueAt(selectedRow, 1).toString();
            String nombre = vista.tblClientes.getValueAt(selectedRow, 2).toString();
            String telefono = vista.tblClientes.getValueAt(selectedRow, 3).toString();
            String direccion = vista.tblClientes.getValueAt(selectedRow, 4).toString();

            vista.txtID.setText(id.toString());
            vista.txtDni.setText(dni);
            vista.txtNombres.setText(nombre);
            vista.txtTelefono.setText(telefono);
            vista.txtDireccion.setText(direccion);

            vista.txtID.setForeground(Color.black);
            vista.txtDni.setForeground(Color.black);
            vista.txtNombres.setForeground(Color.black);
            vista.txtTelefono.setForeground(Color.black);
            vista.txtDireccion.setForeground(Color.black);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar");
        }

    }

    // Método para limpiar los campos del formulario
    private void limpiar() {
        System.out.println("Limpiando formulario...");  // Depuración
        vista.txtDni.setText("Ingrese su DNI...");
        vista.txtDni.setForeground(new Color(153, 153, 153)); // Color del placeholder

        vista.txtNombres.setText("Ingrese sus Nombres y Apellidos...");
        vista.txtNombres.setForeground(new Color(153, 153, 153));

        vista.txtTelefono.setText("Ingrese su Telefono...");
        vista.txtTelefono.setForeground(new Color(153, 153, 153));

        vista.txtDireccion.setText("Ingrese su Direccion...");
        vista.txtDireccion.setForeground(new Color(153, 153, 153));

        vista.txtID.setText("AutoIncrementable");
    }

    // Método para limpiar la tabla de clientes
    private void limpiarTabla() {
        System.out.println("Limpiando tabla de clientes...");  // Depuración
        dtmodel.setRowCount(0);
    }

    // Método para listar todos los clientes en la tabla
    public void listar() {
        System.out.println("Listando clientes...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblClientes.getModel();
        vista.tblClientes.setModel(dtmodel);
        List<Clientes> lista = pdao.readAllClientes();
        Object[] objeto = new Object[6];  // Ajusta según los atributos de tu entidad Clientes
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getCliente_ID();
            objeto[1] = lista.get(i).getNombreCompleto();
            objeto[2] = lista.get(i).getTelefono();
            objeto[3] = lista.get(i).getDireccion();
            objeto[4] = lista.get(i).getDNI();
            dtmodel.addRow(objeto);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < vista.tblClientes.getColumnCount(); i++) {
            vista.tblClientes.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblClientes.getColumnCount(); i++) {
            vista.tblClientes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        vista.tblClientes.setModel(dtmodel);
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
        if (textField.equals(vista.txtDni)) {
            textField.setText("Ingrese su DNI...");
        } else if (textField.equals(vista.txtNombres)) {
            textField.setText("Ingrese sus Nombres y Apellidos...");
        } else if (textField.equals(vista.txtTelefono)) {
            textField.setText("Ingrese su Telefono...");
        } else if (textField.equals(vista.txtDireccion)) {
            textField.setText("Ingrese su Dirección...");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

    private String getPlaceholderText(JTextField textField) {
        if (textField.equals(vista.txtDni)) {
            return "Ingrese su DNI...";
        } else if (textField.equals(vista.txtNombres)) {
            return "Ingrese sus Nombres y Apellidos...";
        } else if (textField.equals(vista.txtTelefono)) {
            return "Ingrese su Telefono...";
        } else if (textField.equals(vista.txtDireccion)) {
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

        this.vista.lblGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGuardar) {
                    System.out.println("label Guardar presionado");  // Depuración
                    guardarCliente();
                }
            }
        });
        this.vista.lblModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblModificar) {
                    System.out.println("label Modificar presionado");  // Depuración
                    SeleccionarCliente();
                }
            }
        });
        this.vista.lblEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblEliminar) {
                    System.out.println("label Eliminar presionado");  // Depuración
                    eliminarCliente();
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
        vista.txtDni.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtDni, "Ingrese su DNI...");
            }
        });

        vista.txtNombres.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtNombres, "Ingrese sus Nombres y Apellidos...");
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

    }
}
