package pe.edu.utp.controller;

import pe.edu.utp.vista.BuscadordProductos;
import pe.edu.utp.vista.Carrito;
import pe.edu.utp.vista.RegistrodClientes;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import pe.edu.utp.dao.ClientesDao;
import pe.edu.utp.dao.VentaDao;
import pe.edu.utp.daoImpl.ClientesDaoImpl;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;
import pe.edu.utp.entity.Carritoo;
import pe.edu.utp.entity.Clientes;
import pe.edu.utp.entity.DetalleVenta;
import pe.edu.utp.entity.Venta;

public class CarritoController implements Runnable {

    private Carritoo modelo;
    private Venta modelo2;
    private DetalleVenta modelo3;
    private VentaDao pdao;
    private ClientesDao pdao2;
    private Carrito vista;
    private DefaultTableModel dtmodel = new DefaultTableModel();
    private JTable tabla;
    String hora, minuto, segundo;
    Thread hilo;
    private List<Carritoo> carrito;

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
        while (current == hilo) { // Verifica ambos hilos
            Hora();
            if (current == hilo && vista != null) {
                vista.lblHora.setText(hora + ":" + minuto + ":" + segundo);
            }
        }
    }

    public CarritoController(List<Carritoo> carritoExistente, Venta modelo2, DetalleVenta modelo3, Carritoo modelo, ClientesDao pdao2, VentaDao pdao, Carrito vista) {
        this.modelo = modelo;
        this.modelo2 = modelo2;
        this.modelo3 = modelo3;
        this.carrito = carritoExistente;
        this.pdao = pdao;
        this.pdao2 = pdao2;
        this.vista = vista;
        initialize();
    }

    public void iniciar() {
        vista.setTitle("Carrito");
        vista.setLocationRelativeTo(null);
        vista.lblFecha.setText(fecha());
        hilo = new Thread(this);
        hilo.start();
    }

    private void NuevoCliente() {
        Clientes modeloCliente = new Clientes();
        ClientesDao daoCliente = new ClientesDaoImpl();
        RegistrodClientes vistaRegistrodClientes = new RegistrodClientes(SesionUsuario.getUsuarioLogeado());
        RegistroClienteController RegistroController = new RegistroClienteController(modeloCliente, daoCliente, vistaRegistrodClientes);
        RegistroController.iniciar();
        vistaRegistrodClientes.setVisible(true);
        vista.dispose();
    }


    private void NuevoProducto() {
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        BuscadordProductos c = new BuscadordProductos(usuarioLogeado);
        c.show();
        vista.dispose();
    }

    private void crearPDF() {
        try {
            FileOutputStream archivo;
            File file = new File("src/pdf/");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance("src/pe/edu/utp/assets/negro inka.png");
            img.scalePercent(300);

            img.setAlignment(Element.ALIGN_CENTER);
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            LocalTime horaActual = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaFormateada = horaActual.format(formatter);

            fecha.add("Factura \n"
                    + "\nFecha: " + new SimpleDateFormat("YYYY-MM-dd").format(date) + "\n"
                    + "\nHora: " + horaFormateada + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{60f, 5f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_CENTER);

            Encabezado.addCell(img);

            String ruc = "20100049181";
            String nom = "InkaGameStore";
            String tel = "966 205 226";
            String dir = "Av. Antunez de Mayolo 1173, Los Olivos";
            String dir2 = "Av La Mar 2291, Tda 50, San Miguel";
            String rs = "inkagamestore@gmail.com";

            Encabezado.addCell("");
            Encabezado.addCell("\nRUC: " + ruc + "\n\nNombre: " + nom + "\n\nTeléfono: " + tel + "\n\nDirección: \n\n" + dir + "\n\n" + dir2 + "\n\nCorreo: " + rs);
            Encabezado.addCell(fecha);
            Encabezado.addCell(horaFormateada);
            doc.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("Datos del cliente: " + "\n\n");
            doc.add(cli);

            PdfPTable tablacli = new PdfPTable(2);
            tablacli.setWidthPercentage(100);
            tablacli.getDefaultCell().setBorder(0);
            float[] Columnacli = new float[]{30f, 50f};
            tablacli.setWidths(Columnacli);
            tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1 = new PdfPCell(new Phrase("Nombre: ", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("DNI: ", negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            tablacli.addCell(cl1);
            tablacli.addCell(cl2);

            String nombre = vista.lblNombre.getText();
            String identifi = vista.txtBusquedaDni.getText();

            tablacli.addCell(nombre);
            tablacli.addCell(identifi);
            doc.add(tablacli);

            Paragraph pro = new Paragraph();
            pro.add(Chunk.NEWLINE);
            pro.add("Datos de los productos: " + "\n\n");
            doc.add(pro);

            //Productos
            PdfPTable tablapro = new PdfPTable(5);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] Columnapro = new float[]{40f, 50f, 45f, 40f, 40f};
            tablapro.setWidths(Columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell pro1 = new PdfPCell(new Phrase("ID", negrita));
            PdfPCell pro2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell pro3 = new PdfPCell(new Phrase("Precio", negrita));
            PdfPCell pro4 = new PdfPCell(new Phrase("Cantidad", negrita));
            PdfPCell pro5 = new PdfPCell(new Phrase("Subtotal", negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro5.setBorder(0);
            pro1.setBackgroundColor(BaseColor.CYAN);
            pro2.setBackgroundColor(BaseColor.CYAN);
            pro3.setBackgroundColor(BaseColor.CYAN);
            pro4.setBackgroundColor(BaseColor.CYAN);
            pro5.setBackgroundColor(BaseColor.CYAN);
            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            tablapro.addCell(pro5);
            for (int i = 0; i < vista.tblCarrito.getRowCount(); i++) {
                String ID = vista.tblCarrito.getValueAt(i, 0).toString();
                String Nombre = vista.tblCarrito.getValueAt(i, 1).toString();
                String PrecioUnitario = vista.tblCarrito.getValueAt(i, 2).toString();
                String Cantidad = vista.tblCarrito.getValueAt(i, 3).toString();
                String Valor = vista.tblCarrito.getValueAt(i, 6).toString();

                tablapro.addCell(ID);
                tablapro.addCell(Nombre);
                tablapro.addCell(PrecioUnitario);
                tablapro.addCell(Cantidad);
                tablapro.addCell(Valor);
            }

            doc.add(tablapro);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total: S/ " + vista.lblTotal.getText() + "\n\n");
            info.setAlignment(Element.ALIGN_JUSTIFIED);
            info.setAlignment(Element.ALIGN_RIGHT);
            String pagoSeleccionado = (String) vista.cbxPagos.getItemAt(0);
            info.add("TipoPago: S/ " + pagoSeleccionado + "\n\n");
            info.setAlignment(Element.ALIGN_JUSTIFIED);
            info.setAlignment(Element.ALIGN_RIGHT);
            info.add("Pagó: S/ " + vista.txtPresupuesto.getText() + "\n\n");
            info.setAlignment(Element.ALIGN_JUSTIFIED);
            info.setAlignment(Element.ALIGN_RIGHT);
            info.add("Vuelto: S/ " + vista.lblVuelto.getText());
            info.setAlignment(Element.ALIGN_JUSTIFIED);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelación y Firma\n\n");
            firma.setAlignment(Element.ALIGN_CENTER);
            firma.add("" + nombre + "\n\n");
            firma.setAlignment(Element.ALIGN_CENTER);
            firma.add("-------------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su compra");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            System.out.println("Error " + e);

        }
    }

    private void Cancelar() {
        carrito.clear();
        JOptionPane.showMessageDialog(vista, "Carrito cancelado y limpiado.");
        InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
        BuscadordProductos c = new BuscadordProductos(usuarioLogeado);
        c.show();
        vista.dispose();
    }

    private void buscar() {
        String DNIBuscado = vista.txtBusquedaDni.getText().trim();

        if (DNIBuscado.equals("Ingrese el Numero del DNI...")) {
            JOptionPane.showMessageDialog(null, "Ingrese el DNI");
        } else {
            List<Clientes> ClienteEncontrado = pdao2.readClientes(DNIBuscado);
            if (ClienteEncontrado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontro el producto");
            } else {
                Clientes cliente = ClienteEncontrado.get(0);
                // Asigna los valores a los campos de la vista
                vista.lblID.setText(String.valueOf(cliente.getCliente_ID()));
                vista.lblNombre.setText(cliente.getNombreCompleto());
            }
        }
    }

  

    private void insertarVenta() {
        try {
            System.out.println("Guardando cliente...");  // Depuración
            modelo2.setClienteID((vista.lblID.getText()));
            modelo2.setUsuarioID(String.valueOf(SesionUsuario.getIdLogeado()));
            modelo2.setTotal(Double.parseDouble(vista.lblTotal.getText()));
            modelo2.setFecha(vista.lblFecha.getText());
            modelo2.setHora(vista.lblHora.getText());

            // Crear una lista para los detalles de la venta
            List<DetalleVenta> detallesVenta = new ArrayList<>();

            for (int i = 0; i < vista.tblCarrito.getRowCount(); i++) {
                int idproducto = (int) vista.tblCarrito.getValueAt(i, 0);
                int cantidad = (int) vista.tblCarrito.getValueAt(i, 3);
                double precio = (double) vista.tblCarrito.getValueAt(i, 2);
                String fecha = vista.tblCarrito.getValueAt(i, 4).toString();
                String hora = vista.tblCarrito.getValueAt(i, 5).toString();
                double subtotal = Double.parseDouble(vista.tblCarrito.getValueAt(i, 6).toString());

                // Crear una nueva instancia de detalle de venta (modelo3)
                DetalleVenta detalle = new DetalleVenta();
                detalle.setProductoID(String.valueOf(idproducto));
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precio);
                detalle.setFechHoraAgreCarrito(fecha + " " + hora);
                detalle.setSubtotal(subtotal);

                // Agregar cada detalle a la lista
                detallesVenta.add(detalle);
            }

            if (pdao.createVenta(modelo2, detallesVenta)) {
                JOptionPane.showMessageDialog(null, "Venta Guardada");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
            }

        } catch (NumberFormatException e) {
            System.out.println("a");
        }
    }

    private void eliminarProducto() {
        int filaSeleccionada = vista.tblCarrito.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idProducto = (int) vista.tblCarrito.getValueAt(filaSeleccionada, 0);
            Carritoo productoAEliminar = null;
            for (Carritoo item : carrito) {
                if (item.getIdProducto() == idProducto) {
                    productoAEliminar = item;
                    break;
                }
            }
            if (productoAEliminar != null) {
                carrito.remove(productoAEliminar);
                DefaultTableModel dtmodel = (DefaultTableModel) vista.tblCarrito.getModel();
                dtmodel.removeRow(filaSeleccionada);
                JOptionPane.showMessageDialog(null, "Producto eliminado del carrito.");
                calcularTotal();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto en el carrito.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.");
        }
    }

    private void ConfirmarCompra() {
        insertarVenta();
    }

    private void procesar() {
        double total = Double.parseDouble(vista.lblTotal.getText());
        double presupuesto = Double.parseDouble(vista.txtPresupuesto.getText());
        double vuelto = presupuesto - total;

        vista.lblVuelto.setText(String.valueOf(vuelto));
    }

    private double calcularTotalRecursivo(int filaActual) {
        if (filaActual >= vista.tblCarrito.getRowCount()) {
            return 0;
        }
        double subtotal = (double) vista.tblCarrito.getValueAt(filaActual, 6);
        System.out.println(subtotal);
        return subtotal + calcularTotalRecursivo(filaActual + 1);
    }

    public void calcularTotal() {
        double total = calcularTotalRecursivo(0);
        vista.lblTotal.setText(String.valueOf(total));
    }

    private JTextField campoActual = null;

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
        if (textField.equals(vista.txtBusquedaDni)) {
            return "Ingrese el Numero del DNI...";
        }
        return "";
    }

    private void resetPlaceholder(JTextField textField) {
        if (textField.equals(vista.txtBusquedaDni)) {
            textField.setText("Ingrese el Numero del DNI...");
        }
        textField.setForeground(new Color(153, 153, 153)); // Color del placeholder
    }

    private void initialize() {
        System.out.println("Inicializando botones y listeners...");  // Depuración
        this.vista.lblBuscarDNI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblClienteNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblAgregarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblEliminarP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblContinuar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.vista.lblProcesar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this.vista.lblPDF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblPDF) {
                    System.out.println("label GenerarPDF presionado");
                    crearPDF();
                }
            }
        });
        this.vista.lblBuscarDNI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblBuscarDNI) {
                    System.out.println("label Buscar presionado");  // Depuración
                    buscar();
                }
            }
        });
        this.vista.lblClienteNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblClienteNuevo) {
                    System.out.println("label Cliente nuevo presionado");  // Depuración
                    NuevoCliente();
                }
            }
        });
        this.vista.lblAgregarProducto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblAgregarProducto) {
                    System.out.println("label agegar presionado");  // Depuración
                    NuevoProducto();
                }
            }
        });
        this.vista.lblEliminarP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblEliminarP) {
                    System.out.println("label Eliminar presionado");  // Depuración
                    eliminarProducto();
                }
            }
        });
        this.vista.lblContinuar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblContinuar) {
                    System.out.println("label Continuar presionado");  // Depuración
                    ConfirmarCompra();
                }
            }
        });
        this.vista.lblCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblCancelar) {
                    System.out.println("label Cancelar presionado");  // Depuración
                    Cancelar();
                }
            }
        });
        this.vista.lblProcesar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == vista.lblProcesar) {
                    System.out.println("label Procesar presionado");  // Depuración
                    procesar();
                }
            }
        });

        // Agregar MouseListener a los campos de texto
        vista.txtBusquedaDni.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                txtMousePressed(vista.txtBusquedaDni, "Ingrese el Numero del DNI...");
            }
        });

    }
}
