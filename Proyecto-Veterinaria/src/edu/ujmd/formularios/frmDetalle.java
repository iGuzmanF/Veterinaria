/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.formularios;

import edu.ujmd.veterinaria.controladores.DetallefacturaJpaController;
import edu.ujmd.veterinaria.controladores.FacturaJpaController;
import edu.ujmd.veterinaria.controladores.FormapagoJpaController;
import edu.ujmd.veterinaria.entidades.Clientes;
import edu.ujmd.veterinaria.entidades.Detallefactura;
import edu.ujmd.veterinaria.entidades.Factura;
import edu.ujmd.veterinaria.entidades.Formapago;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author meev9
 */
public class frmDetalle extends javax.swing.JFrame {

    //Establecemos la conexion a la base de datos
    FacturaJpaController controlfac = new FacturaJpaController();
    DetallefacturaJpaController controldet = new DetallefacturaJpaController();
    FormapagoJpaController controlfor = new FormapagoJpaController();

    //Entidades
    Factura entifa = new Factura();
    Detallefactura entide = new Detallefactura();
    Formapago entifo = new Formapago();

    //metodo paras sumar valores
    public void calculoIVA() {

        //Calculo del total y iva
        String n1 = txtPrecio.getText();
        String n2 = txtIva.getText();
        String resultado = txtTotal.getText();
        float numero1 = Float.parseFloat(n1);
        float numero2 = Float.parseFloat(n2);
        float resul = Float.parseFloat(resultado);
        //calcilo del iva

        resul = (float) (numero1 * 0.13);
        


    }

    //Metodo cargar
    public void cargar() {

        entifa.setCodFactura(cboFactura.getSelectedIndex() + 1);
        entide.setCodFactura(entifa);
        entifo.setCodFormapago(cboFormaPago.getSelectedIndex() + 1);
        entide.setCodFormapago(entifo);
        entide.setPrecio(Float.parseFloat(txtPrecio.getText()));
        entide.setIva(Float.parseFloat(txtIva.getText()));
        entide.setFechapago(this.calendarfecha.getDate());
        entide.setDetalle(this.txtDetalle.getText());
        entide.setSubtotal(Float.parseFloat(txtSubtotal.getText()));
        entide.setTotal(Float.parseFloat(txtTotal.getText()));
        calculoIVA();

    }

    public void limpiar() {
        this.lblcodigo.setText("Generado Automaticamente");
        this.cboFormaPago.setSelectedIndex(0);
        this.cboFactura.setSelectedIndex(0);
        this.txtPrecio.setText("");
        this.txtDetalle.setText("");
        this.txtSubtotal.setText("");
        this.txtTotal.setText("");

    }

    public void llenarTabla() {
        String col[] = {"ID", "FACTURA", "FORMA DE PAGO", "PRECIO", "IVA", "FECHA", "DETALLE", "SUBTOTAL", "TOTAL"};
        DefaultTableModel modelo = new DefaultTableModel(col, 0);
        Object[] obj = new Object[9];
        List ls;

        try {
            ls = controldet.findDetallefacturaEntities();
            for (int i = 0; i < ls.size(); i++) {
                entide = (Detallefactura) ls.get(i);
                obj[0] = entide.getCodDetalle();
                obj[1] = entide.getCodFactura().getNumFactura();
                obj[2] = entide.getCodFormapago().getTarjeta();
                obj[3] = entide.getPrecio();
                obj[4] = entide.getIva();
                obj[5] = entide.getFechapago();
                obj[6] = entide.getDetalle();
                obj[7] = entide.getSubtotal();
                obj[8] = entide.getTotal();

                modelo.addRow(obj);
            }
            this.tabladetalle.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos : " + e.getMessage());
        }

    }

    //Llenar combo box
    List litsFactura = null;
    List litsForma = null;

    public void llenarComboFactura() {
        try {
            litsFactura = controlfac.findFacturaEntities();
            for (int i = 0; i < litsFactura.size(); i++) {
                entifa = (Factura) litsFactura.get(i);
                cboFactura.addItem(Integer.toString(entifa.getNumFactura()));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos : " + e.getMessage());
        }
    }

    public int buscarComboFactura(String fa) {
        int index = 0;
        try {
            for (int i = 0; i < litsFactura.size(); i++) {
                entifa = (Factura) litsFactura.get(i);
                if (entifa.getCodFactura().equals(fa)) {
                    index = i;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos : " + e.getMessage());
        }
        return index;
    }

    public void llenarComboMetodo() {
        try {
            litsForma = controlfor.findFormapagoEntities();
            for (int i = 0; i < litsForma.size(); i++) {
                entifo = (Formapago) litsForma.get(i);
                cboFormaPago.addItem(entifo.getTarjeta());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos : " + e.getMessage());
        }
    }

    public int buscarComboMetodo(String me) {
        int index = 0;
        try {
            for (int i = 0; i < litsForma.size(); i++) {
                entifo = (Formapago) litsForma.get(i);
                if (entifo.getCodFormapago().equals(me)) {
                    index = i;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos : " + e.getMessage());
        }
        return index;
    }

    /**
     * Creates new form frmDetalle
     */
    public frmDetalle() {
        initComponents();
        llenarTabla();
        llenarComboFactura();
        llenarComboMetodo();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        lblcodigo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnModifica = new javax.swing.JButton();
        txtEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabladetalle = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnVolver3 = new javax.swing.JButton();
        cboFormaPago = new javax.swing.JComboBox<>();
        cboFactura = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        calendarfecha = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtDetalle = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnvolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setBackground(new java.awt.Color(153, 153, 153));

        lblcodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblcodigo.setText("Se genera automaticamente");

        jLabel7.setText("PRECIO");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModifica.setText("Modificar");
        btnModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificaActionPerformed(evt);
            }
        });

        txtEliminar.setText("Eliminar");
        txtEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEliminarActionPerformed(evt);
            }
        });

        tabladetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabladetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabladetalleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabladetalle);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("DETALLE FACTURA");

