/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.archivos;

/**
 *
 * @author meev9
 */
import java.io.*;

public class EscribirFichero {

     static  String[] listadeproductos = new String[10];
    static  String rutaabsoluta = "\\hist\\bitacora.txt";
    
    public static void main(String[] args) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
             String path = (System.getProperty("user.dir"));
            fichero = new FileWriter(path + rutaabsoluta,true);
            pw = new PrintWriter(fichero);
            for (int i = 0; i < 10; i++) {
                pw.println("Linea " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
