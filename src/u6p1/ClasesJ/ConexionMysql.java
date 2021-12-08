/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package u6p1.ClasesJ;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

/**
 *
 * @author reyes
 */
public class ConexionMysql {

    String usuario = "root",
            contraseña = "root",
            host = "localhost",
            basedatos = "";
    Connection con;

    public ConexionMysql(String bd) {
        basedatos = bd;
    }

    public String getBasedatos() {
        return basedatos;
    }

    public void conectar() {
        try {

            System.out.println("Conexion a la BD");
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + basedatos, usuario, contraseña);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido la sig excepcion: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void desconectar() {
        try {
            con.close();
            System.out.println("Desconectado de la BD");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public boolean insertar(String tabla, String values) {
        try {
            Statement stmt = (Statement) con.createStatement();
            String query = "INSERT INTO " + tabla + " VALUES(" + values + ");";
            stmt.executeUpdate(query);
            System.out.println("Insercion Exitosa");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modificar(String tabla, String values) {
        try {
            Statement stmt = (Statement) con.createStatement();
            String query = "UPDATE " + tabla + " SET " + values + ";";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminar(String tabla, String condicion) {
        try {
            Statement stmt = (Statement) con.createStatement();
            String query = "DELETE FROM " + tabla + " WHERE " + condicion + ";";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ResultSet consultar(String tabla) {
        try {
            ResultSet rs;
            Statement stmt = (Statement) con.createStatement();
            String query = "SELECT * FROM " + tabla + " ;";
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ResultSet consultaIndividual(String tabla,String id) {
        try {
            ResultSet rs;
            Statement stmt = (Statement) con.createStatement();
            String query = "SELECT * FROM " + tabla + " WHERE "+ id +";";
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public DefaultTableModel construirTabla(ResultSet rs) {

        try {
            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }

            return new DefaultTableModel(data, columnNames);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public DefaultComboBoxModel construirComboBox() {

        try {
            DefaultComboBoxModel comboBox;
            ResultSet rs;
            Statement stmt = (Statement) con.createStatement();
            String query = "show full tables where Table_Type = 'BASE TABLE';";
            rs = stmt.executeQuery(query);
            // data of the table
            Vector<String> data = new Vector<String>();
            while (rs.next()) {
                data.add(rs.getObject(1).toString());

            }
            comboBox = new DefaultComboBoxModel(data);

            return comboBox;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
