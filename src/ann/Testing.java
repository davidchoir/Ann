/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author David
 */
public class Testing extends javax.swing.JFrame {

    /**
     * Creates new form Testing
     */
    
    private DefaultTableModel tbDataPrediksi;
    
    public Testing() {
        initComponents();
        
        tbDataPrediksi = new DefaultTableModel();
        
        tableUji.setModel(tbDataPrediksi);
        tbDataPrediksi.addColumn("Tahun");
        tbDataPrediksi.addColumn("Target");
        tbDataPrediksi.addColumn("Prediksi");
        tbDataPrediksi.addColumn("Akurasi");
    }
    
    private void test() {
        String kategori = (String) comboKategori.getSelectedItem();
        String aktivasi = (String) comboAktivasi.getSelectedItem();
        
        tbDataPrediksi.getDataVector().removeAllElements();
        tbDataPrediksi.fireTableDataChanged();
        DecimalFormat df = new DecimalFormat("#.##");
        
        String pilihAktivasi = "";
        
        if ("Sigmoid Biner".equals(aktivasi)) {
            pilihAktivasi = "Biner";
        } else {
            pilihAktivasi = "Bipolar";
        }
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Helper helper = new Helper();
        int countRecords = helper.countRecords();
        double setting[] = helper.readSetting(pilihAktivasi);
        
        double nilaiMax = setting[2];
        double nilaiMin = setting[1];
        double neuron_hidden = setting[0];
        int neuron_output = 1;
        int neuron_input = 5;
        int count = 0;
        
        double akurasi[] = new double[50];
        double rataAkurasi = 0;
        
        double xNorm[][] = new double[50][50];
        double x[][] = new double[50][50];
        double t[] = new double[50];
        double prediksi[] = new double[50];
        
        double v[][] = helper.readBobotHidden(neuron_hidden, neuron_input, pilihAktivasi);
        double vb[] = helper.readBiasHidden(pilihAktivasi);
        double w[][] = helper.readBobotOutput(neuron_output, neuron_hidden, pilihAktivasi);
        double wb[] = helper.readBiasOutput(pilihAktivasi);
        
        if ("Semua Data".equals(kategori)) {
            countRecords = helper.countRecords();
        } else {
            countRecords = helper.countRecords(kategori);
        }
        
        int n = 0;
        try {
            Statement stm = Connect.getConn().createStatement();
            // ResultSet rsl = stm.executeQuery("select * from datas");
            ResultSet rsl;
            if ("Semua Data".equals(kategori)) {
                rsl = stm.executeQuery("select * from datas");
            } else {
                rsl = stm.executeQuery("select * from datas where kategori = '"+kategori+"'");
            }
            
            while (rsl.next()) {
                xNorm[n][0] = rsl.getDouble("t5");
                xNorm[n][1] = rsl.getDouble("t4");
                xNorm[n][2] = rsl.getDouble("t3");
                xNorm[n][3] = rsl.getDouble("t2");
                xNorm[n][4] = rsl.getDouble("t1");
                xNorm[n][5] = rsl.getDouble("target");
                n++;
            }
        
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("Gagal"+e);
        }
        
        System.out.println(nilaiMin);
        System.out.println(nilaiMax);
        
        for (int i = 0; i < countRecords; i++) {
            for (int j = 0; j < 6; j++) {
                x[i][j] = ((xNorm[i][j]-nilaiMin)*(1-0)/(nilaiMax-nilaiMin))-0;
                t[i] = x[i][5];
            }
            // System.out.println(x[i][0]+" | "+x[i][1]+" | "+x[i][2]+" | "+x[i][3]+" | "+x[i][4]+" | "+x[i][5]);
            // System.out.println(xNorm[i][0]+" | "+xNorm[i][1]+" | "+xNorm[i][2]+" | "+xNorm[i][3]+" | "+xNorm[i][4]+" | "+xNorm[i][5]);
        }
        
        for (int i = 0; i < countRecords; i++) {
            double hasil = 0;
            
            double z[] = new double[10];
            for (int j = 0; j < neuron_hidden; j++) {
                double z_net[] = new double[10];
                double temp = 0;
                for (int k = 0; k < neuron_input; k++) {
                    // System.out.println(v[i][j]);
                    temp = temp + (x[i][k] * v[j][k]);
                    // System.out.println(x[i][k]+" "+v[k][j]);
                }
                z_net[j] = vb[j] + temp;
                z[j] = 1/(1+(Math.exp(-z_net[j])));
            }
            
            double y[] = new double[10];
            for (int j = 0; j < neuron_output; j++) {
                double y_net[] = new double[10];
                double temp = 0;

                for (int k = 0; k < neuron_hidden; k++) {
                    temp = temp + (z[k] * w[j][k]);
                }
                y_net[j] = wb[j] + temp;
                y[j] = 1/(1+(Math.exp(-y_net[j])));
                
                hasil = ((nilaiMin*(1-0))+((y[j]-0)*(nilaiMax-nilaiMin)))/(1-0);
                
            }
            /*
            prediksi[i] = hasil;
            selisih[i] = Math.abs(hasil - t[i]);
            rataSelisih = rataSelisih + selisih[i];
            */
            
            // MAPE
            prediksi[i] = hasil;
            akurasi[i] = 100 - ((Math.abs(t[i]-hasil)/t[i]) * 100);
            rataAkurasi = rataAkurasi + akurasi[i];
            
            // System.out.println(t[i]+" - "+hasil+" = "+akurasi[i]);
            // System.out.println(hasil+" - "+t[i]+" = "+selisih[i]);
        }
        
        try {
            Statement stm = Connect.getConn().createStatement();
            // ResultSet rsl = stm.executeQuery("select * from datas");
            ResultSet rsl;
            if ("Semua Data".equals(kategori)) {
                rsl = stm.executeQuery("select * from datas");
            } else {
                rsl = stm.executeQuery("select * from datas where kategori = '"+kategori+"'");
            }
            
            while (rsl.next()) {
                Object[] obj = new Object[4];
                
                obj[0] = rsl.getString("tahun");
                obj[1] = rsl.getString("target");
                obj[2] = df.format(prediksi[count]);
                obj[3] = df.format(akurasi[count])+"%";
                
                tbDataPrediksi.addRow(obj);
                
                // for graph
                dataset.setValue(new Double(rsl.getString("target")), "Values", new Integer(count));
                
                count++;
            }
            
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Data\n"+e.toString());
        }
        
        JFreeChart chart = ChartFactory.createLineChart("Grafik MSE", "Epoch", "Nilai MSE", dataset);
        
        ChartFrame frame = new ChartFrame("Bar Chart", chart);
        frame.setVisible(true);
        frame.setSize(700,550);
        
        // MAPE Akurasi
        rataAkurasi = rataAkurasi/countRecords;
        labelSelisih.setText(String.valueOf(df.format(rataAkurasi))+"%");
        
        // rataSelisih = rataSelisih/countRecords;
        // labelSelisih.setText(String.valueOf(df.format(rataSelisih)));
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        comboKategori = new javax.swing.JComboBox<>();
        buttonUji = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        comboAktivasi = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUji = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        labelSelisih = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vlidasi dan Pengujian");

        jPanel1.setBackground(new java.awt.Color(0, 171, 169));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PROSES VALIDASI DAN PENGUJIAN DATA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 171, 169));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pengaturan");

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kategori");

        comboKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua Data", "Data Latih", "Data Uji" }));

        buttonUji.setText("Uji");
        buttonUji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUjiActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Aktivasi");

        comboAktivasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sigmoid Biner", "Sigmoid Bipolar" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonUji, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(99, 99, 99)
                            .addComponent(jLabel2))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(53, 53, 53))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(58, 58, 58)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboAktivasi, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboAktivasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(buttonUji)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableUji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableUji);

        jPanel4.setBackground(new java.awt.Color(0, 171, 169));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Akurasi rata-rata");

        labelSelisih.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        labelSelisih.setForeground(new java.awt.Color(255, 255, 255));
        labelSelisih.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(labelSelisih, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(labelSelisih))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonUjiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUjiActionPerformed
        test();
    }//GEN-LAST:event_buttonUjiActionPerformed

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
            java.util.logging.Logger.getLogger(Testing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Testing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Testing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Testing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Testing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonUji;
    private javax.swing.JComboBox<String> comboAktivasi;
    private javax.swing.JComboBox<String> comboKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelSelisih;
    private javax.swing.JTable tableUji;
    // End of variables declaration//GEN-END:variables
}
