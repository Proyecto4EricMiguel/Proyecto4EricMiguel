/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Categoria;
import modelo.Conexion;
import modelo.Producte;
import modelo.Stock;

/**
 *
 * @author 9199.joan23
 */
public class Controller {

    public Controller() {
    }

    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public int login(String usuario, String clavedef) {

        int resultado = 0;

        Conexion conecControl = new Conexion();
        Connection cn = conecControl.conec();

        String sql = "SELECT * FROM tbl_persona WHERE pers_nom='" + usuario + "' && pers_pass='" + clavedef + "'";

        try {
            // crear el statement
            Statement st = cn.createStatement();
            // lanzar la consulta
            ResultSet rs = st.executeQuery(sql);
            //lanza consulta con "executequery"
            while (rs.next()) {
                resultado = 1;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "UPPS! no se ha podido conectar a la base de datos");
        } finally {
            try {
                cn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "UPPS! no se ha podido desconectar a la base de datos");
            }
        }
        return resultado;
    }

    public DefaultTableModel mostrarProducto() {
        DefaultTableModel muestra = null;

        //1. conectarme
        Conexion conectar = new Conexion();
        Connection cn = conectar.conec();

        String sql = "Select * From tbl_estoc inner join tbl_producte on tbl_estoc.prod_id=tbl_producte.prod_id inner join tbl_categoria on tbl_producte.categoria_id=tbl_categoria.categoria_id ";
        Statement st = null;
        String vectorProducto[] = new String[10];
        String vectorProducto1[] = new String[10];

        vectorProducto1[0] = "prod_id";
        vectorProducto1[1] = "prod_nom";
        vectorProducto1[2] = "prod_preu";
        vectorProducto1[3] = "prod_foto";
        vectorProducto1[4] = "categoria_id";
        vectorProducto1[5] = "estoc_q_max";
        vectorProducto1[6] = "estoc_q_min";
        vectorProducto1[7] = "estoc_q_actual";
        vectorProducto1[8] = "categoria_nom";
        vectorProducto1[9] = "estoc_id";

        muestra = new DefaultTableModel(null, vectorProducto1);
//String[] vectorProducto; De otra manera definir el vector

        try {

            st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                vectorProducto[0] = String.valueOf(rs.getInt("prod_id"));
                vectorProducto[1] = rs.getString("prod_nom");
                vectorProducto[2] = String.valueOf(rs.getDouble("prod_preu"));
                vectorProducto[3] = rs.getString("prod_foto");
                vectorProducto[4] = String.valueOf(rs.getInt("categoria_id"));
                vectorProducto[5] = String.valueOf(rs.getInt("estoc_q_max"));
                vectorProducto[6] = String.valueOf(rs.getInt("estoc_q_min"));
                vectorProducto[7] = String.valueOf(rs.getInt("estoc_q_actual"));
                vectorProducto[8] = rs.getString("categoria_nom");
                vectorProducto[9] = String.valueOf(rs.getInt("estoc_id"));
                muestra.addRow(vectorProducto);
            }
        } catch (Exception e) {
        }

        return muestra;
    }

    public void Caja(JComboBox box) {
        DefaultComboBoxModel value;
        Conexion conectar = new Conexion();
        Connection cn = conectar.conec();

        String sql = "Select * From tbl_categoria";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = cn.createStatement();
            //JOptionPane.showMessageDialog(null, "Conexion viento en popa2");
            rs = st.executeQuery(sql);
            // JOptionPane.showMessageDialog(null, "Conexion viento en popa2");
            value = new DefaultComboBoxModel();
            box.setModel(value);
            while (rs.next()) {

                value.addElement(new Categoria(rs.getInt("categoria_id"), rs.getString("categoria_nom")));

            }
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Conexion erronea");

        }
    }

    public void añadirProductoEstoc(Producte p, Stock s) {
        //1. conectarme
        Conexion conectar = new Conexion();
        Connection cn = conectar.conec();
        //creamos la primera consulta sql
        String sql1 = "INSERT INTO tbl_producte (prod_nom, prod_preu ,categoria_id) VALUES(?,?,?)";

        //Creamos la segunda sentencia, esta setencia recogera el ultimo registro insertado
        String sql2 = "SELECT  DISTINCT last_insert_id() FROM  tbl_producte";
        System.out.println(sql1);
        System.out.println(sql2);

        // Creamos la tercera sentencia 
        String sql3 = "INSERT INTO tbl_estoc (estoc_q_actual, estoc_q_min ,estoc_q_max, prod_id) VALUES (?,?,?,?)";
        System.out.println(sql3);
        PreparedStatement pst1 = null;
        Statement st = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;
        try {

            cn.setAutoCommit(false); //solo hace una sentencia sql (false) hace dos sentencias (true) si se devuelve un error no se efectuaran los cambios

            //Llamamos al prepared Statement 1 y a la primera sentencia 
            pst1 = cn.prepareStatement(sql1);

            pst1.setString(1, p.getProd_nom());
            pst1.setDouble(2, p.getProd_preu());
            pst1.setInt(3, p.getCategoria_id());

            //Ejecutamos la sentecio con ExecuteUpdate
            pst1.executeUpdate();

            JOptionPane.showMessageDialog(null, "La creacion del producto se ha efectuado");

            //recuperamos el ultimo registro del producto (el que acabamos de crear) con la segunda sentecia 
            st = cn.createStatement();
            rs = st.executeQuery(sql2);
            int prod_id = 0;
            while (rs.next()) {
                prod_id = rs.getInt(1);
            }

            //String prod_id= rs.toString();
            JOptionPane.showMessageDialog(null, prod_id);
            // System.out.println(prod_id);

            //Llamamos al prepared Statement 2 y a la tercera sentencia 
            pst2 = cn.prepareStatement(sql3);

            pst2.setInt(1, s.getStock_actual());
            pst2.setInt(2, s.getStock_min());
            pst2.setInt(3, s.getStock_max());
            pst2.setInt(4, prod_id);
            //Ejecutamos la sentecio con ExecuteUpdate

            pst2.executeUpdate();
            JOptionPane.showMessageDialog(null, "La creacion del Stock se ha efectuado");

            // Realizamos un commit para realizar las dos sentencias de insertar
            cn.commit();

            cn.setAutoCommit(true);   //Cambiamos el auto commit a true
            cn.close();
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Conexion erronea ");
            try {
                cn.rollback();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se puede deshacer");
            }

        }
    }
    
    public void eliminarProductoEstoc(Producte p, Stock s) {
        //1. conectarme
        Conexion conectar = new Conexion();
        Connection cn = conectar.conec();
        //creamos la primera consulta sql
        //String sql1 = "INSERT INTO tbl_producte (prod_nom, prod_preu ,prod_foto,categoria_id) VALUES(?,?,?,?)";
        String sql1 = "DELETE FROM tbl_estoc WHERE tbl_estoc.estoc_id = ?";

        // Creamos la segunda sentencia 
        //String sql2 = "INSERT INTO tbl_estoc (estoc_q_actual, estoc_q_min ,estoc_q_max, prod_id) VALUES (?,?,?,?)";
        String sql2 = "DELETE FROM tbl_producte WHERE tbl_producte.prod_id = ?";

        PreparedStatement pst1 = null;
        Statement st = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;
        try {

            cn.setAutoCommit(true);   //solo hace una sentencia sql (false) hace dos sentencias (true) si se devuelve un error no se efectuaran los cambios

            //Llamamos al prepared Statement 1 y a la primera sentencia 
            pst1 = cn.prepareStatement(sql1);
            pst1.setInt(1, s.getIdstock());

            //Ejecutamos la sentecio con ExecuteUpdate
            pst1.executeUpdate();
            JOptionPane.showMessageDialog(null, "La eliminacion del producto se ha efecutado");

            //Llamamos al prepared Statement 2 y a la tercera sentencia 
            pst2 = cn.prepareStatement(sql2);
            pst2.setInt(1, p.getProd_id());

            //Ejecutamos la sentecio con ExecuteUpdate
            pst2.executeUpdate();
            JOptionPane.showMessageDialog(null, "La eliminacion del Stock se ha efectuado");

            // Realizamos un commit para realizar las dos sentencias de insertar
            cn.commit();

            
            cn.close();
            // -----------------------------------------------------------------------
            // -----------------------------------------------------------------------
            //  --------------------- MIRA ESTO IMPORTANTE ---------------------------
            // -----------------------------------------------------------------------
            // -----------------------------------------------------------------------
            //Comentamos la opciones del catch ya que al realizar la acción requerida (eliminar) realiza a su vez los mensajes de error el catch.
        } catch (SQLException | HeadlessException e) {
            //JOptionPane.showMessageDialog(null, "Conexion erronea2");
            try {
                cn.rollback();
            } catch (SQLException ex) {
                
                //JOptionPane.showMessageDialog(null, "No se puede deshacer2");
            }

        }
    }
    
    public void modifcarProducto(Producte p) {
        //1. conectarme
        Conexion conectar = new Conexion();
        Connection cn = conectar.conec();
        //creamos la primera consulta sql
        //String sql1 = "INSERT INTO tbl_producte (prod_nom, prod_preu ,prod_foto,categoria_id) VALUES(?,?,?,?)";

        String sql1 = "UPDATE tbl_producte SET prod_nom = ?, `prod_preu` = ?,  categoria_id = ? WHERE tbl_producte.prod_id = ?";

        // Creamos la segunda sentencia 
        //String sql2 = "INSERT INTO tbl_estoc (estoc_q_actual, estoc_q_min ,estoc_q_max, prod_id) VALUES (?,?,?,?)";
       

        PreparedStatement pst1 = null;
        Statement st = null;
      
        ResultSet rs = null;
        try {

            cn.setAutoCommit(false);   //solo hace una sentencia sql (false) hace dos sentencias (true) si se devuelve un error no se efectuaran los cambios

            //Llamamos al prepared Statement 1 y a la primera sentencia 
            pst1 = cn.prepareStatement(sql1);
            
           
          
            pst1.setString(1, p.getProd_nom());
            pst1.setDouble(2, p.getProd_preu());
            pst1.setInt(3, p.getCategoria_id());
            pst1.setInt(4, p.getProd_id());
            
          
          
            
           

            //Ejecutamos la sentecio con ExecuteUpdate
            pst1.executeUpdate();
            JOptionPane.showMessageDialog(null, "La modificacion del producto se ha efecutado");

            // Realizamos un commit para realizar las dos sentencias de insertar
            cn.commit();

            cn.setAutoCommit(true);   //Cambiamos el auto commit a true
          
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Conexion erronea");
            try {
                cn.rollback();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se puede deshacer");
            }

        }
    }
    
    public void modifcarEstoc(Stock s) {
        //1. conectarme
        Conexion conectar = new Conexion();
        Connection cn = conectar.conec();
        //creamos la primera consulta sql
        //String sql1 = "INSERT INTO tbl_producte (prod_nom, prod_preu ,prod_foto,categoria_id) VALUES(?,?,?,?)";

        

        // Creamos la segunda sentencia 
        //String sql2 = "INSERT INTO tbl_estoc (estoc_q_actual, estoc_q_min ,estoc_q_max, prod_id) VALUES (?,?,?,?)";
        String sql2 = "UPDATE `tbl_estoc` SET `estoc_q_actual` = ?, `estoc_q_min` = ?, `estoc_q_max` = ? WHERE `tbl_estoc`.`estoc_id` = ?";

        
        Statement st = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;
        try {

            cn.setAutoCommit(false);   //solo hace una sentencia sql (false) hace dos sentencias (true) si se devuelve un error no se efectuaran los cambios


            //Llamamos al prepared Statement 2 y a la tercera sentencia 
            pst2 = cn.prepareStatement(sql2);

            pst2.setInt(1, s.getStock_actual());
            pst2.setInt(2, s.getStock_min());
            pst2.setInt(3, s.getStock_max());
            pst2.setInt(4, s.getIdstock());

            //Ejecutamos la sentecio con ExecuteUpdate
            pst2.executeUpdate();
            JOptionPane.showMessageDialog(null, "La modificacion del Stock se ha efectuado");

            // Realizamos un commit para realizar las dos sentencias de insertar
            cn.commit();

            //cn.setAutoCommit(true);   //Cambiamos el auto commit a true
            
            
            cn.close();
            // -----------------------------------------------------------------------
            // -----------------------------------------------------------------------
            //  --------------------- MIRA ESTO IMPORTANTE ---------------------------
            // -----------------------------------------------------------------------
            // -----------------------------------------------------------------------
            //Comentamos la opciones del catch ya que al realizar la acción requerida (eliminar) realiza a su vez los mensajes de error el catch.
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Conexion erronea");
            try {
                cn.rollback();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se puede deshacer");
            }

        }
    }
    
     public DefaultTableModel buscarProductoNombre(String buscar) {
        DefaultTableModel muestra = null;

        //1. conectarme
        Conexion conectar = new Conexion();
        Connection cn = conectar.conec();

        String sql = "Select * From tbl_estoc inner join tbl_producte on tbl_estoc.prod_id=tbl_producte.prod_id inner join tbl_categoria on tbl_producte.categoria_id=tbl_categoria.categoria_id WHERE tbl_producte.prod_nom LIKE '%"+buscar+"%'";
        Statement st = null;
        String vectorProducto[] = new String[10];
        String vectorProducto1[] = new String[10];

        vectorProducto1[0] = "prod_id";
        vectorProducto1[1] = "prod_nom";
        vectorProducto1[2] = "prod_preu";
        vectorProducto1[3] = "prod_foto";
        vectorProducto1[4] = "categoria_id";
        vectorProducto1[5] = "estoc_q_max";
        vectorProducto1[6] = "estoc_q_min";
        vectorProducto1[7] = "estoc_q_actual";
        vectorProducto1[8] = "categoria_nom";
        vectorProducto1[9] = "estoc_id";

        muestra = new DefaultTableModel(null, vectorProducto1);
//String[] vectorProducto; De otra manera definir el vector

        try {

            st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                vectorProducto[0] = String.valueOf(rs.getInt("prod_id"));
                vectorProducto[1] = rs.getString("prod_nom");
                vectorProducto[2] = String.valueOf(rs.getDouble("prod_preu"));
                vectorProducto[3] = rs.getString("prod_foto");
                vectorProducto[4] = String.valueOf(rs.getInt("categoria_id"));
                vectorProducto[5] = String.valueOf(rs.getInt("estoc_q_max"));
                vectorProducto[6] = String.valueOf(rs.getInt("estoc_q_min"));
                vectorProducto[7] = String.valueOf(rs.getInt("estoc_q_actual"));
                vectorProducto[8] = rs.getString("categoria_nom");
                vectorProducto[9] = String.valueOf(rs.getInt("estoc_id"));
                muestra.addRow(vectorProducto);
            }
        } catch (Exception e) {
        }

        return muestra;
    }


}
