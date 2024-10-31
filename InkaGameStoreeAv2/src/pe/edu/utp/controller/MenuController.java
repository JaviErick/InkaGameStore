package pe.edu.utp.controller;

import pe.edu.utp.vista.BuscadordProductos;
import pe.edu.utp.vista.GestorClientes;
import pe.edu.utp.vista.GestorPedidos;
import pe.edu.utp.vista.GestorProductos;
import pe.edu.utp.vista.GestorProveedores;
import pe.edu.utp.vista.GestorUsuarios;
import pe.edu.utp.vista.GestorVentas;
import pe.edu.utp.vista.Historial;
import pe.edu.utp.vista.ListaProductosProveedores;
import pe.edu.utp.vista.Login;
import pe.edu.utp.vista.MenuAdmin;
import pe.edu.utp.vista.MenuEmpleado;
import pe.edu.utp.vista.RegistrodClientes;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import pe.edu.utp.dao.UsuarioDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Asistencia;

public class MenuController {

    private MenuAdmin vista;
    private Asistencia modelo;
    private UsuarioDao pdao;
    private MenuEmpleado vista2;

    public MenuController(MenuAdmin vista, Asistencia modelo, UsuarioDao pdao) {
        this.vista = vista;
        this.modelo = modelo;
        this.pdao = pdao;
        initialize();
    }

    public MenuController(MenuEmpleado vista2, Asistencia modelo, UsuarioDao pdao) {
        this.vista2 = vista2;
        this.modelo = modelo;
        this.pdao = pdao;
        initialize2();
    }

    // Método para iniciar la vista
    public void iniciar() {
        vista.setTitle("MenuAdmin");
        vista.setLocationRelativeTo(null);
        regitrarEntrada();
    }

    // Método para iniciar la vista
    public void iniciar2() {
        vista2.setTitle("MenuEmpleado");
        vista2.setLocationRelativeTo(null);
        regitrarEntrada();
    }

    private void regitrarEntrada() {
        System.out.println("Asistencia Registrada");

        int usuarioID = SesionUsuario.getIdLogeado();
        String fecha = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        String horaentrada = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String horasalida = null;

        modelo.setUsuarioID(usuarioID);
        modelo.setFecha(fecha);
        modelo.setHoraIngreso(horaentrada);
        modelo.setHoraSalida(horasalida);

        String asistenciaID = pdao.registroEntrada(modelo);

        if (asistenciaID != null) {
            modelo.setAsistenciaID(Integer.parseInt(asistenciaID));
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la entrada");
        }
    }

    private void registrarSalida() {
        System.out.println("Registrando salida...");
        int asistenciaID = modelo.getAsistenciaID();
        if (asistenciaID == 0) {
            JOptionPane.showMessageDialog(null, "Error: ID de asistencia no encontrado.");
            return;
        }
        String horasalida = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        boolean salidaExitosa = pdao.registroSalida(asistenciaID, horasalida);

        if (salidaExitosa) {
            JOptionPane.showMessageDialog(null, "SALIDA REGISTRADA CORRECTAMENTE.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la salida.");
        }
    }

    private void initialize2() {
        this.vista2.lblBuscarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista2.lblHistorial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista2.lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        this.vista2.lblBuscarPro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista2.lblBuscarPro) {
                    System.out.println("Label Rclientes presionada");
                    BuscadordProductos();
                }
            }
        });
        this.vista2.lblHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista2.lblHistorial) {
                    System.out.println("Label Rclientes presionada");
                    Historial();
                }
            }
        });

        this.vista2.lblSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista2.lblSalir) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿DESEA CERRAR SESIÓN? Recuerda que tu hora de salida se marcara automaticamente...", "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        CerrarSesion2();
                    }
                }
            }
        });
    }

    private void initialize() {
        System.out.println("Inicializando etiquetas y listeners...");
        this.vista.lblGclientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGproductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGusuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGproveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGpedidos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGventas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGproProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblGcerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblGclientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGclientes) {
                    System.out.println("Label clientes presionada");
                    GestorClientes();
                }
            }
        });
        this.vista.lblGproductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGproductos) {
                    System.out.println("Label productos presionada");
                    GestorProductos();
                }
            }
        });
        this.vista.lblGusuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGusuarios) {
                    System.out.println("Label usuarios presionada");
                    GestorUsuarios();
                }
            }
        });
        this.vista.lblGproveedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGproveedores) {
                    System.out.println("Label proveedores presionada");
                    GestorProveedores();
                }
            }
        });
        this.vista.lblGpedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGpedidos) {
                    System.out.println("Label pedidos presionada");
                    GestorPedidos();
                }
            }
        });
        this.vista.lblGventas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGventas) {
                    System.out.println("Label ventas presionada");
                    GestorVentas();
                }
            }
        });
        this.vista.lblGproProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGproProveedor) {
                    System.out.println("Label proProveedores presionada");
                    ListaProductosProveedores();
                }
            }
        });
        this.vista.lblGcerrarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGcerrarSesion) {
                    System.out.println("Label cerrar sesion presionada");
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿DESEA CERRAR SESIÓN? Recuerda que tu hora de salida se marcara automaticamente...", "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        CerrarSesion();
                    }
                }
            }
        });
    }

    private void GestorClientes() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        // Crear la vista y pasar el usuario logeado
        GestorClientes g = new GestorClientes(usuarioLogeado);
        g.show();
        vista.dispose();
    }

    private void GestorProductos() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        GestorProductos g = new GestorProductos(usuarioLogeado);
        g.show();
        vista.dispose();
    }

    private void GestorUsuarios() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        GestorUsuarios g = new GestorUsuarios(usuarioLogeado);
        g.show();
        vista.dispose();
    }

    private void GestorProveedores() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        GestorProveedores g = new GestorProveedores(usuarioLogeado);
        g.show();
        vista.dispose();
    }

    private void GestorPedidos() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        GestorPedidos g = new GestorPedidos(usuarioLogeado);
        g.show();
        vista.dispose();
    }

    private void GestorVentas() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        GestorVentas g = new GestorVentas(usuarioLogeado);
        g.show();
        vista.dispose();
    }

    private void ListaProductosProveedores() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        ListaProductosProveedores g = new ListaProductosProveedores(usuarioLogeado);
        g.show();
        vista.dispose();
    }

    private void CerrarSesion() {
        SesionUsuario.cerrarSesion();
        Login a = new Login();
        a.show();
        vista.dispose();
    }

    private void CerrarSesion2() {
        registrarSalida();
        SesionUsuario.cerrarSesion();
        Login a = new Login();
        a.show();
        vista2.dispose();
    }

    private void BuscadordProductos() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        BuscadordProductos a = new BuscadordProductos(usuarioLogeado);
        a.show();
        vista2.dispose();
    }

    private void Historial() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        Historial a = new Historial(usuarioLogeado);
        a.show();
        vista2.dispose();
    }

}
