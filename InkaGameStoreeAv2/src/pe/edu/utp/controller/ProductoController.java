package pe.edu.utp.controller;

import pe.edu.utp.vista.GestorProductos;
import pe.edu.utp.vista.MenuAdmin;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.ProductoDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Productos;

public class ProductoController implements Runnable {

    String hora, minuto, segundo;
    Thread hilo;
    private Productos modelo;
    private ProductoDao pdao;
    private GestorProductos vista;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;

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
        while (current == hilo) {
            Hora();
            vista.lblHora.setText(hora + ":" + minuto + ":" + segundo);
        }
    }

    // Constructor del controlador
    public ProductoController(Productos modelo, ProductoDao pdao, GestorProductos vista) {
        this.modelo = modelo;
        this.pdao = pdao;
        this.vista = vista;
        initialize();  // Vincula los eventos a los botones
    }

    public void llenarComboBoxCategorias() {
        List<String> categorias = pdao.getCategorias();

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
        List<String> proveedores = pdao.getProveedores();

        if (proveedores.isEmpty()) {
            System.out.println("No se encontraron Proveedores");
        } else {
            vista.cbxProveedor.removeAllItems();
            for (String proveedor : proveedores) {
                vista.cbxProveedor.addItem(proveedor);
            }
        }
    }

    private void guardarProducto() {
        String codigoBuscar = vista.txtID.getText().trim();
        boolean encontrado = false;
        for (int i = 0; i < vista.tblProductos.getRowCount(); i++) {
            Object valorCelda = vista.tblProductos.getValueAt(i, 0);

            if (codigoBuscar.equals(valorCelda.toString())) {
                encontrado = true;
                modificarProducto();
                limpiar();
                break;
            }
        }
        if (!encontrado) {
            try {
                System.out.println("Guardando Producto...");
                String categoriaSeleccionadaNombre = (String) vista.cbxCategoria.getSelectedItem();
                if (categoriaSeleccionadaNombre != null) {
                    int categoriaId = pdao.ObtenerIDCategoria(categoriaSeleccionadaNombre);
                    if (categoriaId != -1) {
                        modelo.setCategoria(String.valueOf(categoriaId));
                    } else {
                        JOptionPane.showMessageDialog(null, "Categoría no encontrada.");
                        return;
                    }
                }
                String ProveedorSeleccionadoNombre = (String) vista.cbxProveedor.getSelectedItem();
                if (ProveedorSeleccionadoNombre != null) {
                    int proveedorId = pdao.ObtenerIDProveedor(ProveedorSeleccionadoNombre);
                    if (proveedorId != -1) {
                        modelo.setProveedor(String.valueOf(proveedorId));
                    } else {
                        JOptionPane.showMessageDialog(null, "Categoría no encontrada.");
                        return;
                    }
                }
                modelo.setNombreProducto(vista.txtNombre.getText());
                modelo.setPrecio(Integer.parseInt(vista.txtPrecio.getText()));
                modelo.setStock(Integer.parseInt(vista.txtStock.getText()));
                modelo.setDescripcion(vista.txtDescripcion.getText());
                modelo.setFechaHoraAgregada(String.valueOf(vista.lblFecha.getText() + " " + vista.lblHora.getText()));
                modelo.setImagenRuta(vista.txtImagenRuta.getText());

                if (pdao.createProducto(modelo)) {
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

    private void eliminarProducto() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar este Producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Eliminando Producto...");  // Depuración
            int fila = vista.tblProductos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila");
            } else {
                int id = Integer.parseInt(vista.tblProductos.getValueAt(fila, 0).toString());
                if (pdao.deleteProducto(id)) {
                    JOptionPane.showMessageDialog(null, "Producto Eliminado");
                    limpiarTabla();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Eliminar");
                }
            }
        }
    }

    private void seleccionarProducto() {
        int selectedRow = vista.tblProductos.getSelectedRow();
        if (selectedRow >= 0) {
            // Obtén los valores de la fila seleccionada
            Integer id = Integer.parseInt(vista.tblProductos.getValueAt(selectedRow, 0).toString());
            String nombreProducto = vista.tblProductos.getValueAt(selectedRow, 1).toString();
            String categoria = vista.tblProductos.getValueAt(selectedRow, 2).toString();
            String proveedor = vista.tblProductos.getValueAt(selectedRow, 3).toString();
            String precio = vista.tblProductos.getValueAt(selectedRow, 4).toString();
            String stock = vista.tblProductos.getValueAt(selectedRow, 5).toString();
            String descripcion = vista.tblProductos.getValueAt(selectedRow, 7).toString();
            String imagenRuta = vista.tblProductos.getValueAt(selectedRow, 8).toString();

            // Asigna los valores a los campos de la vista
            vista.txtID.setText(id.toString());
            vista.txtNombre.setText(nombreProducto);
            vista.cbxCategoria.setSelectedItem(categoria);
            vista.cbxProveedor.setSelectedItem(proveedor);
            vista.txtPrecio.setText(precio);
            vista.txtStock.setText(stock);
            vista.txtDescripcion.setText(descripcion);
            vista.txtImagenRuta.setText(imagenRuta);
            try {
                Image foto = Toolkit.getDefaultToolkit().getImage(imagenRuta);
                foto = foto.getScaledInstance(190, 130, Image.SCALE_DEFAULT);
                vista.lblImagen.setIcon(new ImageIcon(foto));
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
            }

            // Cambia el color del texto para que no parezca un placeholder
            vista.txtID.setForeground(Color.black);
            vista.txtNombre.setForeground(Color.black);
            vista.txtPrecio.setForeground(Color.black);
            vista.txtStock.setForeground(Color.black);
            vista.txtDescripcion.setForeground(Color.black);
            vista.txtImagenRuta.setForeground(Color.black);

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar");
        }
    }

    private void modificarProducto() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Modificando Producto...");  // Depuración
            modelo.setProducto_ID(Integer.parseInt(vista.txtID.getText()));  // Se obtiene el ID del producto
            modelo.setNombreProducto(vista.txtNombre.getText());  // Se obtiene el nombre del producto
            String categoriaSeleccionadaNombre = (String) vista.cbxCategoria.getSelectedItem();
            if (categoriaSeleccionadaNombre != null) {
                int categoriaId = pdao.ObtenerIDCategoria(categoriaSeleccionadaNombre);
                if (categoriaId != -1) {
                    modelo.setCategoria(String.valueOf(categoriaId));
                } else {
                    JOptionPane.showMessageDialog(null, "Categoría no encontrada.");
                    return;
                }
            }
            String ProveedorSeleccionadoNombre = (String) vista.cbxProveedor.getSelectedItem();
            if (ProveedorSeleccionadoNombre != null) {
                int proveedorId = pdao.ObtenerIDProveedor(ProveedorSeleccionadoNombre);
                if (proveedorId != -1) {
                    modelo.setProveedor(String.valueOf(proveedorId));
                } else {
                    JOptionPane.showMessageDialog(null, "Categoría no encontrada.");
                    return;
                }
            }
            modelo.setPrecio(Double.parseDouble(vista.txtPrecio.getText()));
            modelo.setStock(Integer.parseInt(vista.txtStock.getText()));
            modelo.setDescripcion(vista.txtDescripcion.getText());
            modelo.setImagenRuta(vista.txtImagenRuta.getText());

            if (pdao.updateProducto(modelo)) {
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                limpiar();
                limpiarTabla();
                listar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar el Producto");
            }
        }

    }

    private void inspeccionar() {
        //Creamos una instancia
        JFileChooser archivo = new JFileChooser();
        //Creamos un objeto File que representa el directorio predeterminado
        File ruta = new File("C:\\Users\\AMD\\Pictures");
        //Establece el nuevo directorio 
        archivo.setCurrentDirectory(ruta);

        int ventana = archivo.showOpenDialog(null);
        if (ventana == JFileChooser.APPROVE_OPTION) {
            //Asignamos el archivo a la variable
            File file = archivo.getSelectedFile();
            //Establece la ruta del archivo seleccionado como cadena
            vista.txtImagenRuta.setText(String.valueOf(file));
            //Se carga la imagen desde la ruta
            Image foto = Toolkit.getDefaultToolkit().getImage(vista.txtImagenRuta.getText());
            //Asiganmos el tama{o
            foto = foto.getScaledInstance(190, 130, Image.SCALE_DEFAULT);
            //Se crea un icon con
            vista.lblImagen.setIcon(new ImageIcon(foto));
        }
    }

    public class ImageRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Verifica si el valor es una imagen
            if (value instanceof JLabel) {
                // Si es una imagen, muestra la imagen
                JLabel lbl = (JLabel) value;
                return lbl;
            } else {
                // Si no es una imagen, usa el comportamiento normal
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }
    }

    private void listar() {
        vista.tblProductos.setDefaultRenderer(Object.class, new ImageRenderer());
        System.out.println("Listando Productos...");  // Depuración
        dtmodel = (DefaultTableModel) vista.tblProductos.getModel();
        dtmodel.setRowCount(0);
        List<Productos> lista = pdao.readAllProductos();
        for (Productos producto : lista) {
            Object[] objeto = new Object[10];
            objeto[0] = producto.getProducto_ID();
            objeto[1] = producto.getCategoria();
            objeto[2] = producto.getNombreProducto();
            objeto[3] = producto.getProveedor();
            objeto[4] = producto.getPrecio();
            objeto[5] = producto.getStock();
            objeto[6] = producto.getFechaHoraAgregada();
            objeto[7] = producto.getDescripcion();
            objeto[8] = producto.getImagenRuta();
            try {
                Icon imagenIcon = producto.getImagen();
                if (imagenIcon instanceof ImageIcon) {
                    Image imagen = ((ImageIcon) imagenIcon).getImage();
                    Image imagenEscalada = imagen.getScaledInstance(90, 60, Image.SCALE_SMOOTH);
                    objeto[9] = new JLabel(new ImageIcon(imagenEscalada));
                } else {
                    objeto[9] = new JLabel("Sin imagen");
                }
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
                objeto[9] = new JLabel("Error imagen");
            }
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < vista.tblProductos.getColumnCount(); i++) {
                vista.tblProductos.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            }
            for (int i = 0; i < vista.tblProductos.getColumnCount(); i++) {
                vista.tblProductos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            dtmodel.addRow(objeto);
        }
        vista.tblProductos.setRowHeight(70);
        vista.tblProductos.setModel(dtmodel);
    }

    private void limpiarTabla() {
        System.out.println("Limpiando tabla de Empleado...");  // Depuración
        dtmodel.setRowCount(0);
    }

    private void regresar() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        MenuAdmin a = new MenuAdmin(usuarioLogeado);
        a.show();
        vista.dispose();
    }

    private void limpiar() {
        System.out.println("Limpiando formulario...");  // Depuración
        vista.txtNombre.setText("Ingrese el Nombre del Producto...");
        vista.txtNombre.setForeground(new Color(153, 153, 153)); // Color del placeholder

        vista.txtPrecio.setText("Ingrese el Precio...");
        vista.txtPrecio.setForeground(new Color(153, 153, 153));

        vista.txtStock.setText("Ingrese el Stock...");
        vista.txtStock.setForeground(new Color(153, 153, 153));

        vista.txtImagenRuta.setText("Ingrese la Ruta...");
        vista.txtImagenRuta.setForeground(new Color(153, 153, 153));

        vista.lblImagen.setIcon(null);
        vista.txtDescripcion.setText("");

        vista.txtID.setText("AutoIncrementable**");
    }

    public void iniciar() {
        vista.setTitle("Gestor de Productos");
        vista.setLocationRelativeTo(null);
        vista.txtID.setEnabled(false);
        llenarComboBoxCategorias();
        llenarComboBoxProveedores();
        vista.lblFecha.setText(fecha());
        hilo = new Thread(this);
        hilo.start();
        listar();
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
        if (textField.equals(vista.txtID)) {
            return "AutoIncrementable**";
        } else if (textField.equals(vista.txtNombre)) {
            return "Ingrese el Nombre del Producto...";
        } else if (textField.equals(vista.txtPrecio)) {
            return "Ingrese el Precio...";
        } else if (textField.equals(vista.txtStock)) {
            return "Ingrese el Stock...";
        } else if (textField.equals(vista.txtImagenRuta)) {
            return "Ingrese la Ruta...";
        }
        return "";
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtID)) {
            textField.setText("AutoIncrementable**");
        } else if (textField.equals(vista.txtNombre)) {
            textField.setText("Ingrese el Nombre del Producto...");
        } else if (textField.equals(vista.txtPrecio)) {
            textField.setText("Ingrese el Precio...");
        } else if (textField.equals(vista.txtStock)) {
            textField.setText("Ingrese el Stock...");
        } else if (textField.equals(vista.txtImagenRuta)) {
            textField.setText("Ingrese la Ruta...");
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
        this.vista.lblInspeccionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblGuardar) {
                    System.out.println("label Guardar presionado");  // Depuración
                    guardarProducto();
                }
            }
        });
        this.vista.lblModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblModificar) {
                    System.out.println("label Modificar presionado");  // Depuración
                    seleccionarProducto();
                }
            }
        });
        this.vista.lblEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblEliminar) {
                    System.out.println("label Eliminar presionado");  // Depuración
                    eliminarProducto();
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
                    regresar();
                }
            }
        });
        this.vista.lblInspeccionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblInspeccionar) {
                    System.out.println("label Inspeccionar presionado");  // Depuración
                    inspeccionar();
                }
            }
        });

        // Agregar MouseListener a los campos de texto
        vista.txtNombre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtNombre, "Ingrese el Nombre del Producto...");
            }
        });

        vista.txtPrecio.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtPrecio, "Ingrese el Precio...");
            }
        });

        vista.txtStock.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtStock, "Ingrese el Stock...");
            }
        });

        vista.txtImagenRuta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtImagenRuta, "Ingrese la Ruta...");
            }
        });

    }
}
