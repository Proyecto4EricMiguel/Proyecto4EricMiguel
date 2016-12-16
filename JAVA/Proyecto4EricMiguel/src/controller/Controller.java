/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
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

/**
 *
 * @author dawj23
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
        String vectorProducto[] = new String[9];
        String vectorProducto1[] = new String[9];
        vectorProducto1[0] = "prod_id";
        vectorProducto1[1] = "prod_nom";
        vectorProducto1[2] = "prod_preu";
        vectorProducto1[3] = "prod_foto";
        vectorProducto1[4] = "categoria_id";

        vectorProducto1[5] = "estoc_q_max";
        vectorProducto1[6] = "estoc_q_min";
        vectorProducto1[7] = "estoc_q_actual";
        vectorProducto1[8] = "categoria_nom";

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
}
