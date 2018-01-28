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
            ResultSet rsl = stm.executeQuery("select count(*) from datas where kategori = '"+kategori+"'");
            
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
    
    public int autoNumber() {
        int number = 0;
        try {
            Statement stm = Connect.getConn().createStatement();
            ResultSet rsl = stm.executeQuery("select max(id) from learnings");
            
            while (rsl.next()) {
                number = rsl.getInt(1);
            }
            rsl.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("SQL Gagal : "+e);
        }
        return number;
    }
    
    public void writeBobotHidden(double a[][], int x, int y, String aktivasi){
        String teks = "";
        String path = "d:/bobot/"+aktivasi+"/bobothidden.txt";
        
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
        File file = new File("d:/bobot/"+aktivasi+"/bobothidden.txt");
        
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
    
    public void saveBobotHidden(double a[][], double x, int y, String aktivasi, int name){
        String teks = "";
        String path = "d:/bobot/save/"+aktivasi+"/"+name+"bobothidden.txt";
        
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
    
    public double[][] loadBobotHidden(double a, int b, String aktivasi, String name){
        Scanner scan;
        File file = new File("d:/bobot/save/"+aktivasi+"/"+name+"bobothidden.txt");
        
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
        String path = "d:/bobot/"+aktivasi+"/biashidden.txt";
        
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
        File file = new File("d:/bobot/"+aktivasi+"/biashidden.txt");
        
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
    
    public void saveBiasHidden(double a[], double n, String aktivasi, int name){
        String teks = "";
        String path = "d:/bobot/save/"+aktivasi+"/"+name+"biashidden.txt";
        
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
    
    public double[] loadBiasHidden(String aktivasi, String name){
        Scanner scan;
        File file = new File("d:/bobot/save/"+aktivasi+"/"+name+"biashidden.txt");
        
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
        String path = "d:/bobot/"+aktivasi+"/bobotoutput.txt";
        
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
        File file = new File("d:/bobot/"+aktivasi+"/bobotoutput.txt");
        
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
    
    public void saveBobotOutput(double a[][], int x, double y, String aktivasi, int name){
        String teks = "";
        String path = "d:/bobot/save/"+aktivasi+"/"+name+"bobotoutput.txt";
        
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
    
    public double[][] loadBobotOutput(int a, double b, String aktivasi, String name){
        Scanner scan;
        File file = new File("d:/bobot/save/"+aktivasi+"/"+name+"bobotoutput.txt");
        
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
        String path = "d:/bobot/"+aktivasi+"/biasoutput.txt";
        
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
        File file = new File("d:/bobot/"+aktivasi+"/biasoutput.txt");
        
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
    
    public void saveBiasOutput(double a[], int n, String aktivasi, int name){
        String teks = "";
        String path = "d:/bobot/save/"+aktivasi+"/"+name+"biasoutput.txt";
        
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
    
    public double[] loadBiasOutput(String aktivasi, String name){
        Scanner scan;
        File file = new File("d:/bobot/save/"+aktivasi+"/"+name+"biasoutput.txt");
        
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
        String path = "d:/bobot/"+aktivasi+"/setting.txt";
        
        for (int i = 0; i < 6; i++) {
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
        File file = new File("d:/bobot/"+aktivasi+"/setting.txt");
        
        double arr[] = new double[6];
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
    
    public void saveSetting(double a[], String aktivasi, int name){
        String teks = "";
        String path = "d:/bobot/save/"+aktivasi+"/"+name+"setting.txt";
        
        for (int i = 0; i < 6; i++) {
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
    
    public double[] loadSetting(String aktivasi, String name){
        Scanner scan;
        File file = new File("d:/bobot/save/"+aktivasi+"/"+name+"setting.txt");
        
        double arr[] = new double[10];
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
