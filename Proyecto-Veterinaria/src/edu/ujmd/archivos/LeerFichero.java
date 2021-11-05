
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author meev9
 */
class LeeFichero {
    
    static  String[] listausuarios = new String[10];
    static  String rutaabsoluta = "\\hist\\bitacora.txt";
    public static void main(String[] arg) {
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
            int i=0;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                listausuarios[i] = linea;
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
    }
}
