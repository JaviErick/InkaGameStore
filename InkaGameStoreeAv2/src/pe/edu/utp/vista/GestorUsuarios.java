
package pe.edu.utp.vista;

import pe.edu.utp.controller.UsuarioController;
import pe.edu.utp.daoImpl.UsuarioDaoImpl;
import pe.edu.utp.entity.Usuario;
import pe.edu.utp.dao.UsuarioDao;
import pe.edu.utp.dto.InicioSesionDTO;
import pe.edu.utp.dto.SesionUsuario;

public class GestorUsuarios extends javax.swing.JFrame {

    private Usuario modelo;
    private UsuarioDao dao;
    private UsuarioController controller;
    private InicioSesionDTO usuarioLogeado;

    private void mostrarDatosUsuario() {
        if (usuarioLogeado != null) {
            lblCargo.setText(usuarioLogeado.getNombreCompleto());
        }
    }

    public GestorUsuarios(InicioSesionDTO usuarioLogeado) {
       initComponents(); 
       modelo = new Usuario();
       dao = new UsuarioDaoImpl();
       controller = new UsuarioController(modelo, dao, this);
       controller.iniciar();
       this.usuarioLogeado = usuarioLogeado;
        mostrarDatosUsuario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtContraseña1111 = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cbxRol = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        txtID = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblGuardar = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblModificar = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        lblRegresar = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblEliminar = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        lblExcel = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        lblLimpiar = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        txtContraseña1111.setForeground(new java.awt.Color(153, 153, 153));
        txtContraseña1111.setText("*****************");
        txtContraseña1111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseña1111ActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");

        btnLimpiar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");

        btnEditar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnEditar.setText("Modificar");

        btnGuardar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnGuardar.setText("Guardar");

        jButton1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton1.setText("REPORTE EXCEL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnRegresar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnRegresar.setText("Regresar");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleado", "Administrador" }));
        jPanel1.add(cbxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 180, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("A Cargo:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 80, 20));

        lblCargo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCargo.setForeground(new java.awt.Color(255, 255, 255));
        lblCargo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 120, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Gestor de Empleados");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 500, 120));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/assets/logoinka.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 100, 80));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Rol: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 60, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Nomb y Apelli:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 110, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Teléfono:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 70, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("ID Usuario:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Contraseña:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 80, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Correo:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 60, 30));

        txtContraseña.setForeground(new java.awt.Color(153, 153, 153));
        txtContraseña.setText("************");
        txtContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseñaActionPerformed(evt);
            }
        });
        jPanel1.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, 180, 30));

        txtID.setForeground(new java.awt.Color(153, 153, 153));
        txtID.setText("AutoIncrementable***");
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 180, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("DNI:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 30, 30));

        jPanel6.setBackground(new java.awt.Color(17, 17, 58));

        lblGuardar.setDisplayedMnemonic('M');
        lblGuardar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblGuardar.setForeground(new java.awt.Color(255, 255, 255));
        lblGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGuardar.setText("Guardar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, -1, -1));

        jPanel5.setBackground(new java.awt.Color(17, 17, 58));

        lblModificar.setDisplayedMnemonic('M');
        lblModificar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblModificar.setForeground(new java.awt.Color(255, 255, 255));
        lblModificar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblModificar.setText("Modificar");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, -1, 30));

        txtDNI.setForeground(new java.awt.Color(153, 153, 153));
        txtDNI.setText("Ingrese su DNI...");
        txtDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDNIActionPerformed(evt);
            }
        });
        jPanel1.add(txtDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 180, 30));

        jPanel9.setBackground(new java.awt.Color(17, 17, 58));

        lblRegresar.setDisplayedMnemonic('M');
        lblRegresar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblRegresar.setForeground(new java.awt.Color(255, 255, 255));
        lblRegresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegresar.setText("Regresar");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, -1, -1));

        jPanel7.setBackground(new java.awt.Color(17, 17, 58));

        lblEliminar.setDisplayedMnemonic('M');
        lblEliminar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblEliminar.setForeground(new java.awt.Color(255, 255, 255));
        lblEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEliminar.setText("Eliminar");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, -1, -1));

        jPanel10.setBackground(new java.awt.Color(17, 17, 58));

        lblExcel.setDisplayedMnemonic('M');
        lblExcel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblExcel.setForeground(new java.awt.Color(255, 255, 255));
        lblExcel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExcel.setText("Reporte Excel");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExcel, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExcel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 520, -1, -1));

        txtNombre.setForeground(new java.awt.Color(153, 153, 153));
        txtNombre.setText("Ingrese sus Nombres y Apellidos...");
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 180, 30));

        jPanel8.setBackground(new java.awt.Color(17, 17, 58));

        lblLimpiar.setDisplayedMnemonic('M');
        lblLimpiar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        lblLimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLimpiar.setText("Limpiar");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(lblLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 25, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 540, -1, -1));

        txtTelefono.setForeground(new java.awt.Color(153, 153, 153));
        txtTelefono.setText("Ingrese su Telefono...");
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 180, 30));

        txtCorreo.setForeground(new java.awt.Color(153, 153, 153));
        txtCorreo.setText("Ingrese su Correo...");
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, 180, 30));

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Rol", "DNI", "Nombres y Apellidos", "Teléfono", "Correo", "Contraseña"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuario.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblUsuario);
        if (tblUsuario.getColumnModel().getColumnCount() > 0) {
            tblUsuario.getColumnModel().getColumn(0).setResizable(false);
            tblUsuario.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblUsuario.getColumnModel().getColumn(1).setResizable(false);
            tblUsuario.getColumnModel().getColumn(2).setResizable(false);
            tblUsuario.getColumnModel().getColumn(3).setResizable(false);
            tblUsuario.getColumnModel().getColumn(3).setPreferredWidth(120);
            tblUsuario.getColumnModel().getColumn(4).setResizable(false);
            tblUsuario.getColumnModel().getColumn(5).setResizable(false);
            tblUsuario.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 500, 340));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/assets/encabezado.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 140));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pe/edu/utp/assets/fondo3.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 850, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDNIActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtContraseña1111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseña1111ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseña1111ActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseñaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestorUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestorUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestorUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestorUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InicioSesionDTO usuarioLogeado = SesionUsuario.getUsuarioLogeado();
                new GestorUsuarios(usuarioLogeado).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnRegresar;
    public javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCargo;
    public javax.swing.JLabel lblEliminar;
    public javax.swing.JLabel lblExcel;
    public javax.swing.JLabel lblGuardar;
    public javax.swing.JLabel lblLimpiar;
    public javax.swing.JLabel lblModificar;
    public javax.swing.JLabel lblRegresar;
    public javax.swing.JTable tblUsuario;
    public javax.swing.JPasswordField txtContraseña;
    public javax.swing.JTextField txtContraseña1111;
    public javax.swing.JTextField txtCorreo;
    public javax.swing.JTextField txtDNI;
    public javax.swing.JTextField txtID;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
