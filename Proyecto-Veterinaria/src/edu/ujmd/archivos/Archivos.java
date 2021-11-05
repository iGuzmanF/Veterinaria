/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.archivos;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author meev9
 */
public class Archivos {

    static String[] lista = new String[10];
    static  String rutaabsoluta = "\\hist\\Bitacora.txt";
    
    public String[] LeerFichero() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
        
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            String path = (System.getProperty("user.dir"));

            archivo = new File(path + rutaabsoluta);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                lista[i] = linea;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return lista;
    }

    public void EscribirFichero(String usua,String Hora, int h, int m, int s,String Fecha ,int dia, int mes, int anio) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String path = (System.getProperty("user.dir"));
            fichero = new FileWriter(path + rutaabsoluta, true);
            pw = new PrintWriter(fichero);
            pw.print("-----------\n");
            pw.print("Usuario: ");
            pw.print(usua);
            pw.print(Hora+"");
            pw.print(h+":");
            pw.print(m+":");
            pw.print(s+"");
            pw.print(Fecha+"");
            pw.print(dia+"-");
            pw.print(mes+"-");
            pw.print(anio+"\n");
    
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
    
      public void EscribirFicheroSalida(String HoraSalida,int h, int m, int s,String Fecha ,int dia, int mes, int anio) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String path = (System.getProperty("user.dir"));
            fichero = new FileWriter(path + rutaabsoluta, true);
            pw = new PrintWriter(fichero);
            pw.print("------------\n");
             pw.print("Hora y Fecha de salida \n");
            pw.print(HoraSalida);
            pw.print(h+":");
            pw.print(m+":");
            pw.print(s+"");
            pw.print(Fecha+"");
            pw.print(dia+"-");
            pw.print(mes+"-");
            pw.print(anio+"\n");
    
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




