/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class Helper {
    public double nilaiMax(double[][] arr, int n) {
        double max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 6; j++) {
                if (max < arr[i][j]){
                    max = arr[i][j];
                }
            }
        }
        return max;
    }
    
    public double nilaiMin(double[][] arr, int n) {
        double min = 100;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 6; j++) {                
                if (min > arr[i][j]){
                    min = arr[i][j];
                }
            }
        }
        return min;
    }
    
    public int countRecords() {
        int count = 0;
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select count(*) from datas");
            
            while (rsl.next()) {
                count = rsl.getInt(1);
            }
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("SQL Gagal : "+e);
        }
        return count;
    }
    
    public int countRecords(String kategori) {
        int count = 0;
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select count(*) from tb_data where kategori_data = '"+kategori+"'");
            
            while (rsl.next()) {
                count = rsl.getInt(1);
            }
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("SQL Gagal : "+e);
        }
        return count;
    }
    
    public void writeBobotHidden(double a[][], int x, int y, String aktivasi){
        String teks = "";
        String path = "d:/bobot/bobothidden"+aktivasi+".txt";
        
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                teks = (teks+" ") + Double.toString(a[i][j]);
            }
        }
        
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(teks);
            bw.close();
        } catch (Exception e) {
        }
    }
    
    public double[][] readBobotHidden(double a, int b, String aktivasi){
        Scanner scan;
        File file = new File("d:/bobot/bobothidden"+aktivasi+".txt");
        
        double arr[] = new double[100];
        double x[][] = new double[100][100];
        int i = 0;
        
        try {
            scan = new Scanner(file);

            while(scan.hasNextDouble())
            {
                arr[i] = scan.nextDouble();
                i++;
            }
        
            int count = 0;
            
            for (int j = 0; j < a; j++) {
                for (int k = 0; k < b; k++) {
                    if (count == (a*b)) break;
                    x[j][k] = arr[count];
                    count++;
                }
            }
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
        
        return x;
    }
    
    public void writeBiasHidden(double a[], int n, String aktivasi){
        String teks = "";
        String path = "d:/bobot/biashidden"+aktivasi+".txt";
        
        for (int i = 0; i < n; i++) {
            teks = (teks+" ") + Double.toString(a[i]);
        }
        
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(teks);
            bw.close();
        } catch (Exception e) {
        }
    }
    
    public double[] readBiasHidden(String aktivasi){
        Scanner scan;
        File file = new File("d:/bobot/biashidden"+aktivasi+".txt");
        
        double arr[] = new double[100];
        int i = 0;
        
        try {
            scan = new Scanner(file);

            while(scan.hasNextDouble())
            {
                arr[i] = scan.nextDouble();
                i++;
            }
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
        
        return arr;
    }
    
    // Bobot Output
    
    public void writeBobotOutput(double a[][], int x, int y, String aktivasi){
        String teks = "";
        String path = "d:/bobot/bobotoutput"+aktivasi+".txt";
        
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                teks = (teks+" ") + Double.toString(a[i][j]);
            }
        }
        
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(teks);
            bw.close();
        } catch (Exception e) {
        }
    }
    
    public double[][] readBobotOutput(int a, double b, String aktivasi){
        Scanner scan;
        File file = new File("d:/bobot/bobotoutput"+aktivasi+".txt");
        
        double arr[] = new double[10];
        double x[][] = new double[10][10];
        int i = 0;
        
        try {
            scan = new Scanner(file);

            while(scan.hasNextDouble())
            {
                arr[i] = scan.nextDouble();
                i++;
            }
        
            int count = 0;
            
            for (int j = 0; j < a; j++) {
                for (int k = 0; k < b; k++) {
                    if (count == (a*b)) break;
                    x[j][k] = arr[count];
                    count++;
                }
            }
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
        
        return x;
    }
    
    public void writeBiasOutput(double a[], int n, String aktivasi){
        String teks = "";
        String path = "d:/bobot/biasoutput"+aktivasi+".txt";
        
        for (int i = 0; i < n; i++) {
            teks = (teks+" ") + Double.toString(a[i]);
        }
        
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(teks);
            bw.close();
        } catch (Exception e) {
        }
    }
    
    public double[] readBiasOutput(String aktivasi){
        Scanner scan;
        File file = new File("d:/bobot/biasoutput"+aktivasi+".txt");
        
        double arr[] = new double[50];
        int i = 0;
        
        try {
            scan = new Scanner(file);

            while(scan.hasNextDouble())
            {
                arr[i] = scan.nextDouble();
                i++;
            }
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
        
        return arr;
    }
    
    public void writeSetting(double a[], String aktivasi){
        String teks = "";
        String path = "d:/bobot/setting"+aktivasi+".txt";
        
        for (int i = 0; i < 3; i++) {
            teks = (teks+" ") + Double.toString(a[i]);
        }
        
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(teks);
            bw.close();
        } catch (Exception e) {
        }
    }
    
    public double[] readSetting(String aktivasi){
        Scanner scan;
        File file = new File("d:/bobot/setting"+aktivasi+".txt");
        
        double arr[] = new double[3];
        int i = 0;
        
        try {
            scan = new Scanner(file);

            while(scan.hasNextDouble())
            {
                arr[i] = scan.nextDouble();
                i++;
            }
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
        
        return arr;
    }
}
