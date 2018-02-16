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
    private DefaultTableModel tbDataHasil;
    private int row;
    
    public Testing() {
        initComponents();
        
        tbDataPrediksi = new DefaultTableModel();
        
        tableUji.setModel(tbDataPrediksi);
        tbDataPrediksi.addColumn("Tahun");
        tbDataPrediksi.addColumn("Target");
        tbDataPrediksi.addColumn("Prediksi");
        tbDataPrediksi.addColumn("Akurasi");
        
        tbDataHasil = new DefaultTableModel();
        
        tableHasil.setModel(tbDataHasil);
        tbDataHasil.addColumn("ID");
        tbDataHasil.addColumn("Arsitektur");
        tbDataHasil.addColumn("LR");
        tbDataHasil.addColumn("Epoch");
        tbDataHasil.addColumn("MSE");
        tbDataHasil.addColumn("Akurasi Latih");
        tbDataHasil.addColumn("Akurasi Uji");
        tbDataHasil.addColumn("Aktivasi");
        
        getAllData();
    }
    
    private void test() {
        String kategori = (String) comboKategori.getSelectedItem();
        
        tbDataPrediksi.getDataVector().removeAllElements();
        tbDataPrediksi.fireTableDataChanged();
        DecimalFormat df = new DecimalFormat("#.##");
        
        String pilihAktivasi = textAktivasi.getText();
        String id = textID.getText();
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Helper helper = new Helper();
        int countRecords = helper.countRecords();
        double setting[] = helper.loadSetting(pilihAktivasi, id);
        
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
        double target[] = new double[50];
        double prediksi[] = new double[50];
        
        double v[][] = helper.loadBobotHidden(neuron_hidden, neuron_input, pilihAktivasi, id);
        double vb[] = helper.loadBiasHidden(pilihAktivasi, id);
        double w[][] = helper.loadBobotOutput(neuron_output, neuron_hidden, pilihAktivasi, id);
        double wb[] = helper.loadBiasOutput(pilihAktivasi, id);
        
        if ("Semua Data".equals(kategori)) {
            countRecords = helper.countRecords();
        } else {
            countRecords = helper.countRecords(kategori);
        }
        
        int n = 0;
        try {
            Statement stm = Connect.getConn().createStatement();
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
        
        for (int i = 0; i < countRecords; i++) {
            for (int j = 0; j < 6; j++) {
                
                
                if ("Biner".equals(pilihAktivasi)) {
                    x[i][j] = ((xNorm[i][j]-nilaiMin)*(1-0)/(nilaiMax-nilaiMin))-0;
                t[i] = x[i][5];
                } else {
                    x[i][j] = ((xNorm[i][j]-nilaiMin)*(1-(-1))/(nilaiMax-nilaiMin))-1;
                t[i] = x[i][5];
                }
                target[i] = xNorm[i][5];
            }
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
                
                if ("Biner".equals(pilihAktivasi)) {
                    // Hasil z dengan aktivasi sigmoid biner
                    z[j] = 1/(1+(Math.exp(-z_net[j])));
                } else {
                    // Hasil z dengan aktivasi sigmoid bipolar
                    z[j] = (2/(1+(Math.exp(-z_net[j]))))-1;
                }
                
            }
            
            double y[] = new double[10];
            for (int j = 0; j < neuron_output; j++) {
                double y_net[] = new double[10];
                double temp = 0;

                for (int k = 0; k < neuron_hidden; k++) {
                    temp = temp + (z[k] * w[j][k]);
                }
                y_net[j] = wb[j] + temp;
                
                if ("Sigmoid Biner".equals(pilihAktivasi)) {
                    // Hasil y dengan aktivasi sigmoid biner
                    y[j] = 1/(1+(Math.exp(-y_net[j])));
                    hasil = ((nilaiMin*(1-0))+((y[j]-(0))*(nilaiMax-nilaiMin)))/(1-0);
                } else {
                    // Hasil y dengan aktivasi sigmoid bipolar
                    y[j] = (2/(1+(Math.exp(-y_net[j]))))-1;
                    hasil = ((nilaiMin*(1-(-1)))+((y[j]-(-1))*(nilaiMax-nilaiMin)))/(1-(-1));
                }
            }
            // MAPE
            prediksi[i] = hasil;
            akurasi[i] = 100 - ((Math.abs(target[i]-prediksi[i])/target[i]) * 100);
            
            rataAkurasi = rataAkurasi + akurasi[i];
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
                dataset.setValue(new Double(rsl.getString("target")), "Target", new Integer(rsl.getString("tahun")));
                dataset.setValue(new Double(df.format(prediksi[count])), "Prediksi", new Integer(rsl.getString("tahun")));
                
                count++;
            }
            
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Data\n"+e.toString());
        }
        
        JFreeChart chart = ChartFactory.createLineChart("Grafik Hasil Pengujian", "Tahun", "Nilai IPM", dataset);
        
        ChartFrame frame = new ChartFrame("Bar Chart", chart);
        frame.setVisible(true);
        frame.setSize(700,550);
        
        // MAPE Akurasi
        rataAkurasi = rataAkurasi/countRecords;
        labelMAPE.setText(String.valueOf(df.format(rataAkurasi))+"%");
        
        // update akurasi database
        try {
            Statement stm;
            stm = Connect.getConn().createStatement();
            
            if ("Data Latih".equals(kategori)) {
                stm.executeUpdate("update learnings set akurasi_latih = '"+df.format(rataAkurasi)+"' where id = '"+id+"'");
            } else if ("Data Uji".equals(kategori)) {
                stm.executeUpdate("update learnings set akurasi_uji = '"+df.format(rataAkurasi)+"' where id = '"+id+"'");
            } else {
                System.out.println();
            }
            
            getAllData();
            stm.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Data Gagal Diubah\n"+e.toString());
        }
    }
    
    private void getAllData() {
        tbDataHasil.getDataVector().removeAllElements();
        tbDataHasil.fireTableDataChanged();
        
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select * from learnings");
            
            while (rsl.next()) {
                Object[] obj = new Object[8];
                
                obj[0] = rsl.getString("id");
                obj[1] = rsl.getString("arsitektur");
                obj[2] = rsl.getString("lr");
                obj[3] = rsl.getString("epoch");
                obj[4] = rsl.getString("mse");
                obj[5] = rsl.getString("akurasi_latih");
                obj[6] = rsl.getString("akurasi_uji");
                obj[7] = rsl.getString("aktivasi");
                
                tbDataHasil.addRow(obj);
            }
            
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Data\n"+e.toString());
        }
    }
    
    private void getSelectedData(String aktivasi) {
        tbDataHasil.getDataVector().removeAllElements();
        tbDataHasil.fireTableDataChanged();
        
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select * from learnings where aktivasi = '"+aktivasi+"'");
            
            while (rsl.next()) {
                Object[] obj = new Object[8];
                
                obj[0] = rsl.getString("id");
                obj[1] = rsl.getString("arsitektur");
                obj[2] = rsl.getString("lr");
                obj[3] = rsl.getString("epoch");
                obj[4] = rsl.getString("mse");
                obj[5] = rsl.getString("akurasi_latih");
                obj[6] = rsl.getString("akurasi_uji");
                obj[7] = rsl.getString("aktivasi");
                
                tbDataHasil.addRow(obj);
            }
            
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Data\n"+e.toString());
        }
    }
    
    private void deleteData(){
        String id = String.valueOf(tableHasil.getValueAt(tableHasil.getSelectedRow(), 0));
        
        try {
            Statement stm;
            stm = Connect.getConn().createStatement();
            stm.executeUpdate("delete from learnings where id = '"+id+"'");
            stm.close();
            JOptionPane.showMessageDialog(rootPane, "Data id "+id+" berhasil dihapus");
            getAllData();
            resetField();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Dihapus\n"+e.toString());
        }
    }
    
    private void resetField(){
        textID.setText("");
        textAktivasi.setText("");
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
        textAktivasi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textID = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUji = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        labelMAPE = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableHasil = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboData = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

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

        textAktivasi.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ID");

        textID.setEditable(false);

        jButton1.setText("Hapus");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(70, 70, 70))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel3))
                                        .addGap(65, 65, 65))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buttonUji, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboKategori, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textAktivasi, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textAktivasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(buttonUji))
                .addGap(32, 32, 32))
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

        labelMAPE.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        labelMAPE.setForeground(new java.awt.Color(255, 255, 255));
        labelMAPE.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(labelMAPE, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(labelMAPE))
                .addGap(14, 14, 14))
        );

        tableHasil.setModel(new javax.swing.table.DefaultTableModel(
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
        tableHasil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHasilMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableHasil);

        jPanel3.setBackground(new java.awt.Color(0, 171, 169));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Data hasil pelatihan");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tampil berdasarkan");

        comboData.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua Data", "Biner", "Bipolar" }));
        comboData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel4)
                .addGap(119, 119, 119)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboData, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboData)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 171, 169));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Data hasil pengujian");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonUjiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUjiActionPerformed
        test();
    }//GEN-LAST:event_buttonUjiActionPerformed

    private void comboDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDataActionPerformed
        String data = (String) comboData.getSelectedItem();
        
        if ("Semua Data".equals(data)) {
            getAllData();
        } else {
            getSelectedData(data);
        }
    }//GEN-LAST:event_comboDataActionPerformed

    private void tableHasilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHasilMouseClicked
        if(evt.getClickCount()==1) {
            row = tableHasil.getSelectedRow();
            
            textID.setText(tableHasil.getValueAt(row, 0).toString());
            textAktivasi.setText(tableHasil.getValueAt(row, 7).toString());
        }
    }//GEN-LAST:event_tableHasilMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int pilih = JOptionPane.showConfirmDialog(rootPane, "Apakah anda yakin ingin menghapus data?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        
        if (pilih == 0) {
            deleteData();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JComboBox<String> comboData;
    private javax.swing.JComboBox<String> comboKategori;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelMAPE;
    private javax.swing.JTable tableHasil;
    private javax.swing.JTable tableUji;
    private javax.swing.JTextField textAktivasi;
    private javax.swing.JTextField textID;
    // End of variables declaration//GEN-END:variables
}
