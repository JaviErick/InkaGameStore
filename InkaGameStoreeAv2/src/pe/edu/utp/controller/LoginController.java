package pe.edu.utp.controller;

import pe.edu.utp.vista.Login;
import pe.edu.utp.vista.MenuAdmin;
import pe.edu.utp.vista.MenuEmpleado;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import pe.edu.utp.dao.UsuarioDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Asistencia;
import pe.edu.utp.entity.Usuario;

public class LoginController {

    private UsuarioDao pdao;
    private Login vista;

    // Constructor del controlador
    public LoginController(UsuarioDao pdao, Login vista) {
        this.pdao = pdao;
        this.vista = vista;
        initialize();  // Vincula los eventos a los botones
    }

    // Método para iniciar la vista
    public void iniciar() {
        vista.setTitle("Login");
        vista.setLocationRelativeTo(null);
    }

    private void VerificarCredenciales() {
        List<Usuario> lista = pdao.readAllUsuario();
        String correo = vista.txtCorreo.getText();
        String contraseña = new String(vista.txtContraseñaa.getPassword());

        boolean usuarioEncontrado = false;

        for (Usuario usuario : lista) {
            if (usuario.getCorreo().equals(correo) && usuario.getContraseña().equals(contraseña)) {
                usuarioEncontrado = true;

                InicioSesionDTO usuarioDTO = new InicioSesionDTO(usuario.getUsuario_ID(), usuario.getNombreCompleto(), usuario.getRol());
                SesionUsuario.iniciarSesion(usuarioDTO);

                if (usuario.getRol().equals("Empleado")) {
                    System.out.println("Coincidencia encontrada: Empleado");
                    InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
                    MenuEmpleado m = new MenuEmpleado(usuarioLogeado);

                    m.setVisible(true);
                    vista.dispose();
                } else if (usuario.getRol().equals("Administrador")) {
                    System.out.println("Coincidencia encontrada: Administrador");
                    // Abrir la vista del menú de administrador
                    InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
                    MenuAdmin a = new MenuAdmin(usuarioLogeado);
                    a.setVisible(true);
                    vista.dispose();
                }
                break;
            }
        }
        if (!usuarioEncontrado) {
            JOptionPane.showMessageDialog(null, "Credenciales inválidas. Por favor, verifique su correo y contraseña.");
        }
    }

    private boolean mostrarContraseña = false;
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
        if (textField.equals(vista.txtCorreo)) {
            return "Ingrese su DNI...";
        } else if (textField.equals(vista.txtContraseñaa)) {
            return "************";
        }
        return "";
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtCorreo)) {
            textField.setText("Ingrese su Correo Electrónico...");
        } else if (textField.equals(vista.txtContraseñaa)) {
            textField.setText("************");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

    private void initialize() {
        System.out.println("Inicializando etiquetas y listeners...");
        this.vista.lblIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblIngresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblIngresar) {
                    System.out.println("Label Ingresar presionada");
                    VerificarCredenciales();
                }
            }
        });
        this.vista.lblMostrarContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));  // Cambiar el cursor
        this.vista.lblMostrarContraseña.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mostrarContraseña) {
                    // Ocultar la contraseña
                    vista.txtContraseñaa.setEchoChar('*');
                    mostrarContraseña = false;
                } else {
                    // Mostrar la contraseña
                    vista.txtContraseñaa.setEchoChar((char) 0);
                    mostrarContraseña = true;
                }
            }
        });

        // Agregar MouseListener a los campos de texto
        vista.txtCorreo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtCorreo, "Ingrese su Correo Electrónico...");
            }
        });

        vista.txtContraseñaa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtContraseñaa, "************");
            }
        });

    }

}
