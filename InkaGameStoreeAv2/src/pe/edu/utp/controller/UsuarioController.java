package pe.edu.utp.controller;

import pe.edu.utp.vista.GestorUsuarios;
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
import pe.edu.utp.entity.Usuario;
import pe.edu.utp.dao.UsuarioDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.service.ExcelUsuarios;

/**
 *
 * @author javie
 */
public class UsuarioController {

    private Usuario modelo;
    private UsuarioDao pdao;
    private GestorUsuarios vista;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;

    // Constructor del controlador
    public UsuarioController(Usuario modelo, UsuarioDao pdao, GestorUsuarios vista) {
        this.modelo = modelo;
        this.pdao = pdao;
        this.vista = vista;
        initialize();  // Vincula los eventos a los botones
    }

    private JTextField campoActual = null;

    private void RegresarMenu() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuAdmin m = new MenuAdmin(usuarioLogeado);
        m.show();
    }

    // Método para iniciar la vista
    public void iniciar() {
        vista.setTitle("Gestor de Empleado");
        vista.setLocationRelativeTo(null);
        vista.txtID.setEnabled(false);
        listar();  // Lista los clientes al inicio
    }

    // Método para eliminar un empleado seleccionado de la tabla
    private void eliminarUsuario() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Eliminando Empleado...");  // Depuración
            int fila = vista.tblUsuario.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int id = Integer.parseInt(vista.tblUsuario.getValueAt(fila, 0).toString());
                if (pdao.deleteUsuario(id)) {
                    JOptionPane.showMessageDialog(null, "Empleado Eliminado");
                    limpiarTabla();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                }
            }
        }
    }

    private void SeleccionarUsuario() {
        int selectedRow = vista.tblUsuario.getSelectedRow();
        if (selectedRow >= 0) {
            Integer id = Integer.parseInt(vista.tblUsuario.getValueAt(selectedRow, 0).toString());
            String rol = vista.tblUsuario.getValueAt(selectedRow, 1).toString();
            String dni = vista.tblUsuario.getValueAt(selectedRow, 2).toString();
            String nombres = vista.tblUsuario.getValueAt(selectedRow, 3).toString();
            String telefono = vista.tblUsuario.getValueAt(selectedRow, 4).toString();
            String correo = vista.tblUsuario.getValueAt(selectedRow, 5).toString();
            String contraseña = vista.tblUsuario.getValueAt(selectedRow, 6).toString();

            vista.txtID.setText(id.toString());
            vista.cbxRol.setSelectedItem(rol);
            vista.txtDNI.setText(dni);
            vista.txtNombre.setText(nombres);
            vista.txtTelefono.setText(telefono);
            vista.txtCorreo.setText(correo);
            vista.txtContraseña.setText(contraseña);

            vista.txtID.setForeground(Color.black);
            vista.txtDNI.setForeground(Color.black);
            vista.txtNombre.setForeground(Color.black);
            vista.txtTelefono.setForeground(Color.black);
            vista.txtCorreo.setForeground(Color.black);
            vista.txtContraseña.setForeground(Color.black);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar");
        }
    }

    // Método para modificar un cliente existente
    private void modificarUsuario() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Modificando Empleado...");  // Depuración
            modelo.setUsuario_ID(Integer.parseInt(vista.txtID.getText()));
            String rolSeleccionado = (String) vista.cbxRol.getSelectedItem();
            int rolID = rolSeleccionado.equals("Empleado") ? 1 : 2;
            modelo.setRol(String.valueOf(rolID));
            modelo.setDNI(vista.txtDNI.getText());
            modelo.setNombreCompleto(vista.txtNombre.getText());
            modelo.setTelefono(vista.txtTelefono.getText());
            modelo.setCorreo(vista.txtCorreo.getText());
            modelo.setContraseña(String.valueOf(vista.txtContraseña.getPassword()));

            if (pdao.updateUsuario(modelo)) {
                JOptionPane.showMessageDialog(null, "Empleado Modificado");
                limpiar(); // Limpia el formulario después de modificar
                limpiarTabla();
                listar(); // Actualiza la tabla para mostrar los cambios
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
            }
        }
    }

    private void generarReporteExcel() {
        // Obtener lista de clientes
        List<Usuario> listaUsuarios = pdao.readAllUsuario();  // Obtiene todos los clientes desde ClientesDao
        if (!listaUsuarios.isEmpty()) {
            // Llamar al servicio para generar el reporte
            ExcelUsuarios.generarReporteUsuarios(listaUsuarios);
        } else {
            JOptionPane.showMessageDialog(null, "No hay clientes para generar el reporte.");
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiar() {
        System.out.println("Limpiando formulario...");  // Depuración
        vista.txtDNI.setText("Ingrese su DNI...");
        vista.txtDNI.setForeground(new Color(153, 153, 153)); // Color del placeholder

        vista.txtNombre.setText("Ingrese sus Nombres y Apellidos...");
        vista.txtNombre.setForeground(new Color(153, 153, 153));

        vista.txtTelefono.setText("Ingrese su Telefono...");
        vista.txtTelefono.setForeground(new Color(153, 153, 153));

        vista.txtCorreo.setText("Ingrese su Correo...");
        vista.txtCorreo.setForeground(new Color(153, 153, 153));

        vista.txtContraseña.setText("************");
        vista.txtContraseña.setForeground(new Color(153, 153, 153));

        vista.txtID.setText("AutoIncrementable");
    }

    // Método para limpiar la tabla de Proveedores
    private void limpiarTabla() {
        System.out.println("Limpiando tabla de Empleado...");  // Depuración
        dtmodel.setRowCount(0);
    }

    private void listar() {
        System.out.println("Listando Usuarios...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblUsuario.getModel();
        vista.tblUsuario.setModel(dtmodel);
        List<Usuario> lista = pdao.readAllUsuario();
        Object[] objeto = new Object[7];  // Ajusta según los atributos de tu entidad Clientes
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getUsuario_ID();
            objeto[1] = lista.get(i).getRol();
            objeto[2] = lista.get(i).getDNI();
            objeto[3] = lista.get(i).getNombreCompleto();
            objeto[4] = lista.get(i).getTelefono();
            objeto[5] = lista.get(i).getCorreo();
            objeto[6] = lista.get(i).getContraseñaOculta();
            dtmodel.addRow(objeto);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < vista.tblUsuario.getColumnCount(); i++) {
            vista.tblUsuario.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        for (int i = 0; i < vista.tblUsuario.getColumnCount(); i++) {
            vista.tblUsuario.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        vista.tblUsuario.setModel(dtmodel);
    }

    // Método para guardar
    private void guardarUsuario() {
        String codigoBuscar = vista.txtID.getText().trim();
        boolean encontrado = false;

        // Recorrer las filas de la tabla para comprobar si el código ya existe
        for (int i = 0; i < vista.tblUsuario.getRowCount(); i++) {
            Object valorCelda = vista.tblUsuario.getValueAt(i, 0); // Cambia 0 por el índice de la columna que necesites
            // Comparar el valor de la celda con el código buscado
            if (codigoBuscar.equals(valorCelda.toString())) {
                encontrado = true;
                modificarUsuario(); // Llama a modificarUsuario si el empleado existe
                limpiar(); // Limpia después de modificar
                break;
            }
        }

        if (!encontrado) {
            try {
                System.out.println("Guardando Empleado...");
                String rolSeleccionado = (String) vista.cbxRol.getSelectedItem();
                int rolID = rolSeleccionado.equals("Empleado") ? 1 : 2; // Establece el rol

                modelo.setRol(String.valueOf(rolID));
                modelo.setDNI(vista.txtDNI.getText());
                modelo.setNombreCompleto(vista.txtNombre.getText());
                modelo.setTelefono(vista.txtTelefono.getText());
                modelo.setCorreo(vista.txtCorreo.getText());
                modelo.setContraseña(String.valueOf(vista.txtContraseña.getPassword()));

                if (pdao.createUsuario(modelo)) {
                    JOptionPane.showMessageDialog(null, "Empleado Guardado");
                    limpiar();
                    limpiarTabla();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error en la conversión de números: " + e.getMessage());
            }
        }
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

    private String getPlaceholderText(JTextField textField) {
        if (textField.equals(vista.txtDNI)) {
            return "Ingrese su DNI...";
        } else if (textField.equals(vista.txtNombre)) {
            return "Ingrese sus Nombres y Apellidos...";
        } else if (textField.equals(vista.txtTelefono)) {
            return "Ingrese su Telefono...";
        } else if (textField.equals(vista.txtCorreo)) {
            return "Ingrese su Correo...";
        } else if (textField.equals(vista.txtContraseña)) {
            return "************";
        }
        return "";
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtDNI)) {
            textField.setText("Ingrese su DNI...");
        } else if (textField.equals(vista.txtNombre)) {
            textField.setText("Ingrese sus Nombres y Apellidos...");
        } else if (textField.equals(vista.txtTelefono)) {
            textField.setText("Ingrese su Telefono...");
        } else if (textField.equals(vista.txtCorreo)) {
            textField.setText("Ingrese su Correo...");
        } else if (textField.equals(vista.txtContraseña)) {
            textField.setText("************");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

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
                    guardarUsuario();
                }
            }
        });
        this.vista.lblModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblModificar) {
                    System.out.println("label Modificar presionado");  // Depuración
                    SeleccionarUsuario();
                }
            }
        });
        this.vista.lblEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblEliminar) {
                    System.out.println("label Eliminar presionado");  // Depuración
                    eliminarUsuario();
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
        vista.txtDNI.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtDNI, "Ingrese su DNI...");
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

        vista.txtCorreo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtCorreo, "Ingrese su Correo...");
            }
        });

        vista.txtContraseña.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtContraseña, "************");
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
