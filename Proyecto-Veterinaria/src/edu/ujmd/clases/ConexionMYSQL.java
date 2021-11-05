/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author meev9
 */
public class ConexionMYSQL {

    private static final String user = "root";
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String password = "Mario1999622521";
    private static final String url = "jdbc:mysql://localhost:3306/veterinaria?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static Connection cnn;

    public ConexionMYSQL() {
        
        cnn = null;
        try
        {
            Class.forName(driver);
            cnn = DriverManager.getConnection(url,user,password);
            if(cnn!=null)
            {
                System.out.println("Conexion establecida");
            }
        }
        
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Error al conectar" +e);
        }
    }

    //Conexion en la base de datos
   public Connection ObtenerConexion()
   {
       return cnn;
   }

    public void cerrarConexion() {
        try {
            cnn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
