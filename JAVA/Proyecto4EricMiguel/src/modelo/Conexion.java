/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dawj23
 */
public class Conexion {

    // Datos de la conexion
    public String bd = "bd_estoc";
    public String ip = "localhost";
    public String puerto = "3306";
    public String url = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;
    //driver:tipo driver://ip:puerto(si es pordefecto no hace falta)/nombre base datos
    public String user = "root";
    public String pass = "";

    //1º ESTABLER CONEXION:
    //funcion que conecta con la base de datos y java
    public Connection conec() {
        //hay que indicar la ruta donde se encuentra el driver
        Connection conexion = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver");//indicamos donde se encuentra el driver
            conexion = DriverManager.getConnection(url, user, pass);
            JOptionPane.showMessageDialog(null, "Conexion Establecida");//muesta ventana con mensaje
            System.out.println("Conexion Establecida");// muestra el mensaje por consola
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Conexion Erronea");
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Conexion Erronea");
            JOptionPane.showMessageDialog(null, e);

        } finally {
            return conexion;
        }
     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
