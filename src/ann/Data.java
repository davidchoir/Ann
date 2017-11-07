/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */
public class Data extends javax.swing.JFrame {

    /**
     * Creates new form Data
     */
    private DefaultTableModel tbData;
    private int row;
    
    public Data() {
        initComponents();
        
        tbData = new DefaultTableModel();
        
        tableData.setModel(tbData);
        tbData.addColumn("ID");
        tbData.addColumn("Tahun");
        tbData.addColumn("T5");
        tbData.addColumn("T4");
        tbData.addColumn("T3");
        tbData.addColumn("T2");
        tbData.addColumn("T1");
        tbData.addColumn("Target");
        tbData.addColumn("Kategori");
        
        getAllData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        labelHeader = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        comboKategori = new javax.swing.JComboBox<>();
        textTahun = new javax.swing.JTextField();
        textT5 = new javax.swing.JTextField();
        textT4 = new javax.swing.JTextField();
        textT3 = new javax.swing.JTextField();
        textT2 = new javax.swing.JTextField();
        textT1 = new javax.swing.JTextField();
        textTarget = new javax.swing.JTextField();
        buttonHapus = new javax.swing.JButton();
        buttonSimpan = new javax.swing.JButton();
        buttonUbah = new javax.swing.JButton();
        labelId = new javax.swing.JLabel();
        textId = new javax.swing.JTextField();
        buttonTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboSort = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Latih dan Data Uji");
        setBackground(new java.awt.Color(44, 62, 80));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel3.setBackground(new java.awt.Color(0, 171, 169));

        labelHeader.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        labelHeader.setForeground(new java.awt.Color(255, 255, 255));
        labelHeader.setText("DATA LATIH DAN DATA UJI");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelHeader)
                .addGap(286, 286, 286))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(labelHeader)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 171, 169));

        labelTitle.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(255, 255, 255));
        labelTitle.setText("Data Baru");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kategori");

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tahun");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("T5");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("T4");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("T3");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("T2");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("T1");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Target");

        comboKategori.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        comboKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Data Latih", "Data Uji" }));
        comboKategori.setToolTipText("Kategori Data");

        textTahun.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        textT5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        textT4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        textT3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        textT2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        textT1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        textTarget.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        buttonHapus.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        buttonHapus.setText("Hapus");
        buttonHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapusActionPerformed(evt);
            }
        });

        buttonSimpan.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        buttonSimpan.setText("Simpan");
        buttonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpanActionPerformed(evt);
            }
        });

        buttonUbah.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        buttonUbah.setText("Ubah");
        buttonUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUbahActionPerformed(evt);
            }
        });

        labelId.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        labelId.setForeground(new java.awt.Color(255, 255, 255));
        labelId.setText("ID data");

        textId.setEditable(false);
        textId.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        buttonTambah.setText("Tambah");
        buttonTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(labelId))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(comboKategori, 0, 137, Short.MAX_VALUE)
                                        .addComponent(textTahun)
                                        .addComponent(textT5)
                                        .addComponent(textT4)
                                        .addComponent(textT3)
                                        .addComponent(textT2)
                                        .addComponent(textT1)
                                        .addComponent(textTarget))
                                    .addComponent(textId, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(labelTitle)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(labelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelId)
                    .addComponent(textId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(textT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(textT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(textTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSimpan)
                    .addComponent(buttonTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonHapus)
                    .addComponent(buttonUbah))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tableData.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableData);

        jPanel2.setBackground(new java.awt.Color(0, 171, 169));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tampil Berdasarkan");

        comboSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua Data", "Data Latih", "Data Uji" }));
        comboSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpanActionPerformed
        saveData();
    }//GEN-LAST:event_buttonSimpanActionPerformed

    private void buttonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapusActionPerformed
        deleteData();
    }//GEN-LAST:event_buttonHapusActionPerformed

    private void buttonUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUbahActionPerformed
        updateData();
    }//GEN-LAST:event_buttonUbahActionPerformed

    private void tableDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDataMouseClicked
        if(evt.getClickCount()==1) {
            row = tableData.getSelectedRow();
            
            textId.setText(tableData.getValueAt(row, 0).toString());
            textTahun.setText(tableData.getValueAt(row, 1).toString());
            textT5.setText(tableData.getValueAt(row, 2).toString());
            textT4.setText(tableData.getValueAt(row, 3).toString());
            textT3.setText(tableData.getValueAt(row, 4).toString());
            textT2.setText(tableData.getValueAt(row, 5).toString());
            textT1.setText(tableData.getValueAt(row, 6).toString());
            textTarget.setText(tableData.getValueAt(row, 7).toString());
            comboKategori.setSelectedItem(tableData.getValueAt(row, 8).toString());
        }
    }//GEN-LAST:event_tableDataMouseClicked

    private void comboSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortActionPerformed
        String kategori = (String) comboSort.getSelectedItem();
        
        if ("Semua Data".equals(kategori)) {
            getAllData();
        } else {
            getSelectedData(kategori);
        }
    }//GEN-LAST:event_comboSortActionPerformed

    private void buttonTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambahActionPerformed
        tambahData();
    }//GEN-LAST:event_buttonTambahActionPerformed

    /**
     * @param args the command line arguments
     */
    
    private void getAllData() {
        tbData.getDataVector().removeAllElements();
        tbData.fireTableDataChanged();
        
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select * from tb_data");
            
            while (rsl.next()) {
                Object[] obj = new Object[9];
                
                obj[0] = rsl.getString("id_data");
                obj[1] = rsl.getString("tahun_data");
                obj[2] = rsl.getString("t5");
                obj[3] = rsl.getString("t4");
                obj[4] = rsl.getString("t3");
                obj[5] = rsl.getString("t2");
                obj[6] = rsl.getString("t1");
                obj[7] = rsl.getString("target_data");
                obj[8] = rsl.getString("kategori_data");
                
                tbData.addRow(obj);
            }
            
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Data\n"+e.toString());
        }
    }
    
    private void getSelectedData(String kategori) {
        tbData.getDataVector().removeAllElements();
        tbData.fireTableDataChanged();
        
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select * from tb_data where kategori_data = '"+kategori+"'");
            
            while (rsl.next()) {
                Object[] obj = new Object[9];
                
                obj[0] = rsl.getString("id_data");
                obj[1] = rsl.getString("tahun_data");
                obj[2] = rsl.getString("t5");
                obj[3] = rsl.getString("t4");
                obj[4] = rsl.getString("t3");
                obj[5] = rsl.getString("t2");
                obj[6] = rsl.getString("t1");
                obj[7] = rsl.getString("target_data");
                obj[8] = rsl.getString("kategori_data");
                
                tbData.addRow(obj);
            }
            
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Data\n"+e.toString());
        }
    }
    
    private void resetField() {
        textId.setText("");
        textTahun.setText("");
        textT1.setText("");
        textT2.setText("");
        textT3.setText("");
        textT4.setText("");
        textT5.setText("");
        textTarget.setText("");
    }
    
    private void saveData() {
        String tahun = textTahun.getText();
        double t5 = Double.parseDouble(textT5.getText());
        double t4 = Double.parseDouble(textT4.getText());
        double t3 = Double.parseDouble(textT3.getText());
        double t2 = Double.parseDouble(textT2.getText());
        double t1 = Double.parseDouble(textT1.getText());
        double t = Double.parseDouble(textTarget.getText());
        String kategori = (String) comboKategori.getSelectedItem();
        int id = 0;
        
        try {
            Statement stm;
            stm = Connect.getConn().createStatement();
            stm.executeUpdate("insert into tb_data values('"+id+"','"+
                    tahun+"','"+t5+"','"+t4+"','"+t3+"','"+t2+"','"+t1+"','"+t+"','"+kategori+"')");
            stm.close();
            JOptionPane.showMessageDialog(rootPane, "Data tahun "+tahun+" berhasil disimpan");
            getAllData();
            resetField();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Data Gagal Disimpan\n"+e.toString());
        }
    }
    
    private void updateData() {
        String id = textId.getText();
        String tahun = textTahun.getText();
        double t5 = Double.parseDouble(textT5.getText());
        double t4 = Double.parseDouble(textT4.getText());
        double t3 = Double.parseDouble(textT3.getText());
        double t2 = Double.parseDouble(textT2.getText());
        double t1 = Double.parseDouble(textT1.getText());
        double t = Double.parseDouble(textTarget.getText());
        String kategori = (String) comboKategori.getSelectedItem();
        
        try {
            Statement stm;
            stm = Connect.getConn().createStatement();
            
            stm.executeUpdate("update tb_data set tahun_data = '"+tahun+"',t5 = '"+t5+"',t4 = '"+t4+"',t3 = '"+
                    t3+"',t2 = '"+t2+"',t1 = '"+t1+"',target_data = '"+t+"',kategori_data = '"+kategori+
                    "' where id_data = '"+id+"'");
            
            Object[] obj = new Object[9];
            
            obj[0]=textId.getText();
            obj[1]=textTahun.getText();
            obj[2]=textT1.getText();
            obj[3]=textT2.getText();
            obj[4]=textT3.getText();
            obj[5]=textT2.getText();
            obj[6]=textT1.getText();
            obj[7]=textTarget.getText();
            obj[8]=comboKategori.getSelectedItem();
            
            tbData.removeRow(row);
            tbData.insertRow(row, obj);
            
            stm.close();
            JOptionPane.showMessageDialog(rootPane, "Data tahun "+tahun+" berhasil diubah");
            resetField();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Data Gagal Diubah\n"+e.toString());
        }
    }
    
    private void deleteData() {
        String id_data = String.valueOf(tableData.getValueAt(tableData.getSelectedRow(), 0));
        String tahun = String.valueOf(tableData.getValueAt(tableData.getSelectedRow(), 1));
        
        try {
            Statement stm;
            stm = Connect.getConn().createStatement();
            stm.executeUpdate("delete from tb_data where id_data = '"+id_data+"'");
            stm.close();
            JOptionPane.showMessageDialog(rootPane, "Data tahun "+tahun+" berhasil dihapus");
            getAllData();
            resetField();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Dihapus\n"+e.toString());
        }
    }
    
    private void tambahData() {
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select * from tb_data ORDER by tahun_data DESC LIMIT 0, 1");
            
            while (rsl.next()) {
                textT1.setText(rsl.getString("target_data"));
                textT2.setText(rsl.getString("t1"));
                textT3.setText(rsl.getString("t2"));
                textT4.setText(rsl.getString("t3"));
                textT5.setText(rsl.getString("t4"));
                
                int tahun_data = 1 + rsl.getInt("tahun_data");
                textTahun.setText(""+tahun_data);
            }
            
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Data\n"+e.toString());
        }
    }
    
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
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Data().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonHapus;
    private javax.swing.JButton buttonSimpan;
    private javax.swing.JButton buttonTambah;
    private javax.swing.JButton buttonUbah;
    private javax.swing.JComboBox<String> comboKategori;
    private javax.swing.JComboBox<String> comboSort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelHeader;
    private javax.swing.JLabel labelId;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTable tableData;
    private javax.swing.JTextField textId;
    private javax.swing.JTextField textT1;
    private javax.swing.JTextField textT2;
    private javax.swing.JTextField textT3;
    private javax.swing.JTextField textT4;
    private javax.swing.JTextField textT5;
    private javax.swing.JTextField textTahun;
    private javax.swing.JTextField textTarget;
    // End of variables declaration//GEN-END:variables
}