        jLabel2.setText("FACTURA");

        jLabel3.setText("FORMA DE PAGO");

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel4.setText("CODIGO");

        btnVolver3.setText("Volver al menu");
        btnVolver3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolver3ActionPerformed(evt);
            }
        });

        jLabel8.setText("IVA");

        txtIva.setText("13");

        jLabel9.setText("FECHA");

        jLabel10.setText("DETALLE");

        jLabel11.setText("SUBTOTAL");

        jLabel12.setText("TOTAL");

        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        btnvolver.setText("Volver ");
        btnvolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(50, 50, 50)
                        .addComponent(cboFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(cboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(59, 59, 59)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(lblcodigo))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(70, 70, 70)
                                .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(54, 54, 54)
                                .addComponent(calendarfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(txtDetalle)
                            .addComponent(txtSubtotal))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(btnGuardar)
                        .addGap(49, 49, 49)
                        .addComponent(btnModifica)
                        .addGap(35, 35, 35)
                        .addComponent(txtEliminar)
                        .addGap(51, 51, 51)
                        .addComponent(btnLimpiar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 785, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnVolver3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnvolver, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver3)
                    .addComponent(btnvolver))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardar)
                            .addComponent(btnModifica)
                            .addComponent(txtEliminar)
                            .addComponent(btnLimpiar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(lblcodigo))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel2))
                                    .addComponent(cboFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel3))
                                    .addComponent(cboFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel7))
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel8))
                                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(calendarfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel10)
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel11))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        boolean save = true;
        try {
            cargar();
            controldet.create(entide);
        } catch (Exception ex) {
            save = false;
            JOptionPane.showMessageDialog(this, "Error en almacenar registro : " + ex.getMessage());
        }
        if (save) {
            limpiar();
            llenarTabla();
         
            JOptionPane.showMessageDialog(this, "El Registro se guardo con Exito!!!");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificaActionPerformed
        boolean update = true;
        try {
            cargar();
            entide.setCodDetalle(Integer.parseInt(this.lblcodigo.getText()));
            controldet.edit(entide);
        } catch (Exception ex) {
            update = false;
            JOptionPane.showMessageDialog(this, "Error en actualizar registro : " + ex.getMessage());
        }
        if (update) {
            limpiar();
            llenarTabla();
           
            JOptionPane.showMessageDialog(this, "El Registro se actualizo con Exito!!!");
        }
    }//GEN-LAST:event_btnModificaActionPerformed

    private void txtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEliminarActionPerformed
        boolean delete = true;
        try {
            cargar();
            entide.setCodDetalle(Integer.parseInt(this.lblcodigo.getText()));
            controldet.destroy(entide.getCodDetalle());
        } catch (Exception ex) {
            delete = false;
            JOptionPane.showMessageDialog(this, "Error en eliminar registro : " + ex.getMessage());
        }
        if (delete) {
            limpiar();
            llenarTabla();
            JOptionPane.showMessageDialog(this, "El Registro se elimino con Exito!!!");
        }
    }//GEN-LAST:event_txtEliminarActionPerformed

    private void tabladetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalleMouseClicked
        int index = 0;
        this.lblcodigo.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 0).toString());
        index = buscarComboFactura(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 1).toString());
        this.cboFactura.setSelectedIndex(index);
         index = buscarComboMetodo(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 2).toString());
        this.cboFormaPago.setSelectedIndex(index);
        this.txtPrecio.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 3).toString());
        this.txtIva.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 4).toString());
        this.txtDetalle.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 6).toString());
        this.txtSubtotal.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 7).toString());
        this.txtTotal.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 8).toString());
    }//GEN-LAST:event_tabladetalleMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnVolver3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolver3ActionPerformed
        frmMenu menu = new frmMenu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolver3ActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void btnvolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvolverActionPerformed
       frmFactura fa = new frmFactura();
       fa.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnvolverActionPerformed

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
            java.util.logging.Logger.getLogger(frmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDetalle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModifica;
    private javax.swing.JButton btnVolver3;
    private javax.swing.JButton btnvolver;
    private com.toedter.calendar.JDateChooser calendarfecha;
    private javax.swing.JComboBox<String> cboFactura;
    private javax.swing.JComboBox<String> cboFormaPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblcodigo;
    private javax.swing.JTable tabladetalle;
    private javax.swing.JTextField txtDetalle;
    private javax.swing.JButton txtEliminar;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
