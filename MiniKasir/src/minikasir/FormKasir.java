/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package minikasir;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.time.LocalDate;

/**
 *
 * @author waski
 */
public class FormKasir extends javax.swing.JFrame {
    private DefaultTableModel model;
    
    /**
     * Creates new form FormKasir
     */
    NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
    public FormKasir() {
        initComponents();
        KodeBarang();
        Total();
        Diskon();
        autonumber();
        
    }
    
     private void autonumber() {
       try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasir?zeroDateTimeBehavior=CONVERT_TO_NULL", "admin", "admin");
            PreparedStatement pst = con.prepareStatement("SELECT MAX(id) FROM sales");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int no = rs.getInt(1) + 1;
                txtFaktur.setText(String.valueOf(no));
            } else {
                txtFaktur.setText("1");
            }
             txTanggal.setText(LocalDate.now().toString());
        } catch (SQLException e) {
          e.printStackTrace();
        }
}
    
   private void saveToDatabase(String kodeBarang, String namaBarang, String harga, String qty, String totalHarga) {
         String query = "INSERT INTO sales (kode_barang, nama_barang, harga, qty, total_harga) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, kodeBarang);
        stmt.setString(2, namaBarang);
        stmt.setInt(3, Integer.parseInt(harga.replace(".", "")));
        stmt.setInt(4, Integer.parseInt(qty));
        stmt.setInt(5, Integer.parseInt(totalHarga.replace(".", "")));
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    
    private void Diskon(){
         txtDiskon.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateDiscountAndTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateDiscountAndTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateDiscountAndTotal();
            }
        });
    }
    
    private void calculateDiscountAndTotal() {
       try {
            int jumlahHarga = txtJumlahHarga.getText().isEmpty() ? 0 : Integer.parseInt(txtJumlahHarga.getText().replace(".", ""));
            int diskon = txtDiskon.getText().isEmpty() ? 0 : Integer.parseInt(txtDiskon.getText().replace("", "0"));
            int diskonAmount = jumlahHarga * diskon / 100;
            int hasilDiskon = jumlahHarga - diskonAmount;
            txtHasilDiskon.setText(nf.format(diskonAmount));

            int hasilPpn = 0;
            if (chkPPN.isSelected()) {
                hasilPpn = hasilDiskon * 10 / 100;
            }
            txtHasilPPN.setText(nf.format(hasilPpn));
            int totalBersih = hasilPpn + hasilDiskon;
            lblJumlahHarga.setText("Rp. " + nf.format(totalBersih));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    
     private void Total(){
          txtQTY.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                calculateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                calculateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                calculateTotal();
            }
         });
     }
     
      private void calculateTotal() {
        try {
            int harga = txtHarga.getText().isEmpty() ? 0 : Integer.parseInt(txtHarga.getText().replace(".", ""));
            int qty = txtQTY.getText().isEmpty() ? 0 : Integer.parseInt(txtQTY.getText());
            int totalHarga = harga * qty;
            txtTotalHarga.setText(nf.format(totalHarga));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    private void KodeBarang(){
        txtKodeBarang.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateBarangDetails();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateBarangDetails();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateBarangDetails();
            }
        });
    }
    private void updateBarangDetails() {
        String kodeBarang = txtKodeBarang.getText();
        switch(kodeBarang){
            case"B001":
                txtNamaBarang.setText("Acer Aspire 5");
                txtHarga.setText(nf.format(7500000));
                txtQTY.grabFocus();
                break;
            case"B002":
                txtNamaBarang.setText("Macbook");
                txtHarga.setText(nf.format(11580000));
                txtQTY.grabFocus();
                break;
            case"B003":
                txtNamaBarang.setText("Asus ROG");
                txtHarga.setText(nf.format(8500000));
                txtQTY.grabFocus();
                break;
            case"B004":
                txtNamaBarang.setText("Asus TUF");
                txtHarga.setText(nf.format(6500000));
                txtQTY.grabFocus();
                break;
            case"B005":
                txtNamaBarang.setText("Macbook Pro");
                txtHarga.setText(nf.format(17500000));
                txtQTY.grabFocus();
                break;
            default:
                txtNamaBarang.setText("");
                txtHarga.setText("");
        }
    }
    
    private void bersih() {
        txtKodeBarang.setText("");
        txtKodeBarang.grabFocus();
        txtQTY.setText("");
        txtTotalHarga.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFaktur = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtKodeBarang = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        txtQTY = new javax.swing.JTextField();
        txtTotalHarga = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtDiskon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtJumlahHarga = new javax.swing.JTextField();
        chkPPN = new javax.swing.JCheckBox();
        txtHasilDiskon = new javax.swing.JTextField();
        txtHasilPPN = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblJmlQty = new javax.swing.JLabel();
        lblJumlahHarga = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        txTanggal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("No.faktur");

        txtFaktur.setEnabled(false);
        txtFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFakturActionPerformed(evt);
            }
        });

        jLabel3.setText("Kode Barang");

        txtHarga.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });

        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        txtQTY.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtQTY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQTYActionPerformed(evt);
            }
        });

        txtTotalHarga.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalHargaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQTY, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tblList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblList.setForeground(new java.awt.Color(0, 153, 153));
        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Barang", "Nama Barang", "Harga", "QTY", "Total Harga"
            }
        ));
        jScrollPane1.setViewportView(tblList);

        jLabel4.setText("Jumlah Harga");

        jLabel5.setText("Diskon %");

        chkPPN.setText("PPN 10 %");
        chkPPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPPNActionPerformed(evt);
            }
        });

        jLabel7.setText("Item Dibeli :");

        lblJmlQty.setText("0");

        lblJumlahHarga.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJumlahHarga.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblJumlahHarga.setText("Rp. ");

        btnSave.setText("add more");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txTanggal.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(chkPPN, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnSave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHasilDiskon, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                                    .addComponent(txtHasilPPN)))
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblJmlQty)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtJumlahHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblJumlahHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblJumlahHarga))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtJumlahHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(lblJmlQty))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHasilDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHasilPPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPPN))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(817, 451));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtQTYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQTYActionPerformed
        // TODO add your handling code here:
         if(txtQTY.getText().equals("")){
        return;
    } else {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();

        Object obj[] = new Object[6];
        obj[1] = txtKodeBarang.getText();
        obj[2] = txtNamaBarang.getText();
        obj[3] = txtHarga.getText();
        obj[4] = txtQTY.getText();
        obj[5] = txtTotalHarga.getText();

        model.addRow(obj);

        int baris = model.getRowCount();
        for (int a = 0; a < baris; a++) {
            String no = String.valueOf(a + 1);
            model.setValueAt(no + ".", a, 0);
        }
        tblList.setRowHeight(30);

        lblJmlQty.setText(String.valueOf(baris));

        // Save data to database
        saveToDatabase(txtKodeBarang.getText(), txtNamaBarang.getText(), txtHarga.getText(), txtQTY.getText(), txtTotalHarga.getText());

        jmlTotalHarga();
        bersih();
    }
    }//GEN-LAST:event_txtQTYActionPerformed
    
    private void txtTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalHargaActionPerformed

    private void txtFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFakturActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtFakturActionPerformed

    private void chkPPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPPNActionPerformed
        // TODO add your handling code here:
        jmlTotalHarga();
    }//GEN-LAST:event_chkPPNActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
         // Simpan data ke database
    saveDataFromTableToDatabase();

    // Menampilkan data dari database ke tabel
    loadDataFromDatabaseToTable();
    }//GEN-LAST:event_btnSaveActionPerformed
private void saveDataFromTableToDatabase() {
    DefaultTableModel model = (DefaultTableModel) tblList.getModel();
    int rowCount = model.getRowCount();
    
    try (Connection conn = Database.getConnection()) {
        for (int i = 0; i < rowCount; i++) {
            String kodeBarang = model.getValueAt(i, 1).toString();
            String namaBarang = model.getValueAt(i, 2).toString();
            String harga = model.getValueAt(i, 3).toString().replace(".", "");
            String qty = model.getValueAt(i, 4).toString();
            String totalHarga = model.getValueAt(i, 5).toString().replace(".", "");
            
            String query = "INSERT INTO sales (kode_barang, nama_barang, harga, qty, total_harga) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, kodeBarang);
                stmt.setString(2, namaBarang);
                stmt.setInt(3, Integer.parseInt(harga));
                stmt.setInt(4, Integer.parseInt(qty));
                stmt.setInt(5, Integer.parseInt(totalHarga));
                stmt.executeUpdate();
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    private void loadDataFromDatabaseToTable() {
    DefaultTableModel model = (DefaultTableModel) tblList.getModel();
    model.setRowCount(0);  // Menghapus semua baris di tabel

    String query = "SELECT * FROM sales";
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Object[] row = new Object[6];
            row[0] = model.getRowCount() + 1; // Nomor urut
            row[1] = rs.getString("kode_barang");
            row[2] = rs.getString("nama_barang");
            row[3] = NumberFormat.getNumberInstance(new Locale("in", "ID")).format(rs.getInt("harga"));
            row[4] = rs.getInt("qty");
            row[5] = NumberFormat.getNumberInstance(new Locale("in", "ID")).format(rs.getInt("total_harga"));
            model.addRow(row);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

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
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormKasir().setVisible(true);
            }
        });
    }
    
    public void run() {
    new FormKasir().setVisible(true);
    // Tampilkan data dari database ke tabel saat aplikasi dijalankan
    loadDataFromDatabaseToTable();
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chkPPN;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJmlQty;
    private javax.swing.JLabel lblJumlahHarga;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txTanggal;
    private javax.swing.JTextField txtDiskon;
    private javax.swing.JTextField txtFaktur;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtHasilDiskon;
    private javax.swing.JTextField txtHasilPPN;
    private javax.swing.JTextField txtJumlahHarga;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtQTY;
    private javax.swing.JTextField txtTotalHarga;
    // End of variables declaration//GEN-END:variables

  

    
    private void jmlTotalHarga() {
        int sub_total = 0;
        for (int a = 0; a < tblList.getRowCount(); a++){
            sub_total += Integer.parseInt(tblList.getValueAt(a, 5).toString().replace(".", ""));
        }
        txtJumlahHarga.setText(nf.format(sub_total));

        int diskon = Integer.parseInt(txtJumlahHarga.getText().replace(".", "")) * Integer.parseInt(txtDiskon.getText().replace("", "0"))/100;
        int hasil_diskon = Integer.parseInt(txtJumlahHarga.getText().replace(".", "")) - diskon;

        int hasil_ppn = 0;
        if(chkPPN.isSelected()){
            hasil_ppn = hasil_diskon * 10/100;
            txtHasilPPN.setText(nf.format(hasil_ppn));
        } else {
            hasil_ppn = 0;
            txtHasilPPN.setText(nf.format(hasil_ppn));
        }

        int totalBersih = hasil_ppn + hasil_diskon;
        lblJumlahHarga.setText("Rp. " + nf.format(totalBersih));
}
}
